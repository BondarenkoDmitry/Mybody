package com.dvb.mybody.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.R.attr.defaultHeight;
import static android.R.attr.name;
import static android.R.attr.value;
import static com.dvb.mybody.R.string.arms;
import static com.dvb.mybody.R.string.belly;
import static com.dvb.mybody.R.string.chest;
import static com.dvb.mybody.R.string.shoulders;
import static com.dvb.mybody.data.BodyContract.BodyEntry.CONTENT_LIST_TYPE;

/**
 * Created by dmitrybondarenko on 30.05.17.
 */

public class BodyProvider extends ContentProvider {


    private static final int BODIES = 100;
    private static final int BODY_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String LOG_TAG = BodyProvider.class.getSimpleName();

    static {
        sUriMatcher.addURI(BodyContract.CONTENT_AUTHORITY, BodyContract.PATH_BODY, BODIES);
        sUriMatcher.addURI(BodyContract.CONTENT_AUTHORITY, BodyContract.PATH_BODY + "/#", BODY_ID);
    }


    private BodyDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new BodyDbHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case BODIES:

                cursor = database.query(BodyContract.BodyEntry.TABLE_NAME, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case BODY_ID:

                selection = BodyContract.BodyEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(BodyContract.BodyEntry.TABLE_NAME, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        // Return the cursor
        return cursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BODIES:
                return insertBody(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertBody(Uri uri, ContentValues values) {

        String day = values.getAsString(BodyContract.BodyEntry.COLUMN_BODY_DAY);
        if (day == null) {
            throw new IllegalArgumentException("What day is it today?");
        }

        Integer height = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_HEIGHT);
        if (height != null && height < 0) {
            throw new IllegalArgumentException("What's your height?");
        }


        Integer shoulders = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS);
        if (shoulders != null && shoulders < 0) {
            throw new IllegalArgumentException("Measure your shoulders");
        }

        Integer chest = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_CHEST);
        if (chest != null && chest < 0) {
            throw new IllegalArgumentException("Measure your chest");
        }

        Integer arms = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_ARMS);
        if (arms != null && arms < 0) {
            throw new IllegalArgumentException("Measure your arms");
        }

        Integer belly = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_BELLY);
        if (belly != null && belly < 0) {
            throw new IllegalArgumentException("Measure your belly");
        }

        Integer hip = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_HIP);
        if (hip != null && hip < 0) {
            throw new IllegalArgumentException("Measure your hips");
        }

        Integer weight = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_WEIGHT);
        if (weight != null && weight < 0) {
            throw new IllegalArgumentException("Measure your weight");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new body with the given values
        long id = database.insert(BodyContract.BodyEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the body content URI
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BODIES:
                return updateBody(uri, contentValues, selection, selectionArgs);
            case BODY_ID:

                selection = BodyContract.BodyEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateBody(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateBody(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_DAY)) {
            String day = values.getAsString(BodyContract.BodyEntry.COLUMN_BODY_DAY);
            if (day == null) {
                throw new IllegalArgumentException("What day is it today?");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_HEIGHT)) {
            Integer height = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_HEIGHT);
            if (height != null && height < 0) {
                throw new IllegalArgumentException("Measure your height");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS)) {
            Integer shoulders = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS);
            if (shoulders != null && shoulders < 0) {
                throw new IllegalArgumentException("Measure your shoulders");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_CHEST)) {
            Integer chest = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_CHEST);
            if (chest != null && chest < 0) {
                throw new IllegalArgumentException("Measure your chest");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_ARMS)) {
            Integer arms = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_ARMS);
            if (arms != null && arms < 0) {
                throw new IllegalArgumentException("Measure your arms");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_BELLY)) {
            Integer belly = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_BELLY);
            if (belly != null && belly < 0) {
                throw new IllegalArgumentException("Measure your belly");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_HIP)) {
            Integer hip = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_HIP);
            if (hip != null && hip < 0) {
                throw new IllegalArgumentException("Measure your hip");
            }
        }

        if (values.containsKey(BodyContract.BodyEntry.COLUMN_BODY_WEIGHT)) {
            Integer weight = values.getAsInteger(BodyContract.BodyEntry.COLUMN_BODY_WEIGHT);
            if (weight != null && weight < 0) {
                throw new IllegalArgumentException("Measure your weight");
            }
        }


        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(BodyContract.BodyEntry.TABLE_NAME, values,
                selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case BODIES:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(BodyContract.BodyEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;
            case BODY_ID:
                // Delete a single row given by the ID in the URI
                selection = BodyContract.BodyEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BodyContract.BodyEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BODIES:
                return BodyContract.BodyEntry.CONTENT_LIST_TYPE;
            case BODY_ID:
                return BodyContract.BodyEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match "
                        + match);
        }
    }

}
