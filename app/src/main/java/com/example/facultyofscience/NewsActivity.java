package com.example.facultyofscience;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyofscience.Adapters.NewsAdapter;
import com.example.facultyofscience.Models.News;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    private ArrayList<News> news = new ArrayList<News>();
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);
        getSupportActionBar().hide();
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.newsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        newsAdapter = new NewsAdapter(this, news);
        recyclerView.setAdapter(newsAdapter);
        Content content = new Content();
        content.execute();
    }

    class Content {
        public void execute() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_in));
                    String url = "https://science.asu.edu.eg/ar/news";
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements data = doc.select("div.row.blog-inner");
                        int count = data.size();
                        for (int i = 0; i < count; i++) {
                            String imgUrl = data.select("div.blog-images").select("img").eq(i).attr("src");
                            String title = data.select("h4").select("a").eq(i).text();
                            String detailsUrl = data.select("h4").select("a").eq(i).attr("href");
                            String date = data.select("div.date").eq(i).text();
                            news.add(new News(title, imgUrl, date, detailsUrl));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            progressBar.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_out));
                            newsAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }

}
