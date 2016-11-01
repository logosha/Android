package com.google.app.adapters;

import java.io.File;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.app.R;

/**
 * Created by Алексей on 06.10.2016.
 */

public class CustomAdapter extends ArrayAdapter<File> {
    private ArrayList<File> items;
    private Context c = null;


    public CustomAdapter(Context context, int textViewResourceId, ArrayList<File> items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.c = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_row, null);
        }

        TextView filename = null;
        ImageView fileicon = null;

        File f = items.get(position);
        if (f != null) {
            filename = (TextView) v.findViewById(R.id.filename);
            fileicon = (ImageView) v.findViewById(R.id.fileicon);
        }

        if (filename != null) {
            if (position == 0) {
                filename.setText(f.getAbsolutePath());
            } else if (position == 1) {
                filename.setText(f.getAbsolutePath());
            } else {
                filename.setText(f.getName());
            }
        }

        if (fileicon != null) {
            if (position == 0) {
                fileicon.setImageResource(R.drawable.root);
            } else if (position == 1) {
                fileicon.setImageResource(R.drawable.up);
            } else if (f.isDirectory()) {
                fileicon.setImageResource(R.drawable.folder);
            } else {
                fileicon.setImageResource(R.drawable.file);
            }
        }

        return v;
    }

}
