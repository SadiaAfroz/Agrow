package com.example.agrow.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.agrow.Api;
import com.example.agrow.model.Product;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;
import com.example.agrow.SharedPrefManager;

import java.util.HashMap;
import java.util.Map;

public class eachadvertise extends AppCompatActivity {

    Product product;
    private final String CANCELABLE_REQUEST_TAG = "volleyImageRequest";
    TextView titleTextview,unitcostTextview,amountTextview,descriptionTextview,availabledateTextview,expirydateTextview,divisionTextview,districtTextview;
    ImageView imgRes;
    private ProgressDialog progressDialog;
    Button addtocart;
    double rating;
    String review,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachadvertise);


        progressDialog = new ProgressDialog(this);
        userid= SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        titleTextview=findViewById(R.id.title);
        unitcostTextview=findViewById(R.id.unitcostvalue);
        amountTextview=findViewById(R.id.amountvalue);
        descriptionTextview=findViewById(R.id.description);
        availabledateTextview=findViewById(R.id.availabledatevalue);
        expirydateTextview=findViewById(R.id.expiryDatevalue);
        divisionTextview=findViewById(R.id.divisionvalue);
        districtTextview=findViewById(R.id.districtvalue);
        imgRes=findViewById(R.id.productimage);
        addtocart=findViewById(R.id.addcartbtn);
        final RatingBar ratingRatingBar = (RatingBar) findViewById(R.id.rating_rating_bar);
        final EditText reviewtext=(EditText) findViewById(R.id.editTextreview);
        Button submitButton = (Button) findViewById(R.id.submit_button);
        Button reviewall = (Button) findViewById(R.id.View_Reviews_button);

        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("productObject");

        titleTextview.setText(product.getTitle());
        unitcostTextview.setText(String.valueOf(product.getUnitcost()));
        amountTextview.setText(String.valueOf(product.getAmount()));
        descriptionTextview.setText(product.getDescription());
        availabledateTextview.setText(product.getAvailableDate());
        expirydateTextview.setText(product.getExpiryDate());
        divisionTextview.setText(product.getDivision());
        districtTextview.setText(product.getDistrict());

        //imgRes.setImageResource(R.drawable.userpic);
        String base= Api.BASE_IMAGEURL+"uploads/";
        String addidImage=product.getId();
        String url=base+addidImage+".jpg";
        System.out.println(url);

        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgRes.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //imgRequest.setTag(CANCELABLE_REQUEST_TAG);
        RequestHandler.getInstance(eachadvertise.this).getRequestQueue().add(imgRequest);

       // imgRes.setImageResource(R.drawable.userpic);


        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(eachadvertise.this, com.example.agrow.controller.addtocart.class);
                intent.putExtra("productObject", product);
                eachadvertise.this.startActivity(intent);
            }
        });

       submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rating=ratingRatingBar.getRating();
                review=reviewtext.getText().toString();
                if(!review.isEmpty())addreview();
                addrating();

            }
        });

       reviewall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(eachadvertise.this, ReviewAll.class);
               intent.putExtra("productObject", product);
               eachadvertise.this.startActivity(intent);
           }
       });
    }

    public void addreview() {
        String urlString = Api.BASE_URL + "review";
        System.out.println(review);
        System.out.println(rating);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response + "k");
                        //Toast.makeText(getApplicationContext(), "Rated product successfully", Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("adid", product.getId());
                params.put("sellerid", userid);
                params.put("review", review);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void addrating()
    {
        progressDialog.setMessage("Rating product...");
        progressDialog.show();
        String urlString=Api.BASE_URL+"rating";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response+"k");
                        Toast.makeText(getApplicationContext(),"Rated product successfully", Toast.LENGTH_LONG).show();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("adid", product.getId());
                params.put("sellerid", userid);
                params.put("rating", String.valueOf(rating));
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}
