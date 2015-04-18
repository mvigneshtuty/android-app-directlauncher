package org.delphinuslabs.directlauncher.adapters;

import java.util.ArrayList;

import org.delphinuslabs.directlauncher.R;
import org.delphinuslabs.directlauncher.domain.Contact;
import org.delphinuslabs.directlauncher.utils.DirectLauncherUtils.ContactsViewHolder;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactPickerAdapter extends ArrayAdapter<Contact> {
    private final Context activityCtx;
    private final ArrayList<Contact> contactsList;
    boolean[] checkBoxState;
    String tag = "Direct Launcher";
    public ContactPickerAdapter(Context context, int resource, ArrayList<Contact> objects) {
        super(context, resource, objects);
        this.activityCtx = context;
        this.contactsList = objects;
        this.checkBoxState = new boolean[objects.size()];
        Log.i(tag, "constructing the adapter..");
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) activityCtx).getLayoutInflater();
        ContactsViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.contact_picker_fragment, null);
            viewHolder = new ContactsViewHolder();
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.contact_img);
            viewHolder.txtView = (TextView) convertView.findViewById(R.id.contact_name);
            viewHolder.chkBox = (CheckBox) convertView.findViewById(R.id.chkBox_selectContact);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ContactsViewHolder) convertView.getTag();
        }

        Contact contact = contactsList.get(position);
        if (contact.getContactImg() != null) {
            viewHolder.imgView.setImageBitmap(contact.getContactImg());
        }
        else {
            viewHolder.imgView.setImageResource(R.drawable.default_img);
        }
        viewHolder.txtView.setText(contact.getName());
        viewHolder.chkBox.setChecked(checkBoxState[position]);
        // viewHolder.chkBox.setChecked(true);

        viewHolder.chkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    checkBoxState[position] = true;
                    CharSequence cs = contactsList.get(position).getName()+" clicked!";
                    Toast.makeText(getContext(), cs, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    checkBoxState[position] = false;
                    CharSequence cs = contactsList.get(position).getName() + " UN checked !";
                    Toast.makeText(getContext(), cs, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return convertView;
        /* WITH OUT VIEW HOLDER
         *
         * View rowView = inflater.inflate(R.layout.contact_picker_fragment, null);
        ImageView contactImageView = (ImageView) rowView.findViewById(R.id.contact_img);
        if (contactsList.get(position).getContactImg() != null) {
            contactImageView.setImageBitmap(contactsList.get(position).getContactImg());

        }
        else {
            contactImageView.setImageResource(R.drawable.default_img);
        }
        TextView contactNameView = (TextView) rowView.findViewById(R.id.contact_name);
        contactNameView.setText(contactsList.get(position).getName());
        return rowView;*/
    }




}