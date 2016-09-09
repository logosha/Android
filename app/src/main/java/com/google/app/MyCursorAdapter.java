package com.google.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Алексей on 08.09.2016.
 */
public class MyCursorAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public MyCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.grid_item_layout, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.tvText);
        ImageView imageView = (ImageView) view.findViewById(R.id.gvImage);
        String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        textView.setText(title);

    }
}
