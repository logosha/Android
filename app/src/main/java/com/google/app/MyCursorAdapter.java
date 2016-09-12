package com.google.app;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

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
    public static final class ViewHolder {
        ImageView iv;
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.grid_item_layout, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        ProgressBar progressBar = null;
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);


        ViewHolder holder = new ViewHolder();
        holder.iv = (ImageView) view.findViewById(R.id.gvImage);

        int imagePathIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        final String imagePath = cursor.getString(imagePathIdx);
        Picasso.with(context)
                .load(new File(imagePath))
                .resize(200, 200)
                .error(R.drawable.fail)
                .centerCrop()
                .into(holder.iv, new ImageLoadedCallback(progressBar) {
                    @Override
                    public void onSuccess() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);

                    }
                });

    }
    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public  ImageLoadedCallback(ProgressBar progBar){
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}
