package org.delphinuslabs.directlauncher;

import java.util.ArrayList;

import org.delphinuslabs.directlauncher.adapters.ContactPickerAdapter;
import org.delphinuslabs.directlauncher.adapters.ContactPickerCursorAdapter;
import org.delphinuslabs.directlauncher.domain.Contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class ContactSelection extends Activity implements OnItemClickListener {
    ContactPickerAdapter contactPickerAdapter;
    ContactPickerCursorAdapter contactCursorAdapter;
    ArrayList<Contact> contactsList = new ArrayList<Contact>();
    ArrayList<String> contactsIdList = new ArrayList<String>();
    CharSequence pageTitle;
    String tag = "ContactSelection";

    ListView contactListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_selection);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle dataBundle = getIntent().getExtras();
        pageTitle = dataBundle.getCharSequence("pagetitle");
        /** changes for Array Adapter */
        contactListView = (ListView) findViewById(R.id.lst_contactDetails);
        // final Button selectContactButton = (Button) findViewById(R.id.btn_selectContact);
        contactListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // ContentResolver contentResolver = getContentResolver();
        /*
         * changes for cursor adapter
         */new Handler().post(new Runnable() {
             @Override
             public void run() {
                 // ListView contactListView = (ListView) findViewById(R.id.lst_contactDetails);
                 // contactListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                 ContentResolver contentResolver = getContentResolver();
                 contactCursorAdapter = new ContactPickerCursorAdapter(ContactSelection.this, contentResolver.query(ContactsContract.Contacts.CONTENT_URI, new String[] {ContactsContract.Contacts._ID,
                                 ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts.PHOTO_URI, ContactsContract.Contacts.PHOTO_ID, ContactsContract.Contacts.PHOTO_FILE_ID},
                                 ContactsContract.Contacts.HAS_PHONE_NUMBER + ">" + 0, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"), contactsIdList);
                 // contactsIdList = ContactPickerCursorAdapter.cursorAdapterContactsIdList;
                 // contactsIdList = ContactPickerCursorAdapter.getSelectedIds();
                 // for (String id : contactsIdList) {
                 // Log.i(tag, "id in contact selection : " + id);
                 // }
                 // setContactsIdList(contactsIdList);
                 contactListView.setAdapter(contactCursorAdapter);
             }
         });

         /**
          * changes for listView adapter
          *
          *
          * Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null); Contact contact; boolean hasPhoneNumber; if (cursor.getCount() > 0) { while (cursor.moveToNext()) { contact
          * = new Contact(); String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)); contact.setId(id); String name =
          * cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)); contact.setName(name); contact.setContactImg(DirectLauncherUtils.getContactPhoto(contentResolver, id)); if
          * (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) { hasPhoneNumber = true; Cursor pCur =
          * contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] {id}, null); ArrayList<String> phoneNumbersList = new
          * ArrayList<String>(); while (pCur.moveToNext()) { String phoneNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); phoneNumbersList.add(phoneNumber); //
          * contact.setPhoneNumber(phoneNumber); } contact.setPhoneNumbersList(phoneNumbersList); pCur.close(); } else { hasPhoneNumber = false; } if (hasPhoneNumber) { contactsList.add(contact); }
          *
          * } cursor.close(); }
          *
          * contactPickerAdapter = new ContactPickerAdapter(this, R.layout.contact_picker_fragment, contactsList); contactListView.setAdapter(contactPickerAdapter);
          */
         contactListView.setOnItemClickListener(this);
         /*
          * selectContactButton.setOnClickListener(new View.OnClickListener() {
          *
          * @Override public void onClick(View v) { int numOfSelectedContacts = contactListView.getCount(); CharSequence selectedContacts = "Selected Contacts: \n"; for (int i = 0; i < numOfSelectedContacts; i++) { if
          * (contactListView.isItemChecked(i)) { selectedContacts = selectedContacts + contactsIdList.get(i) + "\n"; }
          *
          * } Toast.makeText(ContactSelection.this, selectedContacts, Toast.LENGTH_SHORT).show(); } });
          */

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.chkBox_selectContact);
        cb.performClick();
        /*
         * if (cb.isChecked()) { Toast.makeText(this, contactsList.get(position).getName() + " selected", Toast.LENGTH_SHORT).show(); } else if (!cb.isChecked()) { Toast.makeText(this,
         * contactsList.get(position).getName() + " de-selected", Toast.LENGTH_SHORT).show(); }
         */
    }

    public void displaySelectedContacts(View view) {

        int numOfSelectedContacts = contactListView.getCount();
        // CharSequence selectedContacts = "Selected Contacts: \n";
        // for (int i = 0; i < numOfSelectedContacts; i++) {
        // if (contactListView.isItemChecked(i)) {
        // selectedContacts = selectedContacts + contactsIdList.get(i) + "\n";
        // }
        //
        // }
        // Toast.makeText(ContactSelection.this, selectedContacts, Toast.LENGTH_SHORT).show();
        Toast.makeText(ContactSelection.this, numOfSelectedContacts, Toast.LENGTH_SHORT).show();

    }

    public ArrayList<String> getContactsIdList() {
        return contactsIdList;
    }

    public void setContactsIdList(ArrayList<String> contactsIdList) {
        this.contactsIdList = contactsIdList;
    }

    public CharSequence getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(CharSequence pageTitle) {
        this.pageTitle = pageTitle;
    }

}
