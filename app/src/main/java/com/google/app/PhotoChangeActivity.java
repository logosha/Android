package com.google.app;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * Created by Алексей on 06.09.2016.
 */
public class PhotoChangeActivity extends  MyAbstractToolbarActivity {

    GridView gv;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);

        gv = (GridView) findViewById(R.id.gvPhoto);

        adjustGridView();


        String[] projection = {
                MediaStore.Images.Thumbnails._ID,
        };

        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null);

        // startManagingCursor(cursor);

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID},
                new int[] {android.R.id.text2,android.R.id.text1});

        gv.setAdapter(adapter);

    }

    private void adjustGridView() {
        gv.setNumColumns(GridView.AUTO_FIT);
        gv.setColumnWidth(80);
        gv.setVerticalSpacing(5);
        gv.setHorizontalSpacing(5);
        gv.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
    }
}
