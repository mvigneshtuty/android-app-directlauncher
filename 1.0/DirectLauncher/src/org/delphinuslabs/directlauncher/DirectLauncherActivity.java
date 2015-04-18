package org.delphinuslabs.directlauncher;

import java.util.ArrayList;
import java.util.Locale;

import org.delphinuslabs.directlauncher.fragments.DialPagerFragment;
import org.delphinuslabs.directlauncher.fragments.MessagePagerFragment;
import org.delphinuslabs.directlauncher.utils.DirectLauncherUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class DirectLauncherActivity extends Activity implements ActionBar.TabListener {
    private final int request_token = 1;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this
     * becomes too memory intensive, it may be best to switch to a {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    static String tag = "Direct Launcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_launcher);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public void invokeContactsView(View view) {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        CharSequence pagerTitle = mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem());
        // Toast.makeText(getApplicationContext(), "current page title : " + mViewPager.getAdapter().getPageTitle(mViewPager.getCurrentItem()), Toast.LENGTH_SHORT).show();
        // Intent contactsIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        // startActivity(contactsIntent);
        Intent selectContact = new Intent("org.delphinuslabs.directlauncher.contactselection");
        Bundle dataBundle = new Bundle();
        dataBundle.putCharSequence("pagetitle", pagerTitle);
        selectContact.putExtras(dataBundle);
        // startActivity(selectContact);
        startActivityForResult(selectContact, DirectLauncherUtils.REQUEST_SELECT_CONTACTS);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DirectLauncherUtils.REQUEST_SELECT_CONTACTS) {
            if (resultCode == DirectLauncherUtils.RESULT_SELECT_CONTACTS) {
                Bundle dataBundle = data.getExtras();
                ArrayList<String> selectedContactsIdList = dataBundle.getStringArrayList("selectedContacts");
                if (selectedContactsIdList.size() > 0) {
                    CharSequence pagerId = dataBundle.getCharSequence("pager_id");
                    CharSequence selectedContacts = "Direct Launcher for " + pagerId + ": \n";
                    for (String selectedId : selectedContactsIdList) {
                        selectedContacts = selectedContacts + selectedId + "\n";
                    }
                    Toast.makeText(getBaseContext(), selectedContacts, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "No contacts selected!", Toast.LENGTH_LONG).show();
                }

            }
            if (resultCode == DirectLauncherUtils.RESULT_CANCEL) {
                // TODO do nothing.. contacts selection cancelled by user
                Toast.makeText(getBaseContext(), "debug: user selected cancel..", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.direct_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            if (position == 0) {
                return DialPagerFragment.newInstance(position + 1);
            }
            else {
                return MessagePagerFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.dial).toUpperCase(l);
                case 1:
                    return getString(R.string.message).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view for dial pager.
     */
    /*
     * public static class DialPagerFragment extends Fragment {
     *//**
     * The fragment argument representing the section number for this fragment.
     */
    /*
     * private static final String ARG_SECTION_NUMBER = "section_number";
     *//**
     * Returns a new instance of this fragment for the given section number.
     */
    /*
     * public static DialPagerFragment newInstance(int sectionNumber) { DialPagerFragment fragment = new DialPagerFragment(); Bundle args = new Bundle(); args.putInt(ARG_SECTION_NUMBER, sectionNumber);
     * fragment.setArguments(args); return fragment; }
     *
     * public DialPagerFragment() { }
     *
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { View rootView = inflater.inflate(R.layout.dial_pager_fragment, container, false); return rootView; } }
     *//**
     * A placeholder fragment containing a simple view for message pager.
     */
    /*
     * public static class MessagePagerFragment extends Fragment {
     *//**
     * The fragment argument representing the section number for this fragment.
     */
    /*
     * private static final String ARG_SECTION_NUMBER = "section_number"; static String test = "";
     *//**
     * Returns a new instance of this fragment for the given section number.
     */
    /*
     * public static MessagePagerFragment newInstance(int sectionNumber) { test = "hello"; MessagePagerFragment fragment = new MessagePagerFragment(); Bundle args = new Bundle(); args.putInt(ARG_SECTION_NUMBER,
     * sectionNumber); fragment.setArguments(args); return fragment; }
     *
     * public MessagePagerFragment() { }
     *
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // test = "value changed.."; Log.i(tag, test); View rootView =
     * inflater.inflate(R.layout.message_pager_fragment, container, false); return rootView; } }
     */

}
