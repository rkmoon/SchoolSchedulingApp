package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.sql.Date;
import java.time.Instant;

public class MainActivity extends AppCompatActivity {

    //Remove these before building
    Repository repo;
    Term testTerm;
    Course testCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repo = new Repository(getApplication());
        testTerm = new Term("Term 1", Date.from(Instant.now()), java.util.Date.from(Instant.now()));;
        testCourse = new Course("History", Date.from(Instant.now()), Date.from(Instant.now()),
                Course.Status.INPROGRESS, "big booty judy", "666",
                "ass", 1, "none");

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

    //remove these before submitting
    public void addTerms(View view){
        Term term2 = new Term("Term 2", Date.from(Instant.now()), java.util.Date.from(Instant.now()));
        repo.insert(testTerm);
        repo.insert(term2);
    }

    public void addCourses(View view){
        repo.insert(testCourse);
    }

    public void addAssessments(View view){

    }
}