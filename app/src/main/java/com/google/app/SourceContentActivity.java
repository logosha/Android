package com.google.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

public class SourceContentActivity extends MyAbstractToolbarActivity  {
    TextView tvFileContent;
    ImageView ivFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_content);
        String fpath = getIntent().getStringExtra("fpath");
        tvFileContent = (TextView) findViewById(R.id.textSource);
        ivFileContent = (ImageView) findViewById(R.id.imageSource);

        if (fpath.endsWith(".png")){
            ivFileContent.setVisibility(View.VISIBLE);
            ivFileContent.setImageDrawable(Drawable.createFromPath(fpath));
        }
        else
        {
            BufferedReader br;
            String response = null;
            try {
                StringBuilder output = new StringBuilder();
                br = new BufferedReader(new FileReader(fpath));
                String line = "";
                while ((line = br.readLine()) != null) {
                    output.append(line +"\n");
                }
                response = output.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            tvFileContent.setVisibility(View.VISIBLE);
            tvFileContent.setText(response);
        }

    }
}
