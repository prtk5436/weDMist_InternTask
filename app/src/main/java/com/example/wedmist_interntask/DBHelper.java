package com.example.wedmist_interntask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, "EMP.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase mydb) {

        mydb.execSQL("create table user(email Text primary key, password Text, name Text, dob Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase mydb, int oldVersion, int newVersion) {
        mydb.execSQL("Drop table if exists user");
    }

    public Boolean insertData(String email, String password, String name, String dob) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("name", name);
        contentValues.put("dob", dob);

        long result = myDB.insert("user", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkUser(String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from user where email= ?", new
                String[]{email});

        if (cursor.getCount() > 0) {
            return true;

        } else {
            return false;
        }
    }


    public Boolean checkCredentials(String email, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from user where email= ? and password=?", new
                String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;

        } else {
            return false;
        }
    }


    public Cursor getUserDetails(String email) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        Log.d(TAG, "getCustomerDetails: inside method");
        Cursor cursor = myDB.rawQuery("Select * from user where email= ? ", new String[]{email});
        if (cursor.moveToFirst()) {
            do {
                //   String address = cursor.getString(cursor.getColumnIndex("address"));
                String userEmail = cursor.getString(cursor.getColumnIndex("email"));
                String userpassword = cursor.getString(cursor.getColumnIndex("password"));
                String username = cursor.getString(cursor.getColumnIndex("name"));
                String userdob = cursor.getString(cursor.getColumnIndex("dob"));
                // Log.d(TAG, "getCustomerDetails: address" + address);
                Log.d(TAG, "getCustomerDetails: password" + userpassword);
                Log.d(TAG, "getCustomerDetails: email" + userEmail);
                Log.d(TAG, "getCustomerDetails: dob" + userdob);
                Log.d(TAG, "getCustomerDetails: name" + username);

            } while (cursor.moveToNext());
        }
        return cursor;
    }
}
