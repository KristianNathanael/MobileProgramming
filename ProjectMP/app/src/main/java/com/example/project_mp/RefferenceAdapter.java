package com.example.project_mp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

public class RefferenceAdapter extends RecyclerView.Adapter<RefferenceAdapter.ViewHolder> {
    Context cont;
    List<Refference> listRef;

    public RefferenceAdapter(Context contx){
        this.cont = contx;
        this.listRef = new ArrayList<>();
    }

    public void setListRef(List<Refference> listReff){
        this.listRef = listReff;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cont).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Refference ref = listRef.get(position);
        holder.tvTitle.setText(ref.trackName);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cont,  ref.trackName + " : " + ref.description, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listRef.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvTitle = itemView.findViewById(R.id.TVFileNameItem);
            parentLayout = itemView.findViewById(R.id.ListLayout);
        }
    }
}
