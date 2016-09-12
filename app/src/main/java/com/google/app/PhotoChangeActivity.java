package com.google.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Алексей on 06.09.2016.
 */
public class PhotoChangeActivity extends MyAbstractToolbarActivity {

    MyCursorAdapter customAdapter;
    private Cursor mCursor;
    private GridView gridView;
    final String LOG_TAG = "myLogs";

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long i) {
                Toast.makeText(PhotoChangeActivity.this, "" + view.getId(), Toast.LENGTH_SHORT).show();
                ImageView newIV = (ImageView) findViewById(R.id.default_photo);
              Cursor cursor = (Cursor)adapterView.getItemAtPosition(pos);
              cursor.moveToPosition(pos);
               String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
               Log.d(LOG_TAG, "itemClick: position = " + pos + ", id = " + id);
                Picasso.with(PhotoChangeActivity.this)
                        .load()
                        .resize(200, 200)
                        .centerInside()
                        .into(newIV);

            }
        });


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