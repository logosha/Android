package com.google.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;


public class MainActivity extends Activity {


    private static final String LOG = "MyLogs";
    ImageView settings;
    ImageView defaultPhoto;
    ImageView logOut;
    TextView userID;
    TextView name;
    TextView secondName;
    TextView phoneNumber;
    boolean isEdit = false;
    File directory;
    final int REQUEST_CODE_CAMERA = 1;
    final int REQUEST_CODE_GALLERY = 2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        String[] items = getResources().getStringArray(R.array.names);
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        lvMain.setAdapter(new ArrayAdapter<String>(this, R.layout.my_list_item, items));


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString();
                String[] names = getResources().getStringArray(R.array.names);

                if (strText.equalsIgnoreCase(names[0])) {
                    startActivity(new Intent(MainActivity.this, ActivitySkills.class));
                }
                if (strText.equalsIgnoreCase(names[1])) {
                    startActivity(new Intent(MainActivity.this, ActivityExperience.class));
                }
                if (strText.equalsIgnoreCase(names[2])) {
                    startActivity(new Intent(MainActivity.this, ActivityEducation.class));
                }
                if (strText.equalsIgnoreCase(names[3])) {
                    startActivity(new Intent(MainActivity.this, ActivityHobbies.class));
                }
            }
        });


        settings = (ImageView) findViewById(R.id.settings);
        defaultPhoto = (ImageView) findViewById(R.id.default_photo);
        logOut = (ImageView) findViewById(R.id.log_out);
        userID = (TextView) findViewById(R.id.user_id);
        name = (TextView) findViewById(R.id.name);
        secondName = (TextView) findViewById(R.id.second_name);
        phoneNumber = (TextView) findViewById(R.id.phone_number);


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEdit) {
                    Toast toast1 = Toast.makeText(MainActivity.this, "включен edit mode", Toast.LENGTH_SHORT);
                    toast1.show();
                    isEdit = true;
                } else {
                    Toast toast2 = Toast.makeText(MainActivity.this, "выключен edit mode", Toast.LENGTH_SHORT);
                    toast2.show();
                    isEdit = false;
                }
            }
        });

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.default_photo && isEdit) {
                    showChooserDialog();
                } else if (v.getId() == R.id.default_photo) {
                    Toast.makeText(MainActivity.this, "это картинка", Toast.LENGTH_LONG).show();
                } else if (v.getId() == R.id.log_out && isEdit) {
                    Toast.makeText(MainActivity.this, "это иконка", Toast.LENGTH_LONG).show();
                } else if (isEdit) {
                    Toast.makeText(MainActivity.this, ((TextView) v).getText(), Toast.LENGTH_LONG).show();
                }
            }

            private void showChooserDialog() {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Photo changing");

                Button btn1 = (Button) dialog.findViewById(R.id.from_galery);
                btn1.setText(R.string.from_gallery);
                Button btn2 = (Button) dialog.findViewById(R.id.from_camera);
                btn2.setText(R.string.from_camera);
                ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
                image.setImageResource(R.drawable.photo_icon);
                dialog.show();
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, PhotoChangeActivity.class);
                        dialog.dismiss();
                        startActivityForResult(intent, REQUEST_CODE_GALLERY);
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        dialog.dismiss();
                        // intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    }
                });

            }

        };

        defaultPhoto.setOnClickListener(ocl);
        logOut.setOnClickListener(ocl);
        userID.setOnClickListener(ocl);
        name.setOnClickListener(ocl);
        secondName.setOnClickListener(ocl);
        phoneNumber.setOnClickListener(ocl);
        findViewById(R.id.email).setOnClickListener(ocl);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY:
                    if (data != null && data.hasExtra("path")) {
                        String path = (String) data.getExtras().get("path");
                        Picasso.with(MainActivity.this)
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