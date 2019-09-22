package com.example.agrow.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agrow.R;
import com.example.agrow.model.singleNotification;

import java.util.List;

public class notificationAdapter  extends RecyclerView.Adapter<notificationAdapter.NotificationViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<singleNotification> notificationList;

    /*
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }
    */

    //getting the context and product list with constructor
    public notificationAdapter(Context mCtx, List<singleNotification> notificationList) {
        this.mCtx = mCtx;
        this.notificationList = notificationList;
    }


    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.notification_item, null);
        return new notificationAdapter.NotificationViewHolder(view);    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        //getting the product of the specified position
        final singleNotification notification = notificationList.get(position);



        //binding the data with the viewholder views
        holder.buyername.setText(notification.getBuyername());
        holder.addid.setText(notification.getAddid());
        holder.buyeraddress.setText(notification.getOrderfrom());
        holder.amount.setText(notification.getAmount()+" kg");
        holder.cost.setText(notification.getCost()+" tk");

    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView buyername,cost, amount,addid, buyeraddress;
        ImageView accept,reject;
        LinearLayout linearlay;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            linearlay=itemView.findViewById(R.id.linlay);
            buyername = itemView.findViewById(R.id.buyername);
            buyeraddress=itemView.findViewById(R.id.buyeraddress);
            cost = itemView.findViewById(R.id.cost);
            amount = itemView.findViewById(R.id.amount);
            addid = itemView.findViewById(R.id.addid);

            /*
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            */


        }
    }

}
