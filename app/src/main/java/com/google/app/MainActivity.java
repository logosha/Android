package com.google.app;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.app.fragments.FragmentEducation;
import com.google.app.fragments.FragmentExperience;
import com.google.app.fragments.FragmentHobbies;
import com.google.app.fragments.FragmentSkills;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageView settings;
    ImageView defaultPhoto;
    ImageView logOut;
    TextView userID;
    TextView name;
    TextView secondName;
    TextView phoneNumber;
    boolean isEdit = false;
    final int REQUEST_CODE_CAMERA = 1;
    final int REQUEST_CODE_GALLERY = 2;

    FragmentEducation fragmentEducation;
    FragmentExperience fragmentExperience;
    FragmentHobbies fragmentHobbies;
    FragmentSkills fragmentSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentEducation = new FragmentEducation();
        fragmentExperience = new FragmentExperience();
        fragmentHobbies = new FragmentHobbies();
        fragmentSkills = new FragmentSkills();

        findUI();
        initListeners();
    }

    private void findUI() {
        settings = (ImageView) findViewById(R.id.settings);
        defaultPhoto = (ImageView) findViewById(R.id.default_photo);
        logOut = (ImageView) findViewById(R.id.log_out);
        userID = (TextView) findViewById(R.id.user_id);
        name = (TextView) findViewById(R.id.name);
        secondName = (TextView) findViewById(R.id.second_name);
        phoneNumber = (TextView) findViewById(R.id.phone_number);
    }


    private void initListeners() {
        defaultPhoto.setOnClickListener(this);
        logOut.setOnClickListener(this);
        userID.setOnClickListener(this);
        name.setOnClickListener(this);
        secondName.setOnClickListener(this);
        phoneNumber.setOnClickListener(this);
        findViewById(R.id.email).setOnClickListener(this);
        settings.setOnClickListener(this);
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
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settings && !isEdit) {
            Toast toast1 = Toast.makeText(MainActivity.this, "включен edit mode", Toast.LENGTH_SHORT);
            toast1.show();
            isEdit = true;
        } else if (v.getId() == R.id.settings && isEdit) {
            Toast toast2 = Toast.makeText(MainActivity.this, "выключен edit mode", Toast.LENGTH_SHORT);
            toast2.show();
            isEdit = false;
        } else if (v.getId() == R.id.default_photo && isEdit) {
            showChooserDialog();
        } else if (v.getId() == R.id.default_photo) {
            Toast.makeText(MainActivity.this, "это картинка", Toast.LENGTH_LONG).show();
        } else if (v.getId() == R.id.log_out && isEdit) {
            Toast.makeText(MainActivity.this, "это иконка", Toast.LENGTH_LONG).show();
        } else if (isEdit) {
            Toast.makeText(MainActivity.this, ((TextView) v).getText(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (id == R.id.nav_education) {
            String tag = "edu";
                    fragmentTransaction
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                    .replace(R.id.container, fragmentEducation, tag)
                    .addToBackStack(tag);

        } else if (id == R.id.nav_experience) {
            String tag = "exp";
            fragmentTransaction
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                    .replace(R.id.container, fragmentExperience, tag)
                    .addToBackStack(tag);

        } else if (id == R.id.nav_skills) {
            String tag = "skl";
            fragmentTransaction
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                    .replace(R.id.container, fragmentSkills, tag)
                    .addToBackStack(tag);

        } else if (id == R.id.nav_hobbies) {
            String tag = "hob";
            fragmentTransaction
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                    .replace(R.id.container, fragmentHobbies, tag)
                    .addToBackStack(tag);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
