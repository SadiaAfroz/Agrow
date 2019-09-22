package com.example.agrow.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agrow.R;
import com.example.agrow.model.Review;

import java.util.List;



public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the reviews in a list
    private List<Review> reviewList;

    //getting the context and review list with constructor
    public ReviewAdapter(Context mCtx, List<Review> reviewList) {
        this.mCtx = mCtx;
        this.reviewList = reviewList;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_reviews, null);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        //getting the review of the specified position
        Review review = reviewList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(review.getTitle());
        holder.textViewShortDesc.setText(review.getShortdesc());

    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
        }
    }
}