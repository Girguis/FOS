package com.example.facultyofscience.Notifications;

import android.content.Context;
import android.content.Intent;

import com.example.facultyofscience.Activities.Announcements.AnnouncementsDetailsActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Method;

public class AnnouncementsNotification {

    static Element topDiv = null;
    static String lastWebsiteTitle = "";

    public static void execute(Context context) {
        try {
            topDiv = (Jsoup.connect("https://science.asu.edu.eg/ar/announcements").get()).selectFirst("div.gallery-item");
            lastWebsiteTitle = topDiv.selectFirst("h3").text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Method method = AnnouncementsNotification.class.getMethod("getIntentForNotification", Context.class);
            NotificationSender.checkForWebsiteUpdate(context, method, lastWebsiteTitle, "Announcements", "اعلان جديد", 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intent getIntentForNotification(Context context) {
        Intent myIntent;
        myIntent = new Intent(context, AnnouncementsDetailsActivity.class);
        myIntent.putExtra("title", lastWebsiteTitle);
        myIntent.putExtra("imgUrl", topDiv.select("img").attr("src"));
        myIntent.putExtra("detailsUrl", topDiv.select("div.gallery-desc").select("h3").select("a").attr("href"));
        return myIntent;
    }
}
