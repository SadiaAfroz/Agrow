package com.example.agrow.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.agrow.R;

public class Userhome extends AppCompatActivity {
    CardView search,login,viewpro,signup,agrotravel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
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
                openAddProductActivity();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotActivity();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserActivity();
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
                openCartActivity();
            }
        });
    }


    public void opensearchActivity(){

        Intent intent=new Intent(this, search2.class);
        startActivity(intent);
    }

    public void openNotActivity(){

        Intent intent=new Intent(this, Notification.class);
        startActivity(intent);
    }
    public void openAddProductActivity(){

        Intent intent=new Intent(this, addproduct.class);
        startActivity(intent);
    }


    public void openUserActivity(){

        Intent intent=new Intent(this, Userwindow.class);

        startActivity(intent);
    }

    public void openShowProductActivity(){

        Intent intent=new Intent(this, showproduct.class);
        startActivity(intent);
    }
    public void openCartActivity(){

        Intent intent=new Intent(this, finalCart.class);
        startActivity(intent);
    }
}
