package org.delphinuslabs.directlauncher.adapters;

import java.util.Locale;

import org.delphinuslabs.directlauncher.R;
import org.delphinuslabs.directlauncher.fragments.DialPagerFragment;
import org.delphinuslabs.directlauncher.fragments.MessagePagerFragment;
import org.delphinuslabs.directlauncher.utils.DirectLauncherUtils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

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
                return DirectLauncherUtils.convertToString(R.string.dial).toUpperCase(l);
            case 1:
                return DirectLauncherUtils.convertToString(R.string.message).toUpperCase(l);
        }
        return null;
    }
}
