package com.google.app.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.app.PhotoChangeActivity;
import com.google.app.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class FragmentMain extends Fragment implements View.OnClickListener{

    ImageView defaultPhoto;

    TextView email;
    TextView name;
    TextView secondName;
    TextView phoneNumber;
    final int REQUEST_CODE_CAMERA = 1;
    final int REQUEST_CODE_GALLERY = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main, container, false);

        defaultPhoto = (ImageView) v.findViewById(R.id.default_photo);

        name = (TextView) v.findViewById(R.id.name);
        email = (TextView) v.findViewById(R.id.email);
        secondName = (TextView) v.findViewById(R.id.second_name);
        phoneNumber = (TextView) v.findViewById(R.id.phone_number);

        defaultPhoto.setOnClickListener(this);

        name.setOnClickListener(this);
        secondName.setOnClickListener(this);
        phoneNumber.setOnClickListener(this);
        email.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.default_photo) {
            showChooserDialog();
        }

    }

    private void showChooserDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Please change photo");

        Button btn1 = (Button) dialog.findViewById(R.id.from_galery);
        btn1.setText(R.string.from_gallery);
        Button btn2 = (Button) dialog.findViewById(R.id.from_camera);
        btn2.setText(R.string.from_camera);
        dialog.show();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhotoChangeActivity.class);
                dialog.dismiss();
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                dialog.dismiss();
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    if (data != null && data.hasExtra("path")) {
                        String path = (String) data.getExtras().get("path");
                        Picasso.with(getActivity())
                                .load(new File(path))
                                .resize(200, 200)
                                .centerInside()
                                .into(defaultPhoto);
                        break;
                    }
                case REQUEST_CODE_CAMERA:
                    if (data != null && data.hasExtra("data")) {
                        Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                        defaultPhoto.setImageBitmap(thumbnailBitmap);
                        break;
                    }
            }
        }
    }


}
