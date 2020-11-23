package com.example.androiduberriderremake;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androiduberriderremake.models.ListRequests;

import java.util.List;

public class ListRequestAdapter extends RecyclerView.Adapter<ListRequestAdapter.ViewHolder> {
     private List<ListRequests> mData;
     private LayoutInflater mInflater;
     private Context context;

     public ListRequestAdapter(List<ListRequests> itemRequest, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemRequest;
     }

    @Override
     public int getItemCount() {return  mData.size();}

    @Override
     public ListRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
         View view = mInflater.inflate(R.layout.lists_requests, null);
         return new ListRequestAdapter.ViewHolder(view);
     }

    @Override
     public void onBindViewHolder(final ListRequestAdapter.ViewHolder holder, final int position){
         holder.bindData(mData.get(position));
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String idRequestText = holder.idRequest.getText().toString();
                 Intent intent = new Intent(context, DetailRequestActivity.class);
                 intent.putExtra("idRequest", idRequestText);
                 context.startActivity(intent);
             }
         });
     }

     public void setItems(List<ListRequests> items){ mData = items; }

     public class ViewHolder extends RecyclerView.ViewHolder{
         ImageView iconImage;
         TextView nameDriver, status, dateRequest, idRequest;

         ViewHolder(View itemView){
             super(itemView);
             iconImage = itemView.findViewById(R.id.iconImageView);
             nameDriver = itemView.findViewById(R.id.nameDriver);
             status = itemView.findViewById(R.id.statusRequest);
             dateRequest = itemView.findViewById(R.id.dateRequest);
             idRequest = itemView.findViewById(R.id.idRequest);
         }

         void bindData(final ListRequests item){
             nameDriver.setText(item.getNameDriver());
             status.setText(item.getStatus());
             dateRequest.setText(item.getDateRequest());
             idRequest.setText(item.getIdRequest());
         }
     }
}
