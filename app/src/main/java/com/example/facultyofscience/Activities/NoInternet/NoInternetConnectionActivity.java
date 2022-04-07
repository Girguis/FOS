package com.example.facultyofscience.Activities.NoInternet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.R;

public class NoInternetConnectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection_layout);
        getSupportActionBar().hide();
    }

    public static boolean isInternetConnectionWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected());
    }
}