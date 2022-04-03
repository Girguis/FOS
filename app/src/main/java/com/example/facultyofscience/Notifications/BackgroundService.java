package com.example.facultyofscience.Notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.facultyofscience.AnnouncementsDetailsActivity;
import com.example.facultyofscience.EventsDetailsActivity;
import com.example.facultyofscience.MainActivity;
import com.example.facultyofscience.NewsDetailsActivity;
import com.example.facultyofscience.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class BackgroundService extends BroadcastReceiver {
    String[] urls = {
            "https://science.asu.edu.eg/ar/news",
            "https://science.asu.edu.eg/ar/events",
            "https://science.asu.edu.eg/ar/announcements"
    };
    String[][] querySelector = {
            {"div.row.blog-inner", "h4"},
            {"div.event-item.mx-auto", "h3"},
            {"div.gallery-item", "h3"}
    };
    String[] types = {"News", "Events", "Announcements"};
    String[] arabicTitles = {"خبر جديد", "حدث جديد", "إعلان جديد"};
    String lastWebsiteTitle = "";
    Element topDiv = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MainActivity.isInternetConnectionWorking(context)) {
                    SharedPreferences sp = context.getSharedPreferences("NotificationsData", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edt = sp.edit();
                    for (int i = 0; i < 3; i++) {
                        try {
                            topDiv = (Jsoup.connect(urls[i]).get()).selectFirst(querySelector[i][0]);
                            lastWebsiteTitle = topDiv.selectFirst(querySelector[i][1]).text();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String lastFileTitle = sp.getString(types[i], "");
                        if (!lastWebsiteTitle.equals(lastFileTitle)) {
                            edt.remove(types[i]);
                            edt.putString(types[i], lastWebsiteTitle);
                            edt.apply();
                            Intent notificationIntent = getIntentForNotification(context, i);
                            PendingIntent pendingIntent = PendingIntent.getActivity(context, i, notificationIntent, 0);
                            sendNotification(context, i, arabicTitles[i], lastWebsiteTitle, pendingIntent);
                            lastWebsiteTitle = "";
                            pendingIntent = null;
                            topDiv = null;
                        }
                    }
                }
            }
        }).start();
    }

    private Intent getIntentForNotification(Context context, int i) {
        Intent myIntent;
        if (i == 0) {
            myIntent = new Intent(context, NewsDetailsActivity.class);
            myIntent.putExtra("title", lastWebsiteTitle);
            myIntent.putExtra("date", topDiv.select("div.date").text());
            myIntent.putExtra("imgUrl", topDiv.select("div.blog-images").select("img").attr("src"));
            myIntent.putExtra("detailsUrl", topDiv.select("h4").select("a").attr("href"));
        } else if (i == 1) {
            myIntent = new Intent(context, EventsDetailsActivity.class);
            myIntent.putExtra("title", lastWebsiteTitle);
            myIntent.putExtra("date", topDiv.select("div.event-date").text());
            myIntent.putExtra("imgUrl", topDiv.select("div.event-img").select("img").attr("src"));
            myIntent.putExtra("detailsUrl", topDiv.select("h3").select("a").attr("href"));
        } else {
            myIntent = new Intent(context, AnnouncementsDetailsActivity.class);
            myIntent.putExtra("title", lastWebsiteTitle);
            myIntent.putExtra("imgUrl", topDiv.select("img").attr("src"));
            myIntent.putExtra("detailsUrl", topDiv.select("div.gallery-desc").select("h3").select("a").attr("href"));
        }
        return myIntent;
    }

    private void sendNotification(Context context, int channelId, String contentTitle, String contentText, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationChannel");
        builder.setContentTitle(contentTitle);
        builder.setContentText(contentText);
        builder.setSmallIcon(R.drawable.facultylogo);
        builder.setAutoCancel(true);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(channelId, builder.build());
    }

}
