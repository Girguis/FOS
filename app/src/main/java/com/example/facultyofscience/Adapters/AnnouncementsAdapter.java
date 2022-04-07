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

import com.example.facultyofscience.Activities.Announcements.AnnouncementsDetailsActivity;
import com.example.facultyofscience.Activities.NoInternet.NoInternetConnectionActivity;
import com.example.facultyofscience.Models.Announcements;
import com.example.facultyofscience.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolder> {
    ArrayList<Announcements> announcements;
    private Context context;

    public AnnouncementsAdapter(Context context, ArrayList<Announcements> announcements) {
        this.announcements = announcements;
        this.context = context;
    }

    @NonNull
    @Override
    public AnnouncementsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcements_item, viewGroup, false);
        return new AnnouncementsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementsAdapter.ViewHolder holder, int position) {
        holder.bind(announcements.get(position));
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleView;
        private View announItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            announItem = itemView;
            announItem.setOnClickListener(this);
        }

        public void bind(Announcements events) {
            titleView = announItem.findViewById(R.id.titleView);
            titleView.setText(events.getTitle());
            imageView = announItem.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
            Picasso.get().load(events.getImgUrl()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            if (NoInternetConnectionActivity.isInternetConnectionWorking(context)) {
                int indx = getBindingAdapterPosition();
                Announcements forDetails = announcements.get(indx);
                Intent intent = new Intent(context, AnnouncementsDetailsActivity.class);
                intent.putExtra("title", forDetails.getTitle());
                intent.putExtra("imgUrl", forDetails.getImgUrl());
                intent.putExtra("detailsUrl", forDetails.getDetailsUrl());
                context.startActivity(intent);
            } else
                context.startActivity(new Intent(context, NoInternetConnectionActivity.class));
        }
    }
}
