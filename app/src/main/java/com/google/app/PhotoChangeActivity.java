package com.google.app;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.GridView;

/**
 * Created by Алексей on 06.09.2016.
 */
public class PhotoChangeActivity extends MyAbstractToolbarActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    GridView gvData;
    SimpleCursorAdapter scAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_change);

        getSupportLoaderManager().initLoader(0, null, this);

        String[] from = new String[] {MediaStore.Images.Media._ID};
        int[] to = new int[] {R.id.tvText};

        gvData = (GridView) findViewById(R.id.gvPhoto);
        scAdapter = new SimpleCursorAdapter(this, R.layout.grid_item, null, from, to, 0);
        gvData.setAdapter(scAdapter);
        adjustGridView();
    }

    private void adjustGridView() {
        gvData.setNumColumns(GridView.AUTO_FIT);
        gvData.setColumnWidth(80);
        gvData.setVerticalSpacing(5);
        gvData.setHorizontalSpacing(5);
        gvData.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new CursorLoader(this,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media._ID},
                null,
                null,
                MediaStore.Images.Media._ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}