package com.example.facultyofscience.Activities.Announcements;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.Activities.ContentDetailsGetter;
import com.example.facultyofscience.R;
import com.squareup.picasso.Picasso;

public class AnnouncementsDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTxt, detailsTxt;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annnouncements_details_layout);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        titleTxt = findViewById(R.id.titleView);
        detailsTxt = findViewById(R.id.announDetailsView);
        progressBar = findViewById(R.id.progress_bar);
        titleTxt.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("imgUrl")).into(imageView);
        ContentDetailsGetter cd = new ContentDetailsGetter(
                this,
                progressBar,
                getIntent().getStringExtra("detailsUrl"),
                "div.about-desc",
                detailsTxt);
        cd.execute();
    }
}