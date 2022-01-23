package com.ryanmoonscheduleapp.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryanmoonscheduleapp.myapplication.Entities.Assessment;
import com.ryanmoonscheduleapp.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView startDate;
        private final TextView endDate;
        private final TextView type;

        private AssessmentViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.assessmentTitle);
            startDate = itemView.findViewById(R.id.assessmentStart);
            endDate = itemView.findViewById(R.id.assessmentEnd);
            type = itemView.findViewById(R.id.assessmentType);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("type", current.getType());
                    context.startActivity(intent);
                }
            });
        }
    }


    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_assessment, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            Date startDate = current.getStartDate();
            Date endDate = current.getEndDate();
            String type = current.typeToString();
            holder.title.setText(title);
            holder.startDate.setText(dateFormat.format(startDate));
            holder.endDate.setText(dateFormat.format(endDate));
            holder.type.setText(type);
        }
        else{
            holder.title.setText(R.string.no_assessments);
        }

    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setAssessments(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }

}