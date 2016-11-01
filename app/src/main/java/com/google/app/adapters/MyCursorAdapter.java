package com.google.app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.app.R;
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
        ProgressBar pb;

        ViewHolder(View itemView) {
            iv = (ImageView) itemView.findViewById(R.id.gvImage);
            pb = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final View view = cursorInflater.inflate(R.layout.grid_item_layout, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
   }

    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.pb.setVisibility(View.VISIBLE);

        int imagePathIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        final String imagePath = cursor.getString(imagePathIdx);
        view.setTag(R.string.tag_image_path, imagePath);
        Picasso.with(context)
                .load(new File(imagePath))
                .resize(200, 200)
                .error(R.drawable.fail)
                .centerCrop()
                .into(holder.iv, new ImageLoadedCallback(holder.pb));

    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar) {
            progressBar = progBar;
        }
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

    }
}
