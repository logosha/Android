package com.google.app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class ActivityHobbies extends MyAbstractToolbarActivity {


    Animation animation;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies);


        imageView = (ImageView) findViewById(R.id.imageView);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_ball);

        imageView.startAnimation(animation);
    }


}
