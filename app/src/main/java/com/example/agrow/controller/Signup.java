package com.example.agrow.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.agrow.Api;
import com.example.agrow.R;
import com.example.agrow.RequestHandler;

import java.util.HashMap;
import java.util.Map;


public class Signup extends AppCompatActivity {
    private String fullname,mobnum,password,division,interest,gender,description;
    private ProgressDialog progressDialog;

    private EditText edittext_fullname,editText_mobnum,editText_password,editText_description;
    private Spinner spinner_division,spinner_interest;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Api api;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup Form");
        progressDialog = new ProgressDialog(this);

        edittext_fullname=(EditText) findViewById(R.id.fullname_input);
        editText_mobnum=(EditText) findViewById(R.id.mobnum_input);
        editText_password=(EditText) findViewById(R.id.password_input);
        editText_description=(EditText) findViewById(R.id.description_input);
        radioGroup =(RadioGroup) findViewById(R.id.radio);

        registerButton=(Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname=edittext_fullname.getText().toString();
                mobnum=editText_mobnum.getText().toString();
                password=editText_password.getText().toString();
                int selectid=radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectid);
                gender= (String) radioButton.getText();
                if(mobnum.isEmpty()){
                    editText_mobnum.setError("Phone Number is required");
                    editText_mobnum.requestFocus();
                }
                if(mobnum.length()!=11){
                    Toast.makeText(getApplicationContext(), "Enter valid mobile number", Toast.LENGTH_LONG).show();
                }
                else
                {registerUser();}
                //createPost();
//                division=divisionSpinner.getSelectedItem().toString();
//                interest=interestedasSpinner.getSelectedItem().toString();

            }
        });

    }



    private void registerUser() {

        mobnum=editText_mobnum.getText().toString();
        password=editText_password.getText().toString();
        description=editText_description.getText().toString();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        System.out.println(mobnum+" "+password);
        String urlString=Api.BASE_URL+"info_enter";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlString,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response);
                        // try {

                        //JSONObject jsonObject = new JSONObject(response);

                        if(response.contains("201")){
                            Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
                            openActivity2();

                        }
                        else
                            Toast.makeText(getApplicationContext(), "Mobile number already exists", Toast.LENGTH_LONG).show();


                        //} catch (JSONException e) {
                        //  e.printStackTrace();
                        //}
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", fullname);
                params.put("password", password);
                params.put("mobileno", mobnum);
                params.put("gender", gender);
                params.put("description", description);
                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);



    }

    public void openActivity2(){

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
