package com.example.fly.imageloagutil;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_adapter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder mholder = (MyViewHolder) holder;
            mholder.mTextView.setText("京城");
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.text);
        }
    }
}
