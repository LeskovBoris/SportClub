package com.example.sportclub.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sportclub.data.SportClubContract.MemberEntry;

import androidx.annotation.Nullable;

public class SportClubDbHelper extends SQLiteOpenHelper {
    public SportClubDbHelper( Context context) {
        super(context, SportClubContract.DATABASE_NAME, null, SportClubContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + MemberEntry.TABLE_NAME + "("
                + MemberEntry.KEY_ID + " INTEGER PRIMARY KEY,"
                + MemberEntry.KEY_FIRSTNAME + " TEXT,"
                + MemberEntry.KEY_LASTNAME + " TEXT,"
                + MemberEntry.KEY_GENDER + " INTEGER NOT NULL,"
                + MemberEntry.KEY_SPORT + " TEXT" + ")";

        db.execSQL(CREATE_MEMBERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MemberEntry.TABLE_NAME);
        onCreate(db);

    }


}
