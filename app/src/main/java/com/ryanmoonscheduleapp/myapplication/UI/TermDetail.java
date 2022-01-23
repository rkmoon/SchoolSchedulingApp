package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TermDetail extends AppCompatActivity {
    private int termID;
    private Term termToEdit;
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        termID = getIntent().getIntExtra("termID", 0);
        repository = new Repository(getApplication());
        termToEdit = repository.get
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));


    }
}