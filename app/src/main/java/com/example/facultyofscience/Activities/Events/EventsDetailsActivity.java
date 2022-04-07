package com.example.facultyofscience.Activities.Events;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.facultyofscience.Activities.ContentDetailsGetter;
import com.example.facultyofscience.R;
import com.squareup.picasso.Picasso;

public class EventsDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTxt, dateTxt, detailsTxt;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_details_layout);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        titleTxt = findViewById(R.id.titleView);
        dateTxt = findViewById(R.id.dateView);
        progressBar = findViewById(R.id.progress_bar);
        detailsTxt = findViewById(R.id.eventDetailsView);
        titleTxt.setText(getIntent().getStringExtra("title"));
        dateTxt.setText(getIntent().getStringExtra("date"));
        Picasso.get().load(getIntent().getStringExtra("imgUrl")).into(imageView);
        ContentDetailsGetter cd = new ContentDetailsGetter(
                this,
                progressBar,
                getIntent().getStringExtra("detailsUrl"),
                "div.event-desc",
                detailsTxt,
                this);
        cd.execute();
    }
}