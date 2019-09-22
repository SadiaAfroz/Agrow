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


import com.example.agrow.model.Product;
import com.example.agrow.R;
import com.example.agrow.controller.eachadvertise;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Product> productList;

    //getting the context and product list with constructor
    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        final Product product = productList.get(position);

        //location Set
        String location="";
        location=product.getUnionloc().isEmpty()?"": (product.getUnionloc()+",");
        location+=product.getUpazila().isEmpty()?"": (product.getUpazila()+",");
        location+=product.getDistrict().isEmpty()?"": (product.getDistrict()+",");
        location+=product.getDivision().isEmpty()?"": (product.getDivision());

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getTitle());
        holder.textViewLocation.setText(location);
        holder.textViewAmount.setText(String.valueOf(product.getAmount()));
        holder.textViewPrice.setText(String.valueOf(product.getUnitcost()));

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        holder.linlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Individual advertise showing
                Intent intent=new Intent(mCtx, eachadvertise.class);
                intent.putExtra("productObject", product);
                mCtx.startActivity(intent);


            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewLocation, textViewAmount, textViewPrice;
        ImageView imageView;
        LinearLayout linlay;

        public ProductViewHolder(View itemView) {
            super(itemView);
            linlay=itemView.findViewById(R.id.linlay);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewAmount = itemView.findViewById(R.id.textViewAmount);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }


}