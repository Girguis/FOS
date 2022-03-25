package com.example.facultyofscience;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {
ImageView imageView;
TextView titleTxt,dateTxt,detailsTxt;
LayoutInflater layoutInflater;
LinearLayout imagesGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_layout);
        getSupportActionBar().hide();
        layoutInflater=LayoutInflater.from(this);
        imagesGallery=findViewById(R.id.detailImagesGallery);
        imageView=findViewById(R.id.imageView);
        titleTxt=findViewById(R.id.titleView);
        dateTxt=findViewById(R.id.dateView);
        detailsTxt=findViewById(R.id.descriptionView);
        titleTxt.setText(getIntent().getStringExtra("title"));
        dateTxt.setText(getIntent().getStringExtra("date"));
        Picasso.get().load(getIntent().getStringExtra("imgUrl")).into(imageView);
        //"div.content"
        ContentDetailsGetter cd=new ContentDetailsGetter(
                getIntent().getStringExtra("detailsUrl"),
                "div.content",
                detailsTxt,
                this);
        cd.execute();
     }

}