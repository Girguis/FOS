package com.example.facultyofscience.Activities.News;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.Activities.ContentDetailsGetter;
import com.example.facultyofscience.R;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTxt, dateTxt, detailsTxt;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_layout);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        titleTxt = findViewById(R.id.titleView);
        dateTxt = findViewById(R.id.dateView);
        detailsTxt = findViewById(R.id.descriptionView);
        progressBar = findViewById(R.id.progress_bar);
        titleTxt.setText(getIntent().getStringExtra("title"));
        dateTxt.setText(getIntent().getStringExtra("date"));
        Picasso.get().load(getIntent().getStringExtra("imgUrl")).into(imageView);
        ContentDetailsGetter cd = new ContentDetailsGetter(
                this,
                progressBar,
                getIntent().getStringExtra("detailsUrl"),
                "div.content",
                detailsTxt);
        cd.execute();
    }

}