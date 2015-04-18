package org.delphinuslabs.directlauncher.utils;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.DisplayPhoto;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class DirectLauncherUtils {
    static Activity activityObj;
    public static final int REQUEST_SELECT_CONTACTS = 1;
    public static final int RESULT_SELECT_CONTACTS = 1;
    public static final int RESULT_CANCEL = 2;
    public static String convertToString(int resourceId) {
        return activityObj.getString(resourceId);
    }

    public static Bitmap getContactPhoto(ContentResolver contentResolver, String contactId) {
        Bitmap contactImg = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactId)));

            if (inputStream != null) {
                contactImg = BitmapFactory.decodeStream(inputStream);
                // ImageView imageView = (ImageView) findViewById(R.id.img_contact);
                // imageView.setImageBitmap(photo);
                inputStream.close();
            }

            // assert inputStream != null;
            // inputStream.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return contactImg;
    }

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, long photo_id, long photo_file_id) {

        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri, true);
        if (input != null) {
            return BitmapFactory.decodeStream(input);
        }
        else {
            Log.d("PHOTO", "first try failed to load photo");

        }

        Uri displayPhotoUri = ContentUris.withAppendedId(DisplayPhoto.CONTENT_URI, photo_file_id);
        try {
            AssetFileDescriptor fd = cr.openAssetFileDescriptor(displayPhotoUri, "r");
            if (fd.createInputStream() != null) {
                return BitmapFactory.decodeStream(fd.createInputStream());
            }
        }
        catch (IOException e) {
            // return null;
        }

        // Uri displayPhotoUri = Uri.withAppendedPath(uri, Contacts.Photo.DISPLAY_PHOTO);
        // try {
        // AssetFileDescriptor fd = cr.openAssetFileDescriptor(displayPhotoUri, "r");
        // if (fd.createInputStream() != null) {
        // return BitmapFactory.decodeStream(fd.createInputStream());
        // }
        //
        // }
        // catch (IOException e) {
        // }


        byte[] photoBytes = null;

        // Uri photoUri = ContentUris.withAppendedId(ContactsContract.Data.CONTENT_URI, photo_id);
        //
        // Cursor c = cr.query(photoUri, new String[] {ContactsContract.CommonDataKinds.Photo.PHOTO}, null, null, null);
        // Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, id);
        Uri photoUri = Uri.withAppendedPath(uri, Contacts.Photo.CONTENT_DIRECTORY);

        Cursor c = cr.query(photoUri, new String[] {Contacts.Photo.PHOTO}, null, null, null);

        try {
            if (c.moveToFirst())
                photoBytes = c.getBlob(0);

        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        finally {

            c.close();
        }

        if (photoBytes != null)
            return BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
        else
            Log.d("PHOTO", "second try also failed");
        return null;
    }

    public static Bitmap getContactImage(ContentResolver contentResolver, long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = contentResolver.query(photoUri, new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                }
            }
        }
        finally {
            cursor.close();
        }
        return null;

    }

    public static Bitmap getContactImg(ContentResolver contentResolver, String image_uri) {
        Bitmap contactImage = null;
        if (image_uri != null) {
            // System.out.println(Uri.parse(image_uri));
            try {
                contactImage = MediaStore.Images.Media.getBitmap(contentResolver, Uri.parse(image_uri));

            }
            catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return contactImage;
    }

    public static class ContactsViewHolder {
        public ImageView imgView;
        public TextView txtView;
        public CheckBox chkBox;
    }
}
