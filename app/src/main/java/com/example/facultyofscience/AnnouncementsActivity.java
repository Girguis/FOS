package com.example.facultyofscience;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyofscience.Adapters.AnnouncementsAdapter;
import com.example.facultyofscience.Models.Announcements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class AnnouncementsActivity extends AppCompatActivity {
    private ArrayList<Announcements> announs = new ArrayList<Announcements>();
    private RecyclerView recyclerView;
    private AnnouncementsAdapter announAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcements_layout);
        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.announRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        announAdapter = new AnnouncementsAdapter(this, announs);
        recyclerView.setAdapter(announAdapter);
        AnnouncementsActivity.Content content = new AnnouncementsActivity.Content();
        content.execute();
    }

    class Content {
        public void execute() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.startAnimation(AnimationUtils.loadAnimation(AnnouncementsActivity.this, android.R.anim.fade_in));
                    String url = "https://science.asu.edu.eg/ar/announcements";
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements data = doc.select("div.gallery-item");
                        int count = data.size();
                        for (int i = 0; i < count; i++) {
                            String imgUrl = data.select("img").eq(i).attr("src");
                            String title = data.select("div.gallery-desc").select("h3").select("a").eq(i).text();
                            String detailsUrl = data.select("div.gallery-desc").select("h3").select("a").eq(i).attr("href");
                            announs.add(new Announcements(title, imgUrl, detailsUrl));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            progressBar.startAnimation(AnimationUtils.loadAnimation(AnnouncementsActivity.this, android.R.anim.fade_out));
                            announAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }
}