package com.example.facultyofscience.Activities;

import android.app.Activity;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ContentDetailsGetter {
    private String detailsUrl, selectQuery, detailsString;
    private TextView detailsTxtView;
    private Activity activity;
    private Context context;
    private ProgressBar progressBar;

    public ContentDetailsGetter(Context context, ProgressBar progressBar, String detailsUrl, String selectQuery, TextView detailsTxtView, Activity activity) {
        this.context = context;
        this.progressBar = progressBar;
        this.detailsUrl = detailsUrl;
        this.selectQuery = selectQuery;
        this.detailsTxtView = detailsTxtView;
        this.activity = activity;
    }

    public void execute() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
                    Document doc = Jsoup.connect(detailsUrl).get();
                    Elements data = doc.select(selectQuery);
                    detailsString = String.valueOf(data);
                    detailsString = detailsString.replaceAll("<img.*?>", "");
                    detailsString = detailsString.replaceAll("<img.*?/>", "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_out));
                        detailsTxtView.setText(HtmlCompat.fromHtml(detailsString, HtmlCompat.FROM_HTML_MODE_LEGACY));
                        detailsTxtView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                });
            }
        }).start();
    }
}
