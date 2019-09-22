package com.example.agrow.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agrow.R;
import com.example.agrow.model.singleCart;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<singleCart> mCartList;
    private CartAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(CartAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
       // public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public ImageView mDeleteImage;

        public CartViewHolder(View itemView, final CartAdapter.OnItemClickListener listener) {
            super(itemView);
         //   mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mTextView3 = itemView.findViewById(R.id.textView3);
            mDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
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
        }
    }

    public CartAdapter(ArrayList<singleCart> cartList) {
        mCartList = cartList;
    }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_cart, parent, false);
        CartAdapter.CartViewHolder evh = new CartAdapter.CartViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(CartAdapter.CartViewHolder holder, int position) {
        singleCart currentItem = mCartList.get(position);

        //holder.mImageView.setImageBitmap(currentItem.getImageResource());
        System.out.println(currentItem.getImageResource()+"kkkk");
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.mTextView3.setText(currentItem.getText3());
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }
}
