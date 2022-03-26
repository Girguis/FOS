package com.example.facultyofscience;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout gpaCalc,fbPage,newsPage,eventsPage,announPage;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getSupportActionBar().hide();
        gpaCalc=findViewById(R.id.gpaCalc);
        gpaCalc.setOnClickListener(this);
        fbPage=findViewById(R.id.fbPage);
        fbPage.setOnClickListener(this);
        newsPage=findViewById(R.id.newsPage);
        newsPage.setOnClickListener(this);
        eventsPage=findViewById(R.id.eventsPage);
        eventsPage.setOnClickListener(this);
        announPage=findViewById(R.id.announPage);
        announPage.setOnClickListener(this);
    }
    @Override
        public void onClick(View v) {
            if(!isInternetConnectionWorking() &&v.getId()!=R.id.gpaCalc&&v.getId()!=R.id.fbPage)
            {
                intent=new Intent(this,NoInternetConnectionActivity.class);
                startActivity(intent);
            }
            else {
                if (v.getId() == R.id.gpaCalc) {
                    intent = new Intent(this, GpaCalcActivity.class);
                    startActivity(intent);
                } else if (v.getId() == R.id.fbPage) {
                    try {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/672109416181270/"));
                        startActivity(intent);
                    } catch (Exception ex) {
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/FacultyofScienceASU"));
                        startActivity(intent);
                    }
                } else if (v.getId() == R.id.newsPage) {
                    intent = new Intent(this, NewsActivity.class);
                    startActivity(intent);
                } else if (v.getId() == R.id.eventsPage) {
                    intent = new Intent(this, EventsActivity.class);
                    startActivity(intent);
                } else if (v.getId() == R.id.announPage) {
                    intent = new Intent(this, AnnouncementsActivity.class);
                    startActivity(intent);
                }
            }
        }
        private boolean isInternetConnectionWorking()
        {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
             return true;
            else
                return false;
        }
}