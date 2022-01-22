package com.ryanmoonscheduleapp.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;



    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView startDate;
        private final TextView endDate;
        private final TextView status;

        private CourseViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.courseTextView);
            startDate = itemView.findViewById(R.id.courseStart);
            endDate = itemView.findViewById(R.id.courseEnd);
            status = itemView.findViewById(R.id.courseStatus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, ListOfAssessments.class);
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhoneNumber", current.getInstructorPhoneNumber());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    context.startActivity(intent);
                }
            });
        }

    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_course, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if(mCourses !=null) {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Course current = mCourses.get(position);
            String title = current.getTitle();
            Date startDate = current.getStartDate();
            Date endDate = current.getEndDate();
            String status = current.statusToString();
            holder.title.setText(title);
            holder.startDate.setText(dateFormat.format(startDate));
            holder.endDate.setText(dateFormat.format(endDate));
            holder.status.setText(status);
        }
        else{
            holder.title.setText(R.string.no_courses);
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public CourseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setCourses(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }
}
