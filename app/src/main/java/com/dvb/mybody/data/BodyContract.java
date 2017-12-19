package com.dvb.mybody.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import static android.provider.Telephony.TextBasedSmsColumns.BODY;

/**
 * Created by dmitrybondarenko on 30.05.17.
 */

public final class BodyContract {

    private BodyContract() {}


    public static final String CONTENT_AUTHORITY = "com.dvb.mybody";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BODY = "body";


    public static final class BodyEntry implements BaseColumns {

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BODY;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BODY);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BODY;

        public final static String TABLE_NAME = "body";
        // Unique ID for the body only in DB
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_BODY_DAY = "day";

        // Height
        // Type int
        public final static String COLUMN_BODY_HEIGHT = "height";
        // Shoulders
        // Type int
        public final static String COLUMN_BODY_SHOULDERS = "shoulders";
        // Chest
        // Type int
        public final static String COLUMN_BODY_CHEST = "chest";
        // Arms
        // Type int
        public final static String COLUMN_BODY_ARMS = "arms";
        // Belly
        // Type int
        public final static String COLUMN_BODY_BELLY = "belly";
        // Hip
        // Type int
        public final static String COLUMN_BODY_HIP = "hip";

        public final static String COLUMN_BODY_WEIGHT = "weight";


    }
}




















