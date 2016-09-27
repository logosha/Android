package com.google.app.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.app.R;

public class FragmentFileContent extends Fragment {
    TextView tvFileContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_fragment_file_content, container, false);
        tvFileContent = (TextView) v.findViewById(R.id.fileContent);
        Toast toast1 = Toast.makeText(getActivity(), "Hello File", Toast.LENGTH_SHORT);
        toast1.show();
        tvFileContent.setText("");
        return v;
    }

}
