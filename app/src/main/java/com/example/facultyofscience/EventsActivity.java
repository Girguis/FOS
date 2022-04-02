package com.example.facultyofscience;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyofscience.Adapters.EventsAdapter;
import com.example.facultyofscience.Models.Events;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {
    private ArrayList<Events> events = new ArrayList<Events>();
    private RecyclerView recyclerView;
    private EventsAdapter eventsAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_layout);
        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.eventsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        eventsAdapter = new EventsAdapter(this, events);
        recyclerView.setAdapter(eventsAdapter);
        EventsActivity.Content content = new EventsActivity.Content();
        content.execute();
    }

    class Content {
        public void execute() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.startAnimation(AnimationUtils.loadAnimation(EventsActivity.this, android.R.anim.fade_in));
                    String url = "https://science.asu.edu.eg/ar/events";
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements data = doc.select("div.event-item.mx-auto");
                        int count = data.size();
                        for (int i = 0; i < count; i++) {
                            String imgUrl = data.select("div.event-img").select("img").eq(i).attr("src");
                            String title = data.select("h3").select("p").eq(i).text();
                            String detailsUrl = data.select("h3").select("a").eq(i).attr("href");
                            Log.d("deatilsURl", "run: " + detailsUrl);
                            String date = data.select("div.event-date").eq(i).text();
                            events.add(new Events(title, imgUrl, date, detailsUrl));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            progressBar.startAnimation(AnimationUtils.loadAnimation(EventsActivity.this, android.R.anim.fade_out));
                            eventsAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }
}