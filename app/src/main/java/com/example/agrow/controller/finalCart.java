package com.example.agrow.controller;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.agrow.adapter.CartAdapter;
import com.example.agrow.model.singleCart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class finalCart extends AppCompatActivity {

    private ArrayList<singleCart> mCartList;

    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Bitmap imagebitmap;
    private Button buttonOrder;
    private EditText editTextInsert;
    private EditText editTextRemove;
    private ProgressDialog progressDialog;
    private Product product;
    private Product[] products;
    private String[] orderfrom,orderto;
    String cartpos,userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_cart);
        buttonOrder=findViewById(R.id.button_order);
        progressDialog = new ProgressDialog(this);
        userid= SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        createExampleList();
       // buildRecyclerView();
        //setButtons();
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderfinal();
            }
        });
    }

    private void orderfinal() {
        progressDialog.setMessage("Placing order...");
        progressDialog.show();
        String urlString= Api.BASE_URL+"order_place";
        for(int i=0;i<mCartList.size();i++){
            final int finalI = i;
            if(i==mCartList.size()-1) progressDialog.dismiss();
            cartpos=mCartList.get(finalI).getcartid();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response+"k");
                        if(finalI==mCartList.size()-1) Toast.makeText(getApplicationContext(),"Order placed successfully", Toast.LENGTH_LONG).show();



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
                params.put("cartid", cartpos);


                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    }
    /* params.put("adid", products[finalI].getId());
                params.put("buyerid", userid);
                params.put("sellerid", products[finalI].getSellerid());
                params.put("orderfrom",orderfrom[finalI]);
                params.put("orderto",orderto[finalI]);
                params.put("amount",mCartList.get(finalI).getText2());
                params.put("cost",mCartList.get(finalI).getText3());
                params.put("status", "0");
                params.put("date", userid);
*/

    private void orderfinal2() {
        String urlString=Api.BASE_URL+"get_product";
        for(int i=0;i<mCartList.size();i++){

            final int finalI = i;
            cartpos=String.valueOf(finalI);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    urlString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response+"abc");

                            try {
                                JSONArray jsonArray=new JSONArray(response);

                                for(int i=0;i<jsonArray.length();i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    products[i] = new Product(
                                            jsonObject.getString("adid"),
                                            jsonObject.getString("title"),
                                            Float.parseFloat(jsonObject.getString("unit_cost")),
                                            Float.parseFloat(jsonObject.getString("amount")),
                                            jsonObject.getString("description"),
                                            jsonObject.getString("sellerid"),
                                            jsonObject.getString("division"),
                                            jsonObject.getString("district"),
                                            jsonObject.getString("upazila"),
                                            jsonObject.getString("unionloc"),
                                            jsonObject.getString("available_date"),
                                            jsonObject.getString("expiry_date"),
                                            R.drawable.userpic,
                                            jsonObject.getDouble("latitude"),
                                            jsonObject.getDouble("longitude")
                                    );
                                    System.out.println(product.getTitle()+"999");

                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }


                            //setting adapter to recyclerview


                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

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
                    params.put("cartid",cartpos);

                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }
        for(int i=0;i<mCartList.size();i++) {
            orderfrom[i]=products[i].getUnionloc()+","+products[i].getUpazila()+","+products[i].getDistrict()+","+products[i].getDivision();

        }
            orderfinal();

    }

    public void insertItem(int position) {
        //mCartList.add(position, new singleCart(R.drawable.ic_action_search, "New Item At Position" + position, "This is Line 2","Total price","1"));
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(final int position) {
        System.out.println(mCartList.get(position).getcartid()+"bb");
        cartpos=mCartList.get(position).getcartid();
        String urlString=Api.BASE_URL+"delete_from_cart";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response+"k");
                        // Toast.makeText(getApplicationContext(),"Order placed successfully", Toast.LENGTH_LONG).show();



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
                params.put("cartid",cartpos);

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



        mCartList.remove(position);
        mAdapter.notifyItemRemoved(position);
        Toast.makeText(getApplicationContext(),"Removed from cart", Toast.LENGTH_LONG).show();




    }
        public void dialoguebox(final int position){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to remove this product from cart?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            removeItem(position);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for(int j=0;j<mCartList.size();j++){
                                System.out.println(mCartList.get(j).getcartid());
                            }
                            System.out.println(mCartList.get(position).getcartid());
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }

        public  void gettoeachadvertise(final int position){
            String urlString=Api.BASE_URL+"get_product";
            cartpos=mCartList.get(position).getcartid();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    urlString,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response+"abc");

                            try {
                                JSONArray jsonArray=new JSONArray(response);

                            for(int i=0;i<jsonArray.length();i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                product = new Product(
                                        jsonObject.getString("adid"),
                                        jsonObject.getString("title"),
                                        Float.parseFloat(jsonObject.getString("unit_cost")),
                                        Float.parseFloat(jsonObject.getString("amount")),
                                        jsonObject.getString("description"),
                                        jsonObject.getString("sellerid"),
                                        jsonObject.getString("division"),
                                        jsonObject.getString("district"),
                                        jsonObject.getString("upazila"),
                                        jsonObject.getString("unionloc"),
                                        jsonObject.getString("available_date"),
                                        jsonObject.getString("expiry_date"),
                                        R.drawable.userpic,
                                        jsonObject.getDouble("latitude"),
                                        jsonObject.getDouble("longitude")
                                );
                                System.out.println(product.getTitle()+"999");
                                newIntentGo(product);
                            }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }


                            //setting adapter to recyclerview


                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

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
                    params.put("cartid",cartpos);

                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);




            //



        }

    private void newIntentGo(Product product) {

        Intent intent=new Intent(this, addtocart.class);
        intent.putExtra("productObject", product);
        this.startActivity(intent);
    }

    public void changeItem(int position, String text) {
        mCartList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void createExampleList() {
      /*  mCartList = new ArrayList<>();
        mCartList.add(new singleCart(R.drawable.ic_action_search, "Line 1", "Line 2", "89/-","2"));
        mCartList.add(new singleCart(R.drawable.ic_action_search, "Line 3", "Line 4","107/-","3"));
        mCartList.add(new singleCart(R.drawable.ic_action_search, "Line 5", "Line 6","110/-","4"));
        */
        String urlString=Api.BASE_URL+"search_cart";
        mCartList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);


                                String base=Api.BASE_IMAGEURL+"uploads/";
                                String addidImage=jsonObject.getString("adid");
                                String url=base+addidImage+".jpg";
                                System.out.println(url);
                                imagebitmap=getImage(url);
                                System.out.println(imagebitmap+"yyyy");
                                mCartList.add(
                                        new singleCart(
                                                imagebitmap,
                                                jsonObject.getString("title"),
                                                jsonObject.getString("amount")+" kg",
                                                jsonObject.getString("cost"),
                                                jsonObject.getString("cartid"))

                                        );
                                //orderto[i]=jsonObject.getString("district")+","+jsonObject.getString("division");
                            }
                           buildRecyclerView();

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                params.put("buyerid",SharedPrefManager.getInstance(getApplicationContext()).getUserID());


                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private Bitmap getImage(String url) {
        final Bitmap[] ret = new Bitmap[1];
        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
               imagebitmap=response;
               ret[0] =response;
                System.out.println(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //imgRequest.setTag(CANCELABLE_REQUEST_TAG);
        RequestHandler.getInstance(this).getRequestQueue().add(imgRequest);
        return ret[0];

    }


    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CartAdapter(mCartList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                gettoeachadvertise(position);
            }

            @Override
            public void onDeleteClick(final int position) {
              dialoguebox(position);

            }
        });
    }


}
