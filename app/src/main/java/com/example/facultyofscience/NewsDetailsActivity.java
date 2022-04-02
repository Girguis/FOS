package com.example.facultyofscience;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTxt, dateTxt, detailsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_layout);
        getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        titleTxt = findViewById(R.id.titleView);
        dateTxt = findViewById(R.id.dateView);
        detailsTxt = findViewById(R.id.descriptionView);
        titleTxt.setText(getIntent().getStringExtra("title"));
        dateTxt.setText(getIntent().getStringExtra("date"));
        Picasso.get().load(getIntent().getStringExtra("imgUrl")).into(imageView);
        ContentDetailsGetter cd = new ContentDetailsGetter(
                getIntent().getStringExtra("detailsUrl"),
                "div.content",
                detailsTxt,
                this);
        cd.execute();
    }

}