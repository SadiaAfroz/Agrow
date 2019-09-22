package com.example.agrow.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.agrow.model.AgroTravel;
import com.example.agrow.Api;
import com.example.agrow.R;

public class Eachagrotravel extends AppCompatActivity {

    private WebView webView;
    AgroTravel agrotravel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eachagrotravel);

        Intent i = getIntent();
        agrotravel = (AgroTravel) i.getSerializableExtra("AgroTravelObject");
//        getExtras().getParcelable(
        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        String url= Api.BASE_HTMLURL +"no-sidebar.html?agrowid="+agrotravel.getId();
        webView.loadUrl(url);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
