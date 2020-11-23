package com.example.androiduberremake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMessage extends RecyclerView.Adapter<holderMessage> {

    private List<Message> listMessages = new ArrayList<>();
    private Context c;

    public AdapterMessage(Context c) {
        this.c = c;
    }

    public void addMessages(Message m){
        listMessages.add(m);
        notifyItemInserted(listMessages.size());
    }

    @NonNull
    @Override
    public holderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_message, parent, false);
        return new holderMessage(v);
    }

    @Override
    public void onBindViewHolder(@NonNull holderMessage holder, int position) {
        holder.getName().setText(listMessages.get(position).getName());
        holder.getMessage().setText(listMessages.get(position).getMessageRecived());
    }

    @Override
    public int getItemCount() {
        return listMessages.size();
    }
}
