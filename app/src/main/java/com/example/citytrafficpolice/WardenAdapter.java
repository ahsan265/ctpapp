package com.example.citytrafficpolice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WardenAdapter extends RecyclerView.Adapter<WardenAdapter.WardenViewHolder> {

    private String[] data;
    public WardenAdapter (String [] data)
    {
        this.data = data;
    }

    @NonNull
    @Override
    public WardenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_warden_data, parent, false);
        return new WardenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WardenViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class WardenViewHolder extends RecyclerView.ViewHolder
        {
            public WardenViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        }
}
