package com.google.app.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.app.R;


public class FragmentHobbies extends Fragment {
    Animation animation;
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_hobbies, container, false);
        imageView = (ImageView) v.findViewById(R.id.navHeaderImage);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_ball);

        imageView.startAnimation(animation);
        return v;
    }
 }
