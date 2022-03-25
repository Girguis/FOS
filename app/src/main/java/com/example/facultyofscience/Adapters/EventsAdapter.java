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
import com.example.facultyofscience.EventsDetailsActivity;
import com.example.facultyofscience.Models.Events;
import com.example.facultyofscience.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    ArrayList<Events> events;
    private Context context;

    public EventsAdapter(Context context,ArrayList<Events> news) {
        this.events = news;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleView,dateView;
        private View eventsItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventsItem=itemView;
            eventsItem.setOnClickListener(this);
        }
        public void bind(Events events)
        {
            titleView=eventsItem.findViewById(R.id.titleView);
            titleView.setText(events.getTitle());
            dateView=eventsItem.findViewById(R.id.dateView);
            dateView.setText(events.getDate());
            imageView =eventsItem.findViewById(R.id.imageView);
            imageView.setOnClickListener(this);
            Picasso.get().load(events.getImgUrl()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            int indx=getBindingAdapterPosition();
            Events forDetails =events.get(indx);
            Intent intent=new Intent(context, EventsDetailsActivity.class);
            intent.putExtra("title",forDetails.getTitle());
            intent.putExtra("date",forDetails.getDate());
            intent.putExtra("imgUrl",forDetails.getImgUrl());
            intent.putExtra("detailsUrl",forDetails.getDetailsUrl());
            context.startActivity(intent);
        }
    }
}

