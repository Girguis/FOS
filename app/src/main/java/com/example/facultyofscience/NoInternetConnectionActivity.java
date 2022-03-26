package com.example.facultyofscience;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NoInternetConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection_layout);
        getSupportActionBar().hide();
    }
}