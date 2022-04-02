package com.example.facultyofscience.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyofscience.Models.News;
import com.example.facultyofscience.NewsDetailsActivity;
import com.example.facultyofscience.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    ArrayList<News> news;
    private Context context;

    public NewsAdapter(Context context, ArrayList<News> news) {
        this.news = news;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleView, dateView;
        private View newsItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsItem = itemView;
            newsItem.setOnClickListener(this);
        }

        public void bind(News news) {
            titleView = newsItem.findViewById(R.id.titleView);
            titleView.setText(news.getTitle());
            dateView = newsItem.findViewById(R.id.dateView);
            dateView.setText(news.getDate());
            imageView = newsItem.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
            Picasso.get().load(news.getImgUrl()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            int indx = getBindingAdapterPosition();
            News forDetails = news.get(indx);
            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("title", forDetails.getTitle());
            intent.putExtra("date", forDetails.getDate());
            intent.putExtra("imgUrl", forDetails.getImgUrl());
            intent.putExtra("detailsUrl", forDetails.getDetailsUrl());
            context.startActivity(intent);
        }
    }
}
