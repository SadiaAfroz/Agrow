package com.example.agrow.model;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

public class singleCart extends AppCompatActivity {
    private Bitmap mImageResource;
    private String cartid;
    private String mText1;
    private String mText2;
    private String mText3;
    public singleCart(Bitmap imageResource, String text1, String text2, String text3, String id) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mText3 = text3;
        cartid = id;
    }



    public void changeText1(String text) {
        mText1 = text;
    }

    public void changeBitmap(Bitmap img) {
        mImageResource = img;
    }


    public Bitmap getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public String getText3() {
        return mText3;
    }

    public String getcartid(){
        return cartid;
    }

}
