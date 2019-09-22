package com.example.agrow.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.agrow.R;


public class FirstActivity extends AppCompatActivity {
    CardView search,login,viewpro,signup,agrotravel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();
        search=findViewById(R.id.searchcard);
        login=findViewById(R.id.logincard);
        signup=findViewById(R.id.signupcard);
        agrotravel=findViewById(R.id.agrotravelcard);
        viewpro=findViewById(R.id.viewproductcard);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensearchActivity();
            }
        });
        viewpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShowProductActivity();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openregisterActivity();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginActivity();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("search");
                opensearchActivity();
            }
        });
        agrotravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAgrowTravelActivity();

            }
        });
    }



    public void opensearchActivity(){

        Intent intent=new Intent(this, search2.class);
        startActivity(intent);
    }
    public void openloginActivity(){

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openAddProductActivity(){

        Intent intent=new Intent(this, addproduct.class);
        startActivity(intent);
    }

    public void openregisterActivity(){

        Intent intent=new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void openShowProductActivity(){

        Intent intent=new Intent(this, showproduct.class);
        startActivity(intent);
    }
    public void openLocationActivity(){
        Intent intent=new Intent(this, CurrentLocationActivity.class);
        startActivity(intent);
    }

    public void openFinalCartActivity(){
        Intent intent=new Intent(this, ReviewAll.class);
        startActivity(intent);
    }

    public void openNotificationActivity(){
        Intent intent=new Intent(this, Notification.class);
        startActivity(intent);
    }
    public void openAgrowTravelActivity(){

        Intent intent=new Intent(this, AgroTravelall.class);
        startActivity(intent);
    }

    public void openWebviewActivity(){

        Intent intent=new Intent(this, Eachagrotravel.class);
        startActivity(intent);
    }
}
