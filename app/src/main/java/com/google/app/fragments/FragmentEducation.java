package com.google.app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.app.R;

public class FragmentEducation extends Fragment {
    Button btn1;
    Button btn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_education, container, false);

        btn1 = (Button) v.findViewById(R.id.btnUniverSite);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.univer.kharkov.ua/en"));
                startActivity(intent);
            }
        });
        btn2 = (Button) v.findViewById(R.id.btnUniverLocation);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:50.004472, 36.228194"));
                startActivity(intent);
            }
        });
        return v;
    }

}
