package com.example.facultyofscience.Notifications;

import android.content.Context;
import android.content.Intent;

import com.example.facultyofscience.Activities.News.NewsDetailsActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.lang.reflect.Method;

public class NewsNotification {
    static Element topDiv = null;
    static String lastWebsiteTitle = "";

    public static void execute(Context context) {

        try {
            topDiv = (Jsoup.connect("https://science.asu.edu.eg/ar/news").get()).selectFirst("div.row.blog-inner");
            lastWebsiteTitle = topDiv.selectFirst("h4").text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Method method = NewsNotification.class.getMethod("getIntentForNotification", Context.class);
            NotificationSender.checkForWebsiteUpdate(context, method, lastWebsiteTitle, "News", "خبر جديد", 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intent getIntentForNotification(Context context) {
        Intent myIntent;
        myIntent = new Intent(context, NewsDetailsActivity.class);
        myIntent.putExtra("title", lastWebsiteTitle);
        myIntent.putExtra("date", topDiv.select("div.date").text());
        myIntent.putExtra("imgUrl", topDiv.select("div.blog-images").select("img").attr("src"));
        myIntent.putExtra("detailsUrl", topDiv.select("h4").select("a").attr("href"));
        return myIntent;
    }
}
