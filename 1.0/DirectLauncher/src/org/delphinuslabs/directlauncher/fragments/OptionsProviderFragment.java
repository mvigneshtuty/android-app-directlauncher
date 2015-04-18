package org.delphinuslabs.directlauncher.fragments;

import java.util.ArrayList;

import org.delphinuslabs.directlauncher.ContactSelection;
import org.delphinuslabs.directlauncher.R;
import org.delphinuslabs.directlauncher.utils.DirectLauncherUtils;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class OptionsProviderFragment extends Fragment {

    String tag = "OptionsProvider";
    ArrayList<String> contactsIdList = new ArrayList<String>();
    ArrayList<String> selectedContactsList = new ArrayList<String>();
    CharSequence pageTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.fragment_options_provider, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnDone = (Button) getActivity().findViewById(R.id.btn_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView contactListView = (ListView) getActivity().findViewById(R.id.lst_contactDetails);
                int numOfSelectedContacts = contactListView.getCount();
                CharSequence csNumOfContacts = numOfSelectedContacts + "";
                // Toast.makeText(getActivity(), "num of contacts : " + csNumOfContacts, Toast.LENGTH_SHORT).show();
                CharSequence selectedContacts = "Selected Contacts: \n";
                ContactSelection cs = (ContactSelection) getActivity();
                pageTitle = cs.getPageTitle();
                contactsIdList = cs.getContactsIdList();
                Log.i(tag, "size : " + contactsIdList.size());
                for (String id : contactsIdList) {
                    Log.i(tag, "id : " + id);
                }
                for (int i = 0; i < numOfSelectedContacts; i++) {
                    if (contactListView.isItemChecked(i)) {
                        // CharSequence idPos = i+"";
                        // Toast.makeText(getActivity(), idPos, Toast.LENGTH_SHORT).show();
                        selectedContacts = selectedContacts + contactsIdList.get(i) + "\n";
                        selectedContactsList.add(contactsIdList.get(i));
                    }

                }
                Intent selectionDetails = new Intent();
                Bundle dataBundle = new Bundle();
                dataBundle.putStringArrayList("selectedContacts", selectedContactsList);
                dataBundle.putCharSequence("pager_id", pageTitle);
                selectionDetails.putExtras(dataBundle);
                // Toast.makeText(getActivity(), selectedContacts, Toast.LENGTH_SHORT).show();
                getActivity().setResult(DirectLauncherUtils.RESULT_SELECT_CONTACTS, selectionDetails);
                getActivity().finish();
            }
        });

        Button btnCancel = (Button) getActivity().findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent goBackToHome = new Intent();
                getActivity().setResult(DirectLauncherUtils.RESULT_CANCEL, goBackToHome);
                getActivity().finish();
            }
        });
    }

}
