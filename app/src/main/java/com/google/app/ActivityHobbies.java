package com.google.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class ActivityHobbies extends AppCompatActivity {


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
