package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ryanmoonscheduleapp.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openTerms(View view){
        Intent intent = new Intent(this, ListOfTerms.class);
        startActivity(intent);
    }

    public void openCourses(View view){
        Intent intent = new Intent(this, ListOfCourses.class);
        startActivity(intent);
    }
    public void openAssessments(View view){
        Intent intent = new Intent(this, ListOfAssessments.class);
        startActivity(intent);
    }
}