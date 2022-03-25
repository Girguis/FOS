package com.example.facultyofscience.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facultyofscience.R;
import com.example.facultyofscience.Models.SubjectGrade;

import java.util.ArrayList;

public class GpaAdapter extends RecyclerView.Adapter<GpaAdapter.ViewHolder>{
    private Context context;
    private ArrayList<SubjectGrade> grades;
    public GpaAdapter(Context context, ArrayList<SubjectGrade> grades)
    {
        this.context=context;
        this.grades=grades;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View myListItem=LayoutInflater.from(context).inflate(R.layout.gpa_list_item,viewGroup,false);
        return new ViewHolder(myListItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(grades.get(position));
    }
    @Override
    public int getItemCount() {
     return grades.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder
    {
        private View itemView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            this.itemView=itemView;
        }
        public void bind(SubjectGrade item)
        {
            TextView textView=itemView.findViewById(R.id.hours_val);
            textView.setText(String.valueOf(item.getHours()));
            TextView spinner=itemView.findViewById(R.id.grade_val);
            spinner.setText(item.getGrade());
            TextView subName=itemView.findViewById(R.id.name_val);
            subName.setText(item.getName());
            Button deleteBtn=itemView.findViewById(R.id.deleteRowBtn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indx=getBindingAdapterPosition();
                    if(grades!=null&&grades.size()>0&&indx<grades.size()&&indx>=0)
                        grades.remove(indx);
                    notifyItemRemoved(indx);
                    notifyDataSetChanged();
                }
            });
        }
    }

}
