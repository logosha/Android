package com.google.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    TextView email;
    ListView lvMain;
    boolean isEdit = false;



    public void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();
        String[] items = res.getStringArray(R.array.names);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);



        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_list_item, items);
        lvMain.setAdapter(adapter);

            lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {
                    TextView textView = (TextView) itemClicked;
                    String strText = textView.getText().toString();
                    String[] names = getResources().getStringArray(R.array.names);

                    if(strText.equalsIgnoreCase(names[0])) {
                        startActivity(new Intent(MainActivity.this, ActivitySkills.class));
                    }
                    if(strText.equalsIgnoreCase(names[1])) {
                        startActivity(new Intent(MainActivity.this, ActivityExperience.class));
                    }
                    if(strText.equalsIgnoreCase(names[2])) {
                        startActivity(new Intent(MainActivity.this, ActivityEducation.class));
                    }
                    if(strText.equalsIgnoreCase(names[3])) {
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
        email = (TextView) findViewById(R.id.email);


        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (isEdit==false) {
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
                if(v.getId()==R.id.default_photo)
                Toast.makeText(MainActivity.this, "это картинка", Toast.LENGTH_LONG).show();
                if (isEdit==true) {
                    switch (v.getId()) {
                        case R.id.log_out:
                            Toast.makeText(MainActivity.this, "это иконка", Toast.LENGTH_LONG).show();
                            break;
                        case R.id.user_id:
                            Toast.makeText(MainActivity.this, R.string.user_id, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.name:
                            Toast.makeText(MainActivity.this, R.string.name, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.second_name:
                            Toast.makeText(MainActivity.this, R.string.second_name, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.phone_number:
                            Toast.makeText(MainActivity.this, R.string.phone_number, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.email:
                            Toast.makeText(MainActivity.this, R.string.email, Toast.LENGTH_LONG).show();
                            break;
                        case R.id.lvMain:
                            Toast.makeText(MainActivity.this, "это лист", Toast.LENGTH_LONG).show();
                            break;
                    }
                }

            }
        };

        defaultPhoto.setOnClickListener(ocl);
        logOut.setOnClickListener(ocl);
        logOut.setOnClickListener(ocl);
        userID.setOnClickListener(ocl);
        name.setOnClickListener(ocl);
        secondName.setOnClickListener(ocl);
        phoneNumber.setOnClickListener(ocl);
        email.setOnClickListener(ocl);

    }


}