package org.delphinuslabs.directlauncher.fragments;

import org.delphinuslabs.directlauncher.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view for message pager.
 */
public class MessagePagerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    String tag = "Direct Launcher";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static MessagePagerFragment newInstance(int sectionNumber) {
        MessagePagerFragment fragment = new MessagePagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MessagePagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message_pager_fragment, container, false);
        return rootView;
    }
}
