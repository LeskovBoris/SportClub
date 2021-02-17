package com.example.sportclub.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;



public class SportClubContentProvider extends ContentProvider {

    SportClubDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new SportClubDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        return null;
    }



    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType( Uri uri) {
        return null;
    }
}
//URI - Unified Resource Identifier
// content://com.example.sportclub/members
//URL - Unified Resource Locator
//http://google.com
