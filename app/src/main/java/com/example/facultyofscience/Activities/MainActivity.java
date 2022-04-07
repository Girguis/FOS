package com.example.facultyofscience.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.Activities.Announcements.AnnouncementsActivity;
import com.example.facultyofscience.Activities.Events.EventsActivity;
import com.example.facultyofscience.Activities.Gpa.GpaCalcActivity;
import com.example.facultyofscience.Activities.News.NewsActivity;
import com.example.facultyofscience.Activities.NoInternet.NoInternetConnectionActivity;
import com.example.facultyofscience.Activities.SuggestionsAndComplaints.SuggestionsAndComplaints;
import com.example.facultyofscience.Notifications.BackgroundService;
import com.example.facultyofscience.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout gpaCalc, fbPage, newsPage, eventsPage, announPage, formPage;

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
        formPage = findViewById(R.id.formPage);
        formPage.setOnClickListener(this);
        notificationChannelCreate();
        startBackgroundProcess();
    }

    private void notificationChannelCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("NotificationChannel", "NotificationChannel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manger = getSystemService(NotificationManager.class);
            manger.createNotificationChannel(channel);
        }
    }

    private void startBackgroundProcess() {
        Intent intent = new Intent(MainActivity.this, BackgroundService.class);
        intent.setAction("BackgroundService");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, AlarmManager.INTERVAL_HOUR * 6, pendingIntent);
    }

    @Override
    public void onClick(View v) {
        if (!NoInternetConnectionActivity.isInternetConnectionWorking(this) && v != gpaCalc && v != fbPage)
            startActivity(new Intent(this, NoInternetConnectionActivity.class));

        else {
            if (v == fbPage) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/672109416181270/")));
                } catch (Exception ex) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/FacultyofScienceASU")));
                }
            } else if (v == gpaCalc) startActivity(new Intent(this, GpaCalcActivity.class));
            else if (v == newsPage) startActivity(new Intent(this, NewsActivity.class));
            else if (v == eventsPage) startActivity(new Intent(this, EventsActivity.class));
            else if (v == announPage) startActivity(new Intent(this, AnnouncementsActivity.class));
            else if (v == formPage) startActivity(new Intent(this, SuggestionsAndComplaints.class));
        }
    }
}