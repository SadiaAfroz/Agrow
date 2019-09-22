package com.example.agrow;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static  SharedPrefManager mInstance;
    private static Context mctx;

    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_NAME="username";
    private static final String KEY_PHONENO="userphoneno";
    private static final String KEY_ID="id";

    private SharedPrefManager(Context context){
        mctx=context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String id, String username, String phoneno){
        SharedPreferences sharedPreferences =mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(KEY_ID, id);
        editor.putString(KEY_NAME, username);
        editor.putString(KEY_PHONENO,phoneno);

        editor.apply();

        return true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences= mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        if(sharedPreferences.getString(KEY_NAME,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
    }


    public String getMobileno(){
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONENO,null);
    }


    public String getUserID(){
        SharedPreferences sharedPreferences=mctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);
    }


}
/*              SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));


                                if(!SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()){
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }

                                SharedPrefManager.getInstance(getApplicationContext()).getUserID();
                                SharedPrefManager.getInstance(getApplicationContext()).getUsername();
                                SharedPrefManager.getInstance(getApplicationContext()).getMobileno();



*/
