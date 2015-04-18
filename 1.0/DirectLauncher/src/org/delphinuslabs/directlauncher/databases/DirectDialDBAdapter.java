package org.delphinuslabs.directlauncher.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DirectDialDBAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String TAG = "DBAdapter";

    public static final String DATABASE_NAME = "DirectLauncherDB";
    public static final String DATABASE_TABLE = "directdial";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table contacts(_id INTEGER PRIMARY KEY AUTOINCREMENT," + "name text not null,email text not null)";

    private final Context context;
    DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DirectDialDBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(ctx);

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data!");
            db.execSQL("DROP IF TABLE EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public DirectDialDBAdapter open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertContact(String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteContact(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllContacts() {
        // try using ALL_KEYS instead of new
        // String[]{KEY_ROWID,KEY_NAME,KEY_EMAIL}
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL}, null, null, null, null, null);
    }

    public Cursor getContact(long rowId) {
        Cursor cursor = db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;

    }

    public boolean updateContact(long rowId, String name, String email) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null) > 0;
    }

}
