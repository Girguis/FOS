package com.example.facultyofscience.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.facultyofscience.MainActivity;

public class BackgroundService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.isInternetConnectionWorking(context)) {
                    try {
                        NewsNotification.execute(context);
                        EventsNotification.execute(context);
                        AnnouncementsNotification.execute(context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
