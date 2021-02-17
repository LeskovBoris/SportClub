package com.example.sportclub.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class SportClubContract {
    //cheking gihub

    public static final  int DATABASE_VERSION = 1;
    public static final  String DATABASE_NAME = "SportClub";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.sportclub";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

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

    }
}
