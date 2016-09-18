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

import java.io.File;

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
        String[] mProjection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

        mCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mProjection,
                null,
                null,
                MediaStore.Images.Media._ID + " DESC");

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long i) {

                String path = (String)view.getTag(R.string.tag_image_path);
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(RESULT_OK, intent);
                finish();
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