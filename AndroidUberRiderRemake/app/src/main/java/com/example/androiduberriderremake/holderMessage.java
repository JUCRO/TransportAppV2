package com.example.androiduberriderremake;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class holderMessage extends RecyclerView.ViewHolder {
    private TextView name, message, time;
    private CircleImageView imgMessage;

    public holderMessage(@NonNull View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        message = (TextView) itemView.findViewById(R.id.messageRecived);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public CircleImageView getImgMessage() {
        return imgMessage;
    }

    public void setImgMessage(CircleImageView imgMessage) {
        this.imgMessage = imgMessage;
    }
}
