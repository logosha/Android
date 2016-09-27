package com.google.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
import android.widget.ImageView;


import com.google.app.fragments.FragmentAbout;
import com.google.app.fragments.FragmentEducation;
import com.google.app.fragments.FragmentExperience;
import com.google.app.fragments.FragmentHobbies;
import com.google.app.fragments.FragmentMain;
import com.google.app.fragments.FragmentSkills;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageView defaultPhoto;
    final String EDU = "edu";
    final String EXP = "exp";
    final String SKL = "skl";
    final String HOB = "hob";
    final String ABT = "abt";




    FragmentEducation fragmentEducation;
    FragmentExperience fragmentExperience;
    FragmentHobbies fragmentHobbies;
    FragmentSkills fragmentSkills;
    FragmentMain fragmentMain;
    FragmentAbout fragmentAbout;

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
        fragmentMain = new FragmentMain();
        fragmentAbout = new FragmentAbout();

        String tag = "main";
        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.container, fragmentMain, tag)
                .addToBackStack(tag)
                .commit();
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
        Fragment fragmentToShow = null;
        String tag = "";

        if (id == R.id.nav_education) {
            fragmentToShow = fragmentEducation;
            tag = EDU;
        } else if (id == R.id.nav_experience) {
            fragmentToShow = fragmentExperience;
            tag = EXP;
        } else if (id == R.id.nav_skills) {
            fragmentToShow = fragmentSkills;
            tag = SKL;
        } else if (id == R.id.nav_hobbies) {
            fragmentToShow = fragmentHobbies;
            tag = HOB;
        } else if (id == R.id.about) {
            fragmentToShow = fragmentAbout;
            tag = ABT;

        } else if (id == R.id.nav_send) {

        }
                 fragmentTransaction
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
                .replace(R.id.container, fragmentToShow, tag)
                .addToBackStack(tag)
                .commit();

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
