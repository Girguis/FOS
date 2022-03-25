package com.example.facultyofscience;

import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

class ContentDetailsGetter {
    private String detailsUrl,selectQuery,detailsString;
    private TextView detailsTxtView;
    private Activity activity;

    public ContentDetailsGetter(String detailsUrl, String selectQuery, TextView detailsTxtView, Activity activity) {
        this.detailsUrl = detailsUrl;
        this.selectQuery = selectQuery;
        this.detailsTxtView = detailsTxtView;
        this.activity = activity;
    }

    public void execute()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc= Jsoup.connect(detailsUrl).get();
                    Elements data=doc.select(selectQuery);
                    detailsString=String.valueOf(data);
                    detailsString=detailsString.replaceAll("<img.*>","");
                    detailsString=detailsString.replaceAll("<img.*/>","");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        detailsTxtView.setText(HtmlCompat.fromHtml(detailsString, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        detailsTxtView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                });
            }
        }).start();
    }
}
