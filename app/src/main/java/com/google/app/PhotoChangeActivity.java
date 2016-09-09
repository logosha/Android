package com.google.app;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Алексей on 06.09.2016.
 */
public class PhotoChangeActivity extends MyAbstractToolbarActivity {

    MyCursorAdapter customAdapter;
    private Cursor mCursor;
    private GridView gridView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);

        gridView = (GridView) findViewById(R.id.gridView);

        String[] mProjection = new String[] {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

        mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mProjection,
                null,
                null,
                MediaStore.Images.Media._ID + " DESC");


        new Handler().post(new Runnable() {

            @Override
            public void run() {
                customAdapter = new MyCursorAdapter(
                        PhotoChangeActivity.this,
                        mCursor,
                        0);

                gridView.setAdapter(customAdapter);
            }

        });


    }

}