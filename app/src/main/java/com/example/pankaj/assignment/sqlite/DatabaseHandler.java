package com.example.pankaj.assignment.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pankaj.assignment.model.Data;
import com.example.pankaj.assignment.model.User;
import com.example.pankaj.assignment.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj on 21-06-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserInfo";

    // Contacts table name
    private static final String TABLE_USERS = "user";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_PHOTO_URL = "photo_url";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "desc";
    private static final String KEY_IMG_URL = "img_url";
    private static final String TABLE_POPULAR = "table_popular";
    private static final String KEY_POPULAR_TITLE = "popular_title";
    private static final String KEY_POPULAR_DESC = "popular_desc";
    private static final String KEY_POPULAR_IMG_URL = "popular_img_url";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRSTNAME + " TEXT," + KEY_LASTNAME + " TEXT," + KEY_PHOTO_URL + " TEXT," + KEY_EMAIL + " TEXT,"
                + KEY_GENDER + " TEXT," + KEY_TITLE + " TEXT," + KEY_DESC + " TEXT," + KEY_IMG_URL + " TEXT" + ")";
        String CREATE_CONTACTS_TABLE_POPULAR = "CREATE TABLE " + TABLE_POPULAR + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_POPULAR_TITLE + " TEXT," + KEY_POPULAR_DESC + " TEXT," + KEY_POPULAR_IMG_URL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE_POPULAR);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POPULAR);

        // Create tables again
        onCreate(db);
    }

    public void addUserInfo(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, userInfo.getFirstname());
        values.put(KEY_LASTNAME, userInfo.getLastname());
        values.put(KEY_PHOTO_URL, userInfo.getPhotourl());
        values.put(KEY_EMAIL, userInfo.getEmail());
        values.put(KEY_GENDER, userInfo.getGender());
        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public void addServerData(List<Data> results){
        for(int i = 0;i < results.size();i++){

            insertDeal(results.get(i));
        }
    }

    private void insertDeal(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE,data.getTitle());
        values.put(KEY_DESC,data.getDescription());
        values.put(KEY_IMG_URL,data.getImage());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public List<Data> getAllDeals(){

        List<Data> deallists = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{

                Data m = new Data();
                m.set_id(Integer.parseInt(cursor.getString(0)));
                m.setTitle(cursor.getString(6));
                m.setDescription(cursor.getString(7));
                m.setImage(cursor.getString(8));
                deallists.add(m);
            }while(cursor.moveToNext());
        }

        return deallists;

    }

    public List<UserInfo> getAllUserInfo(int i) {
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERS  + " WHERE " + KEY_ID + " = " + i;;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserInfo  userInfo = new UserInfo();
                userInfo.setId(Integer.parseInt(cursor.getString(0)));
                userInfo.setFirstname(cursor.getString(1));
                userInfo.setLastname(cursor.getString(2));
                userInfo.setPhotourl(cursor.getString(3));
                userInfo.setEmail(cursor.getString(4));
                userInfo.setGender(cursor.getString(5));

                // Adding contact to list
                userInfoList.add(userInfo);
            } while (cursor.moveToNext());
        }

        // return contact list
        return userInfoList;
    }

    public void addPopular(List<Data> results){
        for(int i = 0;i < results.size();i++){

            insertPopularData(results.get(i));
        }
    }

    private void insertPopularData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POPULAR_TITLE,data.getTitle());
        values.put(KEY_POPULAR_DESC,data.getDescription());
        values.put(KEY_POPULAR_IMG_URL,data.getImage());
        db.insert(TABLE_POPULAR, null, values);
        db.close();
    }


    public List<Data> getAllPopularData(){

        List<Data> deallists = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_POPULAR;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{

                Data m = new Data();
                m.set_id(Integer.parseInt(cursor.getString(0)));
                m.setTitle(cursor.getString(1));
                m.setDescription(cursor.getString(2));
                m.setImage(cursor.getString(3));
                deallists.add(m);
            }while(cursor.moveToNext());
        }

        return deallists;

    }


    public void removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.delete(TABLE_POPULAR, null, null);
    }

}