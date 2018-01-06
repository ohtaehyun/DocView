package com.example.taehyun.docview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.FileUriExposedException;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //path
    private String rootPath;
    private String currentPath;

    //adapter
    private DirectoryAdapter adapter;
    private ArrayList<String> dirs;

    //layout
    @BindView(R.id.directory_view) RecyclerView directory_view;
    @BindView(R.id.bottom_nav) BottomNavigationView bottom_nav;
    @BindView(R.id.current_path) TextView current_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void init(){

        //permission check
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }

        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        currentPath = rootPath;
        current_path.setText(currentPath);
        dirs = new ArrayList<String>();
        initDirs();
        adapter = new DirectoryAdapter(dirs);
        adapter.setMain(this);
        directory_view.setAdapter(adapter);
        directory_view.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initDirs(){
        dirs.add("..");
        File file = new File(rootPath);
        String[] files = file.list();
        if(files!=null) {
            for(int index = 0 ; index<files.length;index++){
                if(files[index].startsWith(".")){

                }else {
                    dirs.add(files[index]);
                }
            }
        }
        Collections.sort(dirs);
    }

    public void getDirs(String path){
        if(path.equals("..")){
            if(currentPath.equals(rootPath)){
                return;
            }
            int end =currentPath.lastIndexOf("/");
            currentPath=currentPath.substring(0,end);
            current_path.setText(currentPath);
        }else {
            currentPath = currentPath + "/" + path;
            current_path.setText(currentPath);
        }
        dirs.clear();
        dirs.add("..");
        File file = new File(currentPath);
        String[] files = file.list();
        if(files!=null) {
            for(int index = 0 ; index<files.length;index++){
                if(files[index].startsWith(".")){

                }else {
                    dirs.add(files[index]);
                }
            }
        }
        Collections.sort(dirs);
        adapter.notifyDataSetChanged();
    }

    public String getCurrentPath(){
        return  currentPath;
    }
}
