package com.example.sportclub.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class SportClubContract {


    public static final  int DATABASE_VERSION = 1;
    public static final  String DATABASE_NAME = "SportClub";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.sportclub";
    public static final String PATH_MEMBERS = "members";


    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);


    private SportClubContract() {

    }

    public static final class MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "members";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

        public static final String KEY_ID = BaseColumns._ID;
        public static final String KEY_FIRSTNAME = "firstName";
        public static final String KEY_LASTNAME = "lastName";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_SPORT = "sport";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +  AUTHORITY + "/" + PATH_MEMBERS; //MIME для работы с несколькими строками
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS; //MIME для работы с одной строкой

    }
}
