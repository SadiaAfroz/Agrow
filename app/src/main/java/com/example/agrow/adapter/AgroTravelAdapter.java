package com.example.agrow.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.agrow.model.AgroTravel;
import com.example.agrow.controller.Eachagrotravel;
import com.example.agrow.R;

import java.util.List;



public class AgroTravelAdapter extends RecyclerView.Adapter<AgroTravelAdapter.AgroTravelViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the agrotravels in a list
    private List<AgroTravel> agrotravelList;

    //getting the context and agrotravel list with constructor
    public AgroTravelAdapter(Context mCtx, List<AgroTravel> agrotravelList) {
        this.mCtx = mCtx;
        this.agrotravelList = agrotravelList;
    }

    @Override
    public AgroTravelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_agrotravel, null);
        return new AgroTravelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgroTravelViewHolder holder, int position) {
        //getting the agrotravel of the specified position
        final AgroTravel agrotravel = agrotravelList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(agrotravel.getTitle());
        holder.textViewShortDesc.setText(agrotravel.getLocation());
        //holder.textViewPrice.setText(String.valueOf(agrotravel.getPrice()));

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(agrotravel.getImage()));
        holder.linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Individual advertise showing
                Intent intent=new Intent(mCtx, Eachagrotravel.class);
                intent.putExtra("AgroTravelObject", agrotravel);
                mCtx.startActivity(intent);


            }
        });
    }


    @Override
    public int getItemCount() {
        return agrotravelList.size();
    }


    class AgroTravelViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        LinearLayout linlay;

        public AgroTravelViewHolder(View itemView) {
            super(itemView);
            linlay=itemView.findViewById(R.id.linlayagro);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}