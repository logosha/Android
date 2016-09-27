package com.google.app.fragments;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.app.R;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FragmentAbout extends Fragment {
    Button btnCheck;
    Button btnDownload;
    Button btnRead;
    ProgressBar downloadProgress;
    TextView tvProgress;
    ListView lvFiles;
    private static final int DOWNLOAD_THREAD_POOL_SIZE = 5;
    private static final String UNZIP_ARCHIVE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/android.zip";
    private static final int BUFFER_SIZE = 1024;


    Uri downloadUri = Uri.parse("https://github.com/logosha/android/archive/master.zip");
    Uri destinationUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/android.zip");
    TextView tvFileContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { View v = inflater.inflate(R.layout.fr_about, container, false);

        btnCheck = (Button) v.findViewById(R.id.btnCheck);
        btnDownload = (Button) v.findViewById(R.id.btnDownload);
        btnRead = (Button) v.findViewById(R.id.btnRead);
        tvProgress = (TextView) v.findViewById(R.id.downloadStatus);
        downloadProgress = (ProgressBar) v.findViewById(R.id.progress_bar);
        lvFiles = (ListView)v.findViewById(R.id.listView1);
        tvFileContent = (TextView) v.findViewById(R.id.fileContent);

        downloadProgress.setMax(100);
        downloadProgress.setProgress(0);
        tvProgress.setText("Press \"PROJECT DOWNLOAD\" for download");


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

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unZip(UNZIP_ARCHIVE);
            }
        });

        return v;
}

    private void downloadProject() {
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
                                            downloadProgress.setProgress(progress);
                                       }
                                    });
        downloadManager.add(downloadRequest);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void unZip(final String zipFileName){
        ArrayList<HashMap<String, String>> myArrList = new ArrayList<>();
        HashMap<String, String> map;
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
                map = new HashMap<>();
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
                map.put("File Name", nextFileName);
                map.put("File Path", nextFile.getAbsolutePath());
                myArrList.add(map);
            }

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), myArrList, android.R.layout.simple_list_item_1,
                    new String[] {"File Name", "File Path"},
                    new int[] {android.R.id.text1, android.R.id.text2});
            lvFiles.setAdapter(adapter);

            lvFiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {
                   TextView textView = (TextView) itemClicked;
                    {
                        BufferedReader br;
                        String response = null;

                        try {

                            StringBuilder output = new StringBuilder();
                            String fpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + textView.getText().toString();

                            br = new BufferedReader(new FileReader(fpath));
                            String line = "";
                            while ((line = br.readLine()) != null) {
                                output.append(line +"n");
                            }
                            response = output.toString();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + textView.getText().toString(), Toast.LENGTH_LONG).show();

                        tvFileContent.setText(response);
                    }
                }
            });

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
