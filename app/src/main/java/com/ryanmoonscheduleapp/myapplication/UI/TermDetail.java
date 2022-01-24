package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.util.Calendar;

public class TermDetail extends AppCompatActivity {
    private int termID;
    private Term termToEdit;
    private Repository repository;
    private TextView termTitle;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        termID = getIntent().getIntExtra("termID", 0);
        repository = new Repository(getApplication());
        termToEdit = repository.getTermByID(termID);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setInitialInformation();

    }

    private void setInitialInformation(){
        getSupportActionBar().setTitle(termToEdit.getTitle());
        termTitle = findViewById(R.id.termDetailTitle);
        termTitle.setText(termToEdit.getTitle());
        startDate.set(termToEdit.getStartDate().getYear(), );
    }
}