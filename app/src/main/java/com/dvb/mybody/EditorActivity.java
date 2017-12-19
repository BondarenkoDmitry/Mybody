package com.dvb.mybody;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.dvb.mybody.data.BodyContract;
import com.dvb.mybody.data.BodyDbHelper;


/**
 * Created by dmitrybondarenko on 30.05.17.
 */

public class EditorActivity extends AppCompatActivity implements
 LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_BODY_LOADER = 0;
    private EditText mDayEdit;
    private EditText mHeightEdit;
    private EditText mShouldersEdit;
    private EditText mChestEdit;
    private EditText mArmsEdit;
    private EditText mBellyEdit;
    private EditText mHipEdit;
    private EditText mWeightEdit;


    private boolean mBodyHasChanged = false;
    private Uri mCurrentBodyUri;


    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
            mBodyHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentBodyUri = intent.getData();

        if (mCurrentBodyUri == null){
            setTitle("Add measurements");
        }else{
            setTitle("Edit measurements");
            getLoaderManager().initLoader(EXISTING_BODY_LOADER, null, this);
        }

        mDayEdit = (EditText) findViewById(R.id.dayEdit);
        mHeightEdit = (EditText) findViewById(R.id.heightEdit);
        mShouldersEdit = (EditText) findViewById(R.id.shouldersEdit);
        mChestEdit = (EditText) findViewById(R.id.chestEdit);
        mArmsEdit = (EditText) findViewById(R.id.armsEdit);
        mBellyEdit = (EditText) findViewById(R.id.bellyEdit);
        mHipEdit = (EditText) findViewById(R.id.hipEdit);
        mWeightEdit = (EditText) findViewById(R.id.weight_edit);

        mDayEdit.setOnTouchListener(mTouchListener);
        mHeightEdit.setOnTouchListener(mTouchListener);
        mShouldersEdit.setOnTouchListener(mTouchListener);
        mChestEdit.setOnTouchListener(mTouchListener);
        mArmsEdit.setOnTouchListener(mTouchListener);
        mBellyEdit.setOnTouchListener(mTouchListener);
        mHipEdit.setOnTouchListener(mTouchListener);
        mWeightEdit.setOnTouchListener(mTouchListener);

    }

    private void saveBody() {

        String dayString = mDayEdit.getText().toString().trim();
        String heightString = mHeightEdit.getText().toString().trim();
        String shouldersString = mShouldersEdit.getText().toString().trim();
        String chestString = mChestEdit.getText().toString().trim();
        String armsString = mArmsEdit.getText().toString().trim();
        String bellyString = mBellyEdit.getText().toString().trim();
        String hipString = mHipEdit.getText().toString().trim();
        String weightString = mWeightEdit.getText().toString().trim();


        if (mCurrentBodyUri == null &&
                TextUtils.isEmpty(dayString) &&
                TextUtils.isEmpty(heightString) &&
                TextUtils.isEmpty(shouldersString) &&
                TextUtils.isEmpty(chestString) &&
                TextUtils.isEmpty(armsString) &&
                TextUtils.isEmpty(bellyString) &&
                TextUtils.isEmpty(hipString) &&
                TextUtils.isEmpty(weightString)){
            return;
        }

        ContentValues values = new ContentValues();

        String day = null;
        if (!TextUtils.isEmpty(dayString)){
            day = dayString;
        }
        values.put(BodyContract.BodyEntry.COLUMN_BODY_DAY, day);

        int height = 0;
        if (!TextUtils.isEmpty(heightString)){
            height = Integer.parseInt(heightString);
        }
        int shoulders = 0;
        if (!TextUtils.isEmpty(shouldersString)){
            shoulders = Integer.parseInt(shouldersString);
        }
        int chest = 0;
        if (!TextUtils.isEmpty(heightString)){
            chest = Integer.parseInt(chestString);
        }
        int arms = 0;
        if (!TextUtils.isEmpty(armsString)) {
            arms = Integer.parseInt(armsString);
        }
        int belly = 0;
        if (!TextUtils.isEmpty(bellyString)) {
            belly = Integer.parseInt(bellyString);
        }
        int hip = 0;
        if (!TextUtils.isEmpty(hipString)) {
            hip = Integer.parseInt(hipString);
        }
        int weight = 0;
        if (!TextUtils.isEmpty(weightString)){
            weight = Integer.parseInt(weightString);
        }

        values.put(BodyContract.BodyEntry.COLUMN_BODY_HEIGHT, height);
        values.put(BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS, shoulders);
        values.put(BodyContract.BodyEntry.COLUMN_BODY_CHEST, chest);
        values.put(BodyContract.BodyEntry.COLUMN_BODY_ARMS, arms);
        values.put(BodyContract.BodyEntry.COLUMN_BODY_BELLY, belly);
        values.put(BodyContract.BodyEntry.COLUMN_BODY_HIP, hip);
        values.put(BodyContract.BodyEntry.COLUMN_BODY_WEIGHT, weight);


        if (mCurrentBodyUri == null) {
            Uri newUri = getContentResolver().insert(BodyContract.BodyEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.hasnt_been_saved),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.saved),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING measure, so update the measure with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentBodyUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.updating_error),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.update_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentBodyUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                saveBody();
                finish();
                return true;

            case R.id.action_delete:
                deleteBody();
                return true;

            case android.R.id.home:
                if (!mBodyHasChanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mBodyHasChanged){
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String[] projection = {
                BodyContract.BodyEntry._ID,
                BodyContract.BodyEntry.COLUMN_BODY_DAY,
                BodyContract.BodyEntry.COLUMN_BODY_HEIGHT,
                BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS,
                BodyContract.BodyEntry.COLUMN_BODY_CHEST,
                BodyContract.BodyEntry.COLUMN_BODY_ARMS,
                BodyContract.BodyEntry.COLUMN_BODY_BELLY,
                BodyContract.BodyEntry.COLUMN_BODY_HIP,
                BodyContract.BodyEntry.COLUMN_BODY_WEIGHT};

        return new CursorLoader(this,
                mCurrentBodyUri,
                projection,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()){
            int dayColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_DAY);
            int heightColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_HEIGHT);
            int shouldersColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS);
            int chestColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_CHEST);
            int armsColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_ARMS);
            int bellyColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_BELLY);
            int hipsColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_HIP);
            int weightColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_WEIGHT);

            String bodyDay = cursor.getString(dayColumnIndex);
            int bodyHeight = cursor.getInt(heightColumnIndex);
            int bodyShoulders = cursor.getInt(shouldersColumnIndex);
            int bodyChest = cursor.getInt(chestColumnIndex);
            int bodyArms = cursor.getInt(armsColumnIndex);
            int bodyBelly = cursor.getInt(bellyColumnIndex);
            int bodyHips = cursor.getInt(hipsColumnIndex);
            int bodyWeight = cursor.getInt(weightColumnIndex);

            // Update the TextViews with the attributes for the current pet
            mDayEdit.setText(bodyDay);
            mHeightEdit.setText(Integer.toString(bodyHeight));
            mShouldersEdit.setText(Integer.toString(bodyShoulders));
            mChestEdit.setText(Integer.toString(bodyChest));
            mArmsEdit.setText(Integer.toString(bodyArms));
            mBellyEdit.setText(Integer.toString(bodyBelly));
            mHipEdit.setText(Integer.toString(bodyHips));
            mWeightEdit.setText(Integer.toString(bodyWeight));
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDayEdit.setText("");
        mHeightEdit.setText("");
        mShouldersEdit.setText("");
        mChestEdit.setText("");
        mArmsEdit.setText("");
        mBellyEdit.setText("");
        mHipEdit.setText("");
        mWeightEdit.setText("");
    }

    private void deleteBody(){
        if (mCurrentBodyUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentBodyUri, null, null);
            if (rowsDeleted == 0){
                Toast.makeText(this, "Error with deleting", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Measurements deleted",
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
