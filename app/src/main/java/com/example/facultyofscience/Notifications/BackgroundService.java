package com.example.facultyofscience.Notifications;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.facultyofscience.MainActivity;
import com.example.facultyofscience.R;

import org.jsoup.Jsoup;

import java.io.IOException;

public class BackgroundService extends BroadcastReceiver {
    String[] urls = {
            "https://science.asu.edu.eg/ar/news",
            "https://science.asu.edu.eg/ar/events",
            "https://science.asu.edu.eg/ar/announcements"
    };
    String[][] querySelector = {
            {"div.blog-content", "h4"},
            {"div.event-content", "h3"},
            {"div.gallery-desc", "h3"}
    };
    String[] types = {"News", "Events", "Announcements"};
    String[] arabicTitles = {"خبر جديد", "حدث جديد", "إعلان جديد"};

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.isInternetConnectionWorking(context)) {
                    SharedPreferences sp = context.getSharedPreferences("NotificationsData", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edt = sp.edit();
                    for (int i = 0; i < 3; i++) {
                        String lastWebsiteTitle = "";
                        try {
                            lastWebsiteTitle = (Jsoup.connect(urls[i]).get()).selectFirst(querySelector[i][0]).selectFirst(querySelector[i][1]).text();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String lastFileTitle = sp.getString(types[i], "");
                        if (!lastWebsiteTitle.equals(lastFileTitle)) {
                            edt.remove(types[i]);
                            edt.putString(types[i], lastWebsiteTitle);
                            edt.apply();
                            sendNotification(context, i, arabicTitles[i], lastWebsiteTitle);
                        }
                    }
                }
            }
        }).start();
    }

    private void sendNotification(Context context, int channelId, String contentTitle, String contentText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationChannel");
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setSmallIcon(R.drawable.facultylogo);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(channelId, builder.build());
    }

}
