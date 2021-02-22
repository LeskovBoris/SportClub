package com.example.sportclub.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.sportclub.data.SportClubContract.MemberEntry;


public class SportClubContentProvider extends ContentProvider {

    SportClubDbHelper dbHelper;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(SportClubContract.AUTHORITY, SportClubContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(SportClubContract.AUTHORITY, SportClubContract.PATH_MEMBERS + "/#", MEMBER_ID);

    }

    @Override
    public boolean onCreate() {
        dbHelper = new SportClubDbHelper(getContext());
        return true;
    }


    @Override
    //query() - извлечение данных из одной строки таблицы
    //projection = {"lastname", "gender"}
    //selection = "_id=?"
    //selectionArgs = 34
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) { //
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case MEMBER_ID:
                selection = MemberEntry._ID + "=?"; // выбираем запись по ID
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry.TABLE_NAME,projection,selection,selectionArgs,
                        null,null,sortOrder);
                break;

            default:

                throw new IllegalArgumentException("Can't query incorrect URI" + uri);
        }

        //cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }



    @Override
    public Uri insert(Uri uri,  ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                long id = db.insert(MemberEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }

                return ContentUris.withAppendedId(uri, id);


            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);
        }


    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);


            case MEMBER_ID:
                selection = MemberEntry._ID + "=?"; // выбираем запись по ID
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(MemberEntry.TABLE_NAME,  selection, selectionArgs);



            default:

                throw new IllegalArgumentException("Can't delete this URI" + uri);
        }

    }

    @Override
    public int update(Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);


            case MEMBER_ID:
                selection = MemberEntry._ID + "=?"; // выбираем запись по ID
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);



            default:

                throw new IllegalArgumentException("Can't update this URI" + uri);
        }


    }

    @Override
    public String getType( Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;


            case MEMBER_ID:
                return MemberEntry.CONTENT_SINGLE_ITEM;



            default:

                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }
}
//URI - Unified Resource Identifier
// content://com.example.sportclub/members code 2
// content://com.example.sportclub/members/34 code 1
//URL - Unified Resource Locator
//http://google.com
