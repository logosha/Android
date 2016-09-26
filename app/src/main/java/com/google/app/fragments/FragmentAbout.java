package com.google.app.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.app.R;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;


public class FragmentAbout extends Fragment {
    Button btnCheck;
    Button btnDownload;
    ProgressBar downloadProgress;
    TextView tvProgress;
    private ThinDownloadManager downloadManager;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 5;
    Uri downloadUri = Uri.parse("https://github.com/logosha/android/archive/master.zip");
    Uri destinationUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/android.zip");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { View v = inflater.inflate(R.layout.fr_about, container, false);

        btnCheck = (Button) v.findViewById(R.id.btnCheck);
        btnDownload = (Button) v.findViewById(R.id.btnDownload);
        tvProgress = (TextView) v.findViewById(R.id.downloadStatus);
        downloadProgress = (ProgressBar) v.findViewById(R.id.progress_bar);
        downloadProgress.setMax(100);
        downloadProgress.setProgress(0);
        tvProgress.setText("Press \"PROJECT DOWNLOAD\" for download project to SD");


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
        downloadManager = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
                DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                                        @Override
                                        public void onDownloadComplete(int id) {
                                            Toast.makeText(getActivity(), "Loading is complete", Toast.LENGTH_LONG).show();
                                            tvProgress.setText("Loading is complete");
                                        }

                                        @Override
                                        public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                                            downloadProgress.setProgress(0);
                                        }

                                        @Override
                                      public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                                            downloadProgress.setProgress(progress);
                                       }
                                    });
        downloadManager.add(downloadRequest);
        downloadManager.release();

    }



    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
