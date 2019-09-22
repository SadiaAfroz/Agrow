package com.example.agrow.controller;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class phnnumchange extends AppCompatActivity {
    EditText oldphnnum,newphnnum;
    Button changephnnum;
    String oldphn,newphn,newpass2;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phnnumchange);

        final String id= SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        progressDialog = new ProgressDialog(this);
        oldphnnum=(EditText)findViewById(R.id.oldphnnum);
        newphnnum=(EditText)findViewById(R.id.newphnnum);
        final String phnnum=MainActivity.getphnnum();
        changephnnum=(Button) findViewById(R.id.changephnnumbtn);
        changephnnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldphn=oldphnnum.getText().toString();
                newphn=newphnnum.getText().toString();

                progressDialog.setMessage("Updating mobile no...");
                progressDialog.show();

                if(oldphn.isEmpty()){
                    oldphnnum.setError("Current mobile no is required");
                    oldphnnum.requestFocus();
                }
                else if(newphn.isEmpty()){
                    newphnnum.setError("New mobile no is required");
                    newphnnum.requestFocus();
                }
                else if(!(oldphn.matches(phnnum))){
                    oldphnnum.setError("Current mobile no does not match");
                    oldphnnum.requestFocus();
                }
                String urlString= Api.BASE_URL+"update_mobileno";
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        urlString,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                System.out.println(response);



                                if(response.contains("201")){
                                    Toast.makeText(getApplicationContext(), "Mobile no updated successfully", Toast.LENGTH_LONG).show();


                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Could not update mobile no", Toast.LENGTH_LONG).show();




                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("id", id);
                        params.put("mobileno",newphn);
                        return params;
                    }
                };


                RequestHandler.getInstance(phnnumchange.this).addToRequestQueue(stringRequest);



            }
        });


    }
}
