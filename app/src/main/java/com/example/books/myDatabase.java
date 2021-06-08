package com.example.books;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class myDatabase extends SQLiteAssetHelper {
    public static final int DB_VERSION = 2;
    public static final String DB_NAME ="Books.db";
    public static final String  TB_NAME = "Books";

    public static final String  ID = "id";
    public static final String  NAME = "name";
    public static final String  AUTHOR = "author";
    public static final String  DEPARTMENT = "department";
    public static final String  LANGUAGE = "language";
    public static final String  PUBLISHER = "publisher";
    public static final String  PAGES = "pages";
    public static final String  IAMGE = "image";
    public static final String RATING = "rating";


    public myDatabase(Context context) {
        super( context, DB_NAME, null, DB_VERSION );
    }
}
