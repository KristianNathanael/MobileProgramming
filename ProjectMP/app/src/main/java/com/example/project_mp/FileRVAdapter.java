package com.example.project_mp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FileRVAdapter extends RecyclerView.Adapter<FileRVAdapter.ViewHolder>{
    private static final String TAG = "FileRVAdapter";

    private ArrayList<String> FileNameList = new ArrayList<>();
    private ArrayList<String> FileUIDList = new ArrayList<>();
    private Context icontext;
    private FirebaseAuth FBauth;
    private ArrayList<String> bgColor = new ArrayList<>();

    public FileRVAdapter(Context icon,ArrayList<String> fileNameList,ArrayList<String> fileUIDList){
        FileNameList = fileNameList;
        icontext = icon;
        FileUIDList = fileUIDList;
        FBauth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"oncreate");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        bgColor.add("#FFB4B4");
        bgColor.add("#FCFFB4");
        bgColor.add("#C7FFB4");
        bgColor.add("#B4FFE1");
        bgColor.add("#B4C3FF");
        bgColor.add("#EBB4FF");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBind");
        holder.fileName.setText(FileNameList.get(position));
        holder.fileName.setBackgroundColor(Color.parseColor(bgColor.get(position)));
        Log.d(TAG, "onBindViewHolder: Color " + bgColor);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(icontext, "Click2", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + FileNameList.get(position));
                Intent intent = new Intent(icontext, FileView.class);
                intent.putExtra("FileName", FileNameList.get(position));
                intent.putExtra("FileColor",bgColor.get(position));
                icontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return FileNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parentLayout;
        TextView fileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout=itemView.findViewById(R.id.ListLayout);
            fileName=itemView.findViewById(R.id.TVFileNameItem);
        }
    }
}
