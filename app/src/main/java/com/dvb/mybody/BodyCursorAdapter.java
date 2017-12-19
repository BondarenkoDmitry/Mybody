package com.dvb.mybody;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dvb.mybody.data.BodyContract;

/**
 * Created by dmitrybondarenko on 30.05.17.
 */

public class BodyCursorAdapter extends CursorAdapter {

    public BodyCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dayTextView = (TextView) view.findViewById(R.id.day_num);
        TextView heightTextView = (TextView) view.findViewById(R.id.height_num);
        TextView shouldersTextView = (TextView) view.findViewById(R.id.shoulders_num);
        TextView chestTextView = (TextView) view.findViewById(R.id.chest_num);
        TextView armsTextView = (TextView) view.findViewById(R.id.arms_num);
        TextView bellyTextView = (TextView) view.findViewById(R.id.belly_num);
        TextView hipsTextView = (TextView) view.findViewById(R.id.hip_num);
        TextView weightTextView = (TextView) view.findViewById(R.id.weight_num);

        int dayColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_DAY);
        int heightColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_HEIGHT);
        int shouldersColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_SHOULDERS);
        int chestColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_CHEST);
        int armsColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_ARMS);
        int bellyColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_BELLY);
        int hipsColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_HIP);
        int weightColumnIndex = cursor.getColumnIndex(BodyContract.BodyEntry.COLUMN_BODY_WEIGHT);


        String bodyDay = cursor.getString(dayColumnIndex);
        String bodyHeight = cursor.getString(heightColumnIndex);
        String bodyShoulders = cursor.getString(shouldersColumnIndex);
        String bodyChest = cursor.getString(chestColumnIndex);
        String bodyArms = cursor.getString(armsColumnIndex);
        String bodyBelly = cursor.getString(bellyColumnIndex);
        String bodyHips = cursor.getString(hipsColumnIndex);
        String bodyWeight = cursor.getString(weightColumnIndex);


        // Update the TextViews with the attributes for the current pet
        dayTextView.setText(bodyDay);
        heightTextView.setText(bodyHeight);
        shouldersTextView.setText(bodyShoulders);
        chestTextView.setText(bodyChest);
        armsTextView.setText(bodyArms);
        bellyTextView.setText(bodyBelly);
        hipsTextView.setText(bodyHips);
        weightTextView.setText(bodyWeight);
    }
}
