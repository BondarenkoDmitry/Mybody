package com.dvb.mybody.data;

import android.content.Context;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dmitrybondarenko on 30.05.17.
 */

public class BodyDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "mybody.db";
    public static final int DATABASE_VERSION = 1;

    public BodyDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_BODY_TABLE = "CREATE TABLE " + BodyContract.BodyEntry.TABLE_NAME + " ("
                + BodyContract.BodyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BodyContract.BodyEntry.COLUMN_BODY_DAY + " NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_HEIGHT + " INTEGER NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS + " INTEGER NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_CHEST + " INTEGER NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_ARMS + " INTEGER NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_BELLY + " INTEGER NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_HIP + " INTEGER NOT NULL, "
                + BodyContract.BodyEntry.COLUMN_BODY_WEIGHT + " INTEGER NOT NULL);";

        db.execSQL(SQL_CREATE_BODY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
