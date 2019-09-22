package com.example.agrow.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.agrow.SharedPrefManager;
import com.example.agrow.adapter.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Userwindow extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    Button fruitbutton,vegetablebutton,cropbutton;
    EditText titleEdit,descriptionEdit,unitcostEdit,amountEdit,unionEdit,upzilaEdit;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userwindow);
        fruitbutton=findViewById(R.id.fruitbutton);
        vegetablebutton=findViewById(R.id.vegetablebutton);
        cropbutton=findViewById(R.id.cropbutton);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        String name= SharedPrefManager.getInstance(getApplicationContext()).getUsername();
        String num=SharedPrefManager.getInstance(getApplicationContext()).getMobileno();


        Toolbar toolbar = findViewById(R.id.toolbaruser);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        TextView username=(TextView)headerView.findViewById(R.id.usernametextview);
        TextView mobilenum=(TextView) headerView.findViewById(R.id.mobilenumtextview);

        username.setText(name);
        mobilenum.setText(num);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fruitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                getFruitProducts();
            }
        });
        vegetablebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                getVegetableProducts();
            }
        });

        cropbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setAdapter(null);
                getCropProducts();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigationtab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.editprofile) {
            Intent intent=new Intent(this, passwordChange.class);
            startActivity(intent);
        } else if (id == R.id.phn) {
            Intent intent=new Intent(this, phnnumchange.class);
            startActivity(intent);

        } else if (id == R.id.location) {


        } else if (id == R.id.history) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.help) {

        }else if (id == R.id.rating) {

        }else if (id == R.id.logout) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            finish();
            startActivity(new Intent(this, MainActivity.class));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getFruitProducts(){
        String urlString= Api.BASE_URL+"show_ad_fruits";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
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

                            ProductAdapter adapter = new ProductAdapter(Userwindow.this, productList);



                            //setting adapter to recyclerview
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

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void getVegetableProducts(){
        String urlString=Api.BASE_URL+"show_ad_vegetable";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
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

                            ProductAdapter adapter = new ProductAdapter(Userwindow.this, productList);



                            //setting adapter to recyclerview
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

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    private void getCropProducts(){
        String urlString=Api.BASE_URL+"show_ad_crop";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
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

                            ProductAdapter adapter = new ProductAdapter(Userwindow.this, productList);



                            //setting adapter to recyclerview
                            recyclerView.setAdapter(adapter);


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

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }
}
