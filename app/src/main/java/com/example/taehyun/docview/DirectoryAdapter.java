package com.example.taehyun.docview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by TaeHyun on 2017-12-28.
 */

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryViewHolder> {
    ArrayList<String > dirs;
    MainActivity main;

    public void setMain(MainActivity main) {
        this.main = main;
    }

    public DirectoryAdapter(ArrayList<String> dirs) {
        this.dirs=dirs;
    }

    @Override
    public DirectoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DirectoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.directory_item,parent,false));
    }

    @Override
    public void onBindViewHolder(DirectoryViewHolder holder, int position){
        holder.dir.setText(dirs.get(position));
        File file = new File(main.getCurrentPath()+"/"+dirs.get(position));
        if(file.isDirectory()==true){
            holder.img.setImageResource(R.drawable.ic_folder_open_black_24dp);
        } else{
            holder.img.setImageResource(R.drawable.ic_image_file_24dp);
        }
    }
    @Override
    public int getItemCount() {
        return dirs.size();
    }

    class DirectoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dir) TextView dir;
        @BindView(R.id.image) ImageView img;

        public DirectoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.dir) void click(View v){
            File file = new File(main.getCurrentPath()+"/"+dir.getText());
            if(file.isDirectory()==true){
                main.getDirs(dir.getText().toString());
            } else {
                if (dir.getText().toString().endsWith(".txt") == true) {


                } else {
                    Toast.makeText(v.getContext(), "Can't read", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
