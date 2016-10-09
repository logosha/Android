package com.google.app.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.app.ListFileActivity;
import com.google.app.R;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FragmentAbout extends Fragment  {
    Button btnCheck;
    Button btnDownload;
    Button btnUnzip;
    Button btnRead;
    ProgressBar downloadProgress;
    TextView tvProgress;
    ListView lvFiles;

    private static final int DOWNLOAD_THREAD_POOL_SIZE = 5;
    private static final int BUFFER_SIZE = 1024;
    private static final String UNZIP_ARCHIVE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/android.zip";
    private static final String DOWNLOAD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/android/android-master";
    private static final String DOWNLOAD_URI = "https://github.com/logosha/android/archive/master.zip";


    Uri downloadUri = Uri.parse(DOWNLOAD_URI);
    Uri destinationUri = Uri.parse(UNZIP_ARCHIVE);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_about, container, false);
        btnCheck = (Button) v.findViewById(R.id.btnCheck);
        btnDownload = (Button) v.findViewById(R.id.btnDownload);
        btnUnzip = (Button) v.findViewById(R.id.btnUnzip);
        btnRead = (Button) v.findViewById(R.id.btnRead);
        tvProgress = (TextView) v.findViewById(R.id.downloadStatus);
        downloadProgress = (ProgressBar) v.findViewById(R.id.progress_bar);
        lvFiles = (ListView) v.findViewById(R.id.listView1);

        downloadProgress.setMax(100);
        downloadProgress.setProgress(0);
        tvProgress.setText("Press \"PROJECT DOWNLOAD\" for download");


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Toast.makeText(getActivity(), "There is an Internet connection", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        downloadProject();
                    }
                });
            }
        });

        btnUnzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), DOWNLOAD_PATH, Toast.LENGTH_LONG).show();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        unZip(UNZIP_ARCHIVE);
                    }

                });
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListFileActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }


    private void downloadProject() {
        File f = new File(UNZIP_ARCHIVE);
        if (f.exists()){
            f.delete();
        }
        ThinDownloadManager downloadManager = new ThinDownloadManager(DOWNLOAD_THREAD_POOL_SIZE);
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri)
                .setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        tvProgress.setText("Loading is complete");
                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        downloadProgress.setProgress(0);
                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
                        if(progress/10>downloadProgress.getProgress()/10) {
                            downloadProgress.setProgress(progress);
                        }
                    }
                });
        downloadManager.add(downloadRequest);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void unZip(final String zipFileName){
        byte[] buffer = new byte[BUFFER_SIZE];
        final String dstDirectory = destinationDirectory(zipFileName);
        final File dstDir = new File(dstDirectory);
        if (!dstDir.exists()) {
            dstDir.mkdir();
        }
        try {
            final ZipInputStream zis = new ZipInputStream(
                    new FileInputStream(zipFileName));
            ZipEntry ze = zis.getNextEntry();
            String nextFileName;
            while (ze != null) {
                nextFileName = ze.getName();
                File nextFile = new File(dstDirectory + File.separator
                        + nextFileName);

                if (ze.isDirectory()) {
                    nextFile.mkdir();
                } else {
                    new File(nextFile.getParent()).mkdirs();

                    try (FileOutputStream fos
                                 = new FileOutputStream(nextFile)) {
                        int length;
                        while((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                ze = zis.getNextEntry();

            }
            tvProgress.setText("Unzipping is complete");
            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException ex) {
        }
        catch (IOException ex) {
        }
    }

    private String destinationDirectory(final String srcZip) {
        return srcZip.substring(0, srcZip.lastIndexOf("."));
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}