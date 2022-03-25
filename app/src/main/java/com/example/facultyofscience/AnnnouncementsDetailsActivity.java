package com.example.facultyofscience;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class AnnnouncementsDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView titleTxt,detailsTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.annnouncements_details_layout);
        getSupportActionBar().hide();
        imageView=findViewById(R.id.imageView);
        titleTxt=findViewById(R.id.titleView);
        detailsTxt=findViewById(R.id.announDetailsView);
        titleTxt.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("imgUrl")).into(imageView);
        ContentDetailsGetter cd=new ContentDetailsGetter(
                getIntent().getStringExtra("detailsUrl"),
                "div.about-desc",
                detailsTxt,
                this);
        cd.execute();
    }
}