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

public class passwordChange extends AppCompatActivity {
    EditText oldpassword,newpassword,newpassword2;
    Button changepass;
    String oldpass,newpass,newpass2;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String id= SharedPrefManager.getInstance(getApplicationContext()).getUserID();
        setContentView(R.layout.activity_password_change);
        progressDialog = new ProgressDialog(this);
        oldpassword=(EditText)findViewById(R.id.oldpassword);
        newpassword=(EditText)findViewById(R.id.newpassword);
        newpassword2=(EditText) findViewById(R.id.newpassword2);
        final String password=MainActivity.getpassword();
        changepass=(Button) findViewById(R.id.changepasswordbtn);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldpass=oldpassword.getText().toString();
                newpass=newpassword.getText().toString();
                newpass2=newpassword2.getText().toString();

                progressDialog.setMessage("Updating password...");
                progressDialog.show();

                if(oldpass.isEmpty()){
                    oldpassword.setError("Current password is required");
                    oldpassword.requestFocus();
                }
                else if(newpass.isEmpty()){
                    newpassword.setError("New password is required");
                    newpassword.requestFocus();
                }
                else if(newpass2.isEmpty()){
                    newpassword2.setError("New password is required");
                    newpassword2.requestFocus();
                }
                else if(!(oldpass.matches(password))){
                    oldpassword.setError("Current password doesnot match");
                    oldpassword.requestFocus();
                }
                else if(!(newpass.matches(newpass2))){
                    newpassword2.setError("Passwords does not match");
                    newpassword2.requestFocus();
                }

                String urlString= Api.BASE_URL+"update_password";
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        urlString,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                System.out.println(response);



                                    if(response.contains("201")){
                                        Toast.makeText(getApplicationContext(), "Password updated successfully", Toast.LENGTH_LONG).show();


                                    }
                                    else
                                        Toast.makeText(getApplicationContext(), "Could not update password", Toast.LENGTH_LONG).show();




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
                        params.put("password",newpass);
                        return params;
                    }
                };


                RequestHandler.getInstance(passwordChange.this).addToRequestQueue(stringRequest);



            }
        });
    }
}
