package com.example.agrow.controller;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.agrow.Api;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;
import com.example.agrow.SharedPrefManager;
import com.example.agrow.adapter.notificationAdapter;
import com.example.agrow.model.singleNotification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Notification extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<singleNotification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        notificationList = new ArrayList<>();
        getNotifications();

    }


    private void getNotifications(){
        String urlString= Api.BASE_URL+"search_notification_seller";
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

                                notificationList.add(
                                        new singleNotification(
                                                jsonObject.getString("name"),
                                                jsonObject.getString("adid"),
                                                jsonObject.getString("orderfrom"),
                                                jsonObject.getString("amount"),
                                                jsonObject.getString("cost")));
                            }

                            notificationAdapter adapter = new notificationAdapter(Notification.this, notificationList);



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
                params.put("sellerid", SharedPrefManager.getInstance(getApplicationContext()).getUserID());


                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

}
