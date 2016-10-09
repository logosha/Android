package com.google.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ListFileActivity extends ListActivity {

    private File mCurrentNode = null;
    private File mLastNode = null;
    private File mRootNode = null;
    private ArrayList<File> mFiles = new ArrayList<File>();
    private CustomAdapter mAdapter = null;
    private static final String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/android/android-master";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_file);
        mAdapter = new CustomAdapter(this, R.layout.list_row, mFiles);
        setListAdapter(mAdapter);

        if (savedInstanceState != null) {
            mRootNode = (File)savedInstanceState.getSerializable("root_node");
            mLastNode = (File)savedInstanceState.getSerializable("last_node");
            mCurrentNode = (File)savedInstanceState.getSerializable("current_node");
        }
        refreshFileList();
    }

    private void refreshFileList() {
        if (mRootNode == null) mRootNode = new File(PATH);
        if (mCurrentNode == null) mCurrentNode = mRootNode;
        mLastNode = mCurrentNode;
        File[] files = mCurrentNode.listFiles();
        mFiles.clear();
        mFiles.add(mRootNode);
        mFiles.add(mLastNode);
        if (files!=null) {
            for (int i = 0; i< files.length; i++) mFiles.add(files[i]);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("root_node", mRootNode);
        outState.putSerializable("current_node", mCurrentNode);
        outState.putSerializable("last_node", mLastNode);
        super.onSaveInstanceState(outState);
    }

       @Override
    public void onListItemClick(ListView parent, View v, int position, long id){
        File f = (File) parent.getItemAtPosition(position);
        if (position == 1) {
            if (mCurrentNode.compareTo(mRootNode)!=0) {
                mCurrentNode = f.getParentFile();
                refreshFileList();
            }
        } else if (f.isDirectory()) {
            mCurrentNode = f;
            refreshFileList();
        } else {
            Intent intent = new Intent(this, SourceContentActivity.class);
            String fpath = f.toString();
            intent.putExtra("fpath", fpath);
            Toast.makeText(this, "You selected: "+fpath+"!", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }


}
