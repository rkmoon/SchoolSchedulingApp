package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.sql.Date;
import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository repo = new Repository(getApplication());
        Course course = new Course("History", Date.from(Instant.now()), Date.from(Instant.now()), Course.Status.INPROGRESS, "big booty judy", "666", "ass");
        repo.insert(course);

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