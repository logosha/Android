package com.google.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.app.adapters.MyCursorAdapter;

/**
 * Created by Алексей on 06.09.2016.
 */
public class PhotoChangeActivity extends MyAbstractToolbarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 0;
    MyCursorAdapter customAdapter;
    private GridView gridView;
    String[] mProjection = new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_change);
        gridView = (GridView) findViewById(R.id.gridView);
        getLoaderManager().initLoader(LOADER_ID, null, this);

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
    }

    @Override
    public Loader <Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        mProjection,
        null,
        null,
        MediaStore.Images.Media._ID + " DESC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (customAdapter==null) {
            customAdapter = new MyCursorAdapter(
                    PhotoChangeActivity.this,
                    cursor,
                    0);
            gridView.setAdapter(customAdapter);
        } else {
            customAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}