package com.example.facultyofscience;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.Notifications.BackgroundService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout gpaCalc, fbPage, newsPage, eventsPage, announPage;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getSupportActionBar().hide();
        gpaCalc = findViewById(R.id.gpaCalc);
        gpaCalc.setOnClickListener(this);
        fbPage = findViewById(R.id.fbPage);
        fbPage.setOnClickListener(this);
        newsPage = findViewById(R.id.newsPage);
        newsPage.setOnClickListener(this);
        eventsPage = findViewById(R.id.eventsPage);
        eventsPage.setOnClickListener(this);
        announPage = findViewById(R.id.announPage);
        announPage.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("NotificationChannel", "NotificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manger = getSystemService(NotificationManager.class);
            manger.createNotificationChannel(channel);
        }
        startBackgroundProcess();
    }

    private void startBackgroundProcess() {
        Intent intent = new Intent(this, BackgroundService.class);
        intent.setAction("BackgroundService");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, AlarmManager.INTERVAL_HOUR * 6, pendingIntent);
    }

    @Override
    public void onClick(View v) {
        if (!isInternetConnectionWorking(this) && v.getId() != R.id.gpaCalc && v.getId() != R.id.fbPage) {
            intent = new Intent(this, NoInternetConnectionActivity.class);
            startActivity(intent);
        } else {
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

    public static boolean isInternetConnectionWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }
}