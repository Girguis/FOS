package com.example.facultyofscience.Notifications;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.facultyofscience.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NotificationSender {

    public static void sendNotification(Context context, int channelId, String contentTitle, String contentText, PendingIntent pendingIntent) {
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

    public static void checkForWebsiteUpdate(Context context, Method method,
                                             String lastWebsiteTitle, String keyName,
                                             String contentTitle, int channelId) {
        Object[] parameters = new Object[1];
        parameters[0] = context;
        SharedPreferences sp = context.getSharedPreferences("NotificationsData", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edt = sp.edit();
        String lastFileTitle = sp.getString(keyName, "");
        if (!lastWebsiteTitle.equals(lastFileTitle)) {
            edt.remove(keyName);
            edt.putString(keyName, lastWebsiteTitle);
            edt.apply();

            try {
                Intent notificationIntent = (Intent) method.invoke(null, parameters);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, channelId, notificationIntent, 0);
                NotificationSender.sendNotification(context, channelId, contentTitle, lastWebsiteTitle, pendingIntent);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
