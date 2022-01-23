package com.ryanmoonscheduleapp.myapplication.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termTitle;
        private final TextView startDate;
        private final TextView endDate;

        private TermViewHolder(View itemView) {
            super(itemView);
            termTitle = itemView.findViewById(R.id.termTitle);
            startDate = itemView.findViewById(R.id.termStart);
            endDate = itemView.findViewById(R.id.termEnd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, ListOfCourses.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item_term, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if(mTerms!=null){
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Term current=mTerms.get(position);
            String title = current.getTitle();
            Date startDate = current.getStartDate();
            Date endDate = current.getEndDate();
            holder.termTitle.setText(title);
            holder.startDate.setText(dateFormat.format(startDate));
            holder.endDate.setText(dateFormat.format(endDate));

        }
        else {
            holder.termTitle.setText(R.string.no_terms);
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

}
