package com.example.agrow.controller;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.agrow.Api;
import com.example.agrow.model.Product;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;
import com.example.agrow.adapter.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class search2 extends AppCompatActivity {
    SearchView mysearchview;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mysearchview=(SearchView)findViewById(R.id.searchview);
        //initializing the productlist
        productList = new ArrayList<>();

        mysearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.setAdapter(null);
                getProducts(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        //getProducts();



    }


    private void getProducts(final String key){
        String urlString= Api.BASE_URL+"search_ad";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println(key);
                        productList.clear();

                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                productList.add(
                                        new Product(
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
                                        ));
                            }

                            ProductAdapter adapter = new ProductAdapter(search2.this, productList);




                            recyclerView.setAdapter(adapter);

                            //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

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
                params.put("key", key);

                return params;
            }
        };


        RequestHandler.getInstance(search2.this).addToRequestQueue(stringRequest);


    }

}
