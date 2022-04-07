package com.example.facultyofscience.Activities.SuggestionsAndComplaints;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.R;

public class SuggestionsAndComplaints extends AppCompatActivity {
    WebView formWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestions_and_complaints_layout);
        getSupportActionBar().hide();
        formWebView = findViewById(R.id.formWebView);
        formWebView.setWebViewClient(new WebViewClient());
        WebSettings formSettings = formWebView.getSettings();
        formSettings.setJavaScriptEnabled(true);
        formSettings.setAllowContentAccess(true);
        formSettings.setLoadWithOverviewMode(true);
        formSettings.setLoadsImagesAutomatically(true);
        formSettings.setDomStorageEnabled(true);
        formSettings.setAppCacheEnabled(true);
        formWebView.loadUrl("https://forms.office.com/r/HfejjtfynN");
    }

    @Override
    public void onBackPressed() {
        if (formWebView.canGoBack())
            formWebView.goBack();
        else
            super.onBackPressed();
    }
}