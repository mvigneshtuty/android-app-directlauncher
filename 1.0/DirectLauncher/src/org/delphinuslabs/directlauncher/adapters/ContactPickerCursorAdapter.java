package org.delphinuslabs.directlauncher.adapters;

import java.util.ArrayList;

import org.delphinuslabs.directlauncher.R;
import org.delphinuslabs.directlauncher.utils.DirectLauncherUtils;
import org.delphinuslabs.directlauncher.utils.DirectLauncherUtils.ContactsViewHolder;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactPickerCursorAdapter extends CursorAdapter implements OnClickListener {
    ArrayList<Boolean> checkBoxState = new ArrayList<Boolean>();
    static String[] selectedIds;
    // int position;
    String contactId;
    static String tag = "CursorAdapter";
    public static ArrayList<String> cursorAdapterContactsIdList;
    Context ctx;

    public ContactPickerCursorAdapter(Context context, Cursor c, ArrayList<String> contactIdList) {
        super(context, c);
        // TODO Auto-generated constructor stub
        this.ctx = context;
        // checkBoxState = new boolean[c.getCount()];
        for (int i = 0; i < this.getCount(); i++) {
            checkBoxState.add(i, false); // initializes all items value with false
        }
        // selectedIds = new String[c.getCount()];
        cursorAdapterContactsIdList = contactIdList;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.contact_picker_fragment, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder();
        viewHolder.imgView = (ImageView) retView.findViewById(R.id.contact_img);
        viewHolder.txtView = (TextView) retView.findViewById(R.id.contact_name);
        viewHolder.chkBox = (CheckBox) retView.findViewById(R.id.chkBox_selectContact);
        // viewHolder.chkBox.setOnClickListener(this);
        retView.setTag(viewHolder);
        return retView;
    }
    /*
    @Override
    public void bindView(View rowView, Context context, Cursor cursor) {
        final int position = cursor.getPosition();
        Log.i(tag, "position : " + position);
        // contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        // cursorAdapterContactsIdList.add(contactId);
        ImageView contactImageView = (ImageView) rowView.findViewById(R.id.contact_img);
        String image_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        Bitmap contactImg = DirectLauncherUtils.getContactImg(context.getContentResolver(), image_uri);
        // Bitmap contactImg = DirectLauncherUtils.loadContactPhoto(context.getContentResolver(), cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID)),
        // cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID)), cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_FILE_ID)));
        // if (image_uri != null) {
        // contactImageView.setImageURI(Uri.parse(image_uri));
        if (contactImg != null) {
            contactImageView.setImageBitmap(contactImg);
        }
        else {
            contactImageView.setImageResource(R.drawable.default_img);
        }
        TextView contactNameView = (TextView) rowView.findViewById(R.id.contact_name);
        contactNameView.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        // cursorAdapterContactsIdList.add(position, cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        Log.i(tag, "name : " + cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
        // Log.i(tag, "id val : " + selectedIds[position]);
        setSelectedIds(contactId);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.chkBox_selectContact);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checkBoxState.set(position, true);
                    CharSequence cs = position + " checked!";
                    Toast.makeText(ctx, cs, Toast.LENGTH_SHORT).show();
                }
                else {
                    checkBoxState.set(position, false);
                    CharSequence cs = position + " UN checked !";
                    Toast.makeText(ctx, cs, Toast.LENGTH_SHORT).show();
                }

            }
        });
        checkBox.setChecked(checkBoxState.get(position));
        Log.i(tag, "chkbox state : " + checkBoxState.get(position));
    }
     */

    @Override
    public void bindView(View rowView, Context context, Cursor cursor) {
        RowData data = new RowData();
        data.contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        data.contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        setSelectedIds(data.contactId + ": " + data.contactName);
        data.contactImg = DirectLauncherUtils.getContactImg(context.getContentResolver(), cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)));
        data.position = cursor.getPosition();
        final ContactsViewHolder viewHolder = (ContactsViewHolder) rowView.getTag();
        viewHolder.txtView.setText(data.contactName);
        if (data.contactImg != null) {
            viewHolder.imgView.setImageBitmap(data.contactImg);
        }
        else {
            viewHolder.imgView.setImageResource(R.drawable.default_img);
        }
        viewHolder.chkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // ContactsViewHolder viewHolder = (ContactsViewHolder) v.getTag();
                RowData data = (RowData) viewHolder.chkBox.getTag();
                if (isChecked) {

                    checkBoxState.set(data.position, true);
                    Log.i(tag, "cursor.getPosition(true) with onChecked Listener : " + data.position);
                }
                else {
                    checkBoxState.set(data.position, false);
                    Log.i(tag, "cursor.getPosition(false) with onChecked Listener : " + data.position);
                }
            }
        });
        // viewHolder.chkBox.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // ContactsViewHolder viewHolder = (ContactsViewHolder) v.getTag();
        // RowData data = (RowData) viewHolder.chkBox.getTag();
        // if (viewHolder.chkBox.isChecked()) {
        // checkBoxState.set(data.position, true);
        // Log.i(tag, "cursor.getPosition(true) with onclick listener : " + data.position);
        // }
        // else {
        // checkBoxState.set(data.position, false);
        // Log.i(tag, "cursor.getPosition(false) with onclick listener : " + data.position);
        // }
        // }
        // });
        viewHolder.chkBox.setChecked(checkBoxState.get(data.position));
        viewHolder.chkBox.setTag(data);
    }

    public static void setSelectedIds(String contactId) {
        //        selectedIds = arg;
        //        for (int i = 0; i < selectedIds.length; i++) {
        //
        //            if (!cursorAdapterContactsIdList.contains(selectedIds[i])) {
        //                Log.i(tag, "id val in cusror adapter : " + selectedIds[i]);
        //                cursorAdapterContactsIdList.add(selectedIds[i]);
        //            }
        //
        //        }
        if (!cursorAdapterContactsIdList.contains(contactId)) {
            Log.i(tag, "id val in cusror adapter : " + contactId);
            cursorAdapterContactsIdList.add(contactId);
        }
    }

    public static ArrayList<String> getSelectedIds() {
        Log.i(tag, "id size" + selectedIds.length);
        // for (int i = 0; i < selectedIds.length; i++) {
        // Log.i(tag, "id val in cusror adapter : " + selectedIds[i]);
        // cursorAdapterContactsIdList.add(selectedIds[i]);
        // }
        return cursorAdapterContactsIdList;

    }

    @Override
    public void onClick(View view) {
        boolean visibility = ((CheckBox) view).isChecked();
        // RowData data = (RowData) view.getTag();
        // Log.i(tag, "data: " + data.position);
        // checkBoxState.set(data.position, visibility);
        ContactsViewHolder viewHolder = (ContactsViewHolder) view.getTag();
        RowData data = (RowData) viewHolder.chkBox.getTag();
        Log.i(tag, "data: " + data.position);
        checkBoxState.set(data.position, visibility);
        // mHelper.setChecked(data.id, visibility == true ? 1 : 0);
    }

    private static class RowData {
        String contactId;
        Bitmap contactImg;
        String contactName;
        int position;
    }

}
