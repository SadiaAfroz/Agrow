package com.example.agrow.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import com.android.volley.Response;
import com.example.agrow.Api;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;
import com.example.agrow.SharedPrefManager;
;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button nextActivity,register;
    private EditText editText_mobnum,editText_password;
    static String mobnum,password;
    TextView passwordtoogle;
    int setPtype=1;
    private ProgressDialog progressDialog;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("LOGINFUNCs","START");

        progressDialog = new ProgressDialog(this);

        editText_mobnum=(EditText) findViewById(R.id.enter_phone);
        editText_password=(EditText) findViewById(R.id.enter_pass);

        passwordtoogle=(TextView)findViewById(R.id.passwordtextview);
        passwordtoogle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setPtype==1){
                    setPtype=0;
                    editText_password.setTransformationMethod(null);
                    if(editText_password.getText().length()>0){
                        editText_password.setSelection(editText_password.getText().length());

                    }
                    passwordtoogle.setBackgroundResource(R.drawable.ic_panorama_fish_eye_black_24dp);
                }
                else{
                    setPtype=1;
                    editText_password.setTransformationMethod(new PasswordTransformationMethod());
                    if(editText_password.getText().length()>0){
                        editText_password.setSelection(editText_password.getText().length());

                    }
                    passwordtoogle.setBackgroundResource(R.drawable.ic_remove_red_eye_black_24dp);
                }

            }
        });
        nextActivity=(Button) findViewById(R.id.login_button);
        nextActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mobnum=editText_mobnum.getText().toString();
                password=editText_password.getText().toString();

                if(mobnum.isEmpty()){
                    editText_mobnum.setError("Phone Number is required");
                    editText_mobnum.requestFocus();
                }
                else if(password.isEmpty()){
                    editText_password.setError("Password is required");
                    editText_password.requestFocus();
                }
                else {
                    userLogin();
                }



            }
        });

        register=(Button)findViewById(R.id.register_button);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Signup.class);
                openActivity2();
            }

        } );


    }

    private void userLogin(){

        mobnum=editText_mobnum.getText().toString();
        password=editText_password.getText().toString();

        progressDialog.setMessage("Logging in user...");
        progressDialog.show();
        System.out.println(mobnum+" "+password);
        String urlString=Api.BASE_URL+"login_verify";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        String[] responses;
                        if(response.contains("["))
                        {
                            responses=response.split("\\[");
                            response="["+responses[1];

                        }
                        else
                        {
                            responses=response.split("\\{");
                            response="{"+responses[1];
                        }

                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if(response.contains("201")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                jsonObject.getString("id"),
                                                jsonObject.getString("name"),
                                                jsonObject.getString("mobileno")
                                        );



                                Toast.makeText(getApplicationContext(), "User login successful", Toast.LENGTH_LONG).show();

                                openHomeActivity();

                            }
                            else
                                Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                params.put("mobileno", mobnum);
                params.put("password", password);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void openActivity2(){

        Intent intent=new Intent(this, Signup.class);
        startActivity(intent);
    }


    public void openHomeActivity(){

        //Intent intent=new Intent(this, Home.class);
        Intent intent=new Intent(this, Userhome.class);
        startActivity(intent);
    }
    public static String getpassword()
    {
        return password;
    }
    public static String getphnnum()
    {
        return mobnum;
    }

}
