package com.example.facultyofscience.Notifications;

import android.content.Context;
import android.content.Intent;

import com.example.facultyofscience.EventsDetailsActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Method;

public class EventsNotification {

    static Element topDiv = null;
    static String lastWebsiteTitle = "";

    public static void execute(Context context) {

        try {
            topDiv = (Jsoup.connect("https://science.asu.edu.eg/ar/events").get()).selectFirst("div.event-item.mx-auto");
            lastWebsiteTitle = topDiv.selectFirst("h3").text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Method method = EventsNotification.class.getMethod("getIntentForNotification", Context.class);
            NotificationSender.checkForWebsiteUpdate(context, method, lastWebsiteTitle, "Events", "حدث جديد", 2);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intent getIntentForNotification(Context context) {
        Intent myIntent;
        myIntent = new Intent(context, EventsDetailsActivity.class);
        myIntent.putExtra("title", lastWebsiteTitle);
        myIntent.putExtra("date", topDiv.select("div.event-date").text());
        myIntent.putExtra("imgUrl", topDiv.select("div.event-img").select("img").attr("src"));
        myIntent.putExtra("detailsUrl", topDiv.select("h3").select("a").attr("href"));
        return myIntent;
    }
}
