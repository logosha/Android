package com.google.app.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.app.R;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FragmentAbout extends Fragment {
    Button btnCheck;
    Button btnDownload;
    Uri downloadUri = Uri.parse("https://github.com/logosha/android/archive/master.zip");
    Uri destinationUri = Uri.parse( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/android.zip");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { View v = inflater.inflate(R.layout.fr_about, container, false);

        btnCheck = (Button) v.findViewById(R.id.btnCheck);
        btnDownload = (Button) v.findViewById(R.id.btnDownload);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isOnline() ){
                    Toast.makeText(getActivity(), "There is an Internet connection", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadProject();
            }
        });
        return v;
}

    private void downloadProject() {
        ThinDownloadManager downloadManager = new ThinDownloadManager(5);
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri).setDestinationURI(destinationUri);
        downloadManager.add(downloadRequest);

        DownloadStatusListener downloadListener = new DownloadStatusListener() {
            @Override
            public void onDownloadComplete(int id) {
                Toast.makeText(getActivity(), "Загрузка завершена", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadFailed(int id, int errorCode, String errorMessage) {
            }

            @Override
            public void onProgress(int id, long totalBytes, int progress) {

            }
        };
        downloadRequest.setDownloadListener(downloadListener);
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
