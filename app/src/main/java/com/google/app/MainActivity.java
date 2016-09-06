package com.google.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

    ImageView settings;
    ImageView defaultPhoto;
    ImageView logOut;
    TextView userID;
    TextView name;
    TextView secondName;
    TextView phoneNumber;
    boolean isEdit = false;
    final int DIALOG_EXIT = 1;


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
                if (isEdit == false) {
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
                if (v.getId() == R.id.default_photo) {
                    Toast.makeText(MainActivity.this, "это картинка", Toast.LENGTH_LONG).show();
                } else if (v.getId() == R.id.default_photo && isEdit == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Photo content")
                            .setMessage("Откуда взять фото")
                            .setCancelable(false)
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog ad = builder.create();
                    ad.show();
                } else if (v.getId() == R.id.log_out && isEdit == true) {
                    Toast.makeText(MainActivity.this, "это иконка", Toast.LENGTH_LONG).show();
                } else if (isEdit == true) {
                    Toast.makeText(MainActivity.this, ((TextView) v).getText(), Toast.LENGTH_LONG).show();
                }
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


}