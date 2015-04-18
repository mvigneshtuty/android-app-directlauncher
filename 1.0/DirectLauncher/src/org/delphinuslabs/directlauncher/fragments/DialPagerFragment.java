package org.delphinuslabs.directlauncher.fragments;

import org.delphinuslabs.directlauncher.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view for dial pager.
 */

public class DialPagerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static DialPagerFragment newInstance(int sectionNumber) {
        DialPagerFragment fragment = new DialPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    Integer[] imageIDs = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6, R.drawable.pic7};

    public DialPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dial_pager_fragment, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "pic" + (position + 1) + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private final Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        // ---returns the number of images---
        @Override
        public int getCount() {
            return imageIDs.length;
        }

        // ---returns the item---
        @Override
        public Object getItem(int position) {
            return position;
        }

        // ---returns the ID of an item---
        @Override
        public long getItemId(int position) {
            return position;
        }

        // ---returns an ImageView view---
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            }
            else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imageIDs[position]);
            return imageView;
        }
    }
}
