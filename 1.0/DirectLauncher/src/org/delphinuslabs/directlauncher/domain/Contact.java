package org.delphinuslabs.directlauncher.domain;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Contact {
    private String id;
    private String name;
    // private String phoneNumber;
    private ArrayList<String> phoneNumbersList;
    private Bitmap contactImg;
    public Contact() {

    }

    // public Contact(Parcel in) {
    // this.id = in.readString();
    // this.name = in.readString();
    // this.phoneNumber = in.readString();
    // this.contactImg = (Bitmap) in.readValue(Contact.class.getClassLoader());
    // this.phoneNumbersList = in.readArrayList(Contact.class.getClassLoader());
    // }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String getPhoneNumber() {
    // return phoneNumber;
    // }
    //
    // public void setPhoneNumber(String phoneNumber) {
    // this.phoneNumber = phoneNumber;
    // }

    public ArrayList<String> getPhoneNumbersList() {
        return phoneNumbersList;
    }

    public void setPhoneNumbersList(ArrayList<String> phoneNumbersList) {
        this.phoneNumbersList = phoneNumbersList;
    }

    public Bitmap getContactImg() {
        return contactImg;
    }

    public void setContactImg(Bitmap contactImg) {
        this.contactImg = contactImg;
    }

    // @Override
    // public int describeContents() {
    // // TODO Auto-generated method stub
    // return 0;
    // }
    //
    // @Override
    // public void writeToParcel(Parcel dest, int flags) {
    // // TODO Auto-generated method stub
    // dest.write
    // dest.writeString(id);
    // dest.writeString(name);
    // dest.writeList(phoneNumbersList);
    //
    // }
    //
    // static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
    //
    // @Override
    // public Contact createFromParcel(Parcel in) {
    // return new Contact();
    // }
    //
    // @Override
    // public Contact[] newArray(int size) {
    // return new Contact[size];
    // }
    // };

}
