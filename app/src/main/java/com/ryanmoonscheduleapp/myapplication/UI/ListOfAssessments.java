package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ryanmoonscheduleapp.myapplication.Entities.Assessment;
import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.util.ArrayList;
import java.util.List;

public class ListOfAssessments extends AppCompatActivity {

    private Repository repository;
    private int courseID;
    private int termID;
    private Course currentCourse;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_assessments);
        courseID = getIntent().getIntExtra("courseID", 0);
        termID = getIntent().getIntExtra("termID", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        recyclerView = findViewById(R.id.recycleViewAssessments);
        repository = new Repository(getApplication());
        currentCourse = repository.getCourseByID(courseID);
        populateAssessments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateAssessments();
    }

    private void populateAssessments() {
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment a: repository.getAllAssessments()){
            if(a.getCourseID() == courseID)
                filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.editCourse:
                Intent intent = new Intent(this, CourseDetail.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("courseID", courseID);
                intent.putExtra("termID", termID);
                startActivity(intent);
                return true;
            case R.id.deleteCourse:
                Course courseToDelete = repository.getCourseByID(courseID);
                int numberOfAssessments = repository.getAssessmentsInCourse(courseID).size();
                if(numberOfAssessments > 0){
                    Toast.makeText(getApplicationContext(), "Cannot Delete Course that has Assessments", Toast.LENGTH_SHORT).show();
                }
                else{
                    repository.delete(courseToDelete);
                    this.finish();
                }
                return true;
            case R.id.deleteAllAssessments:
                List<Assessment> assessmentsToDelete = repository.getAssessmentsInCourse(courseID);
                for(Assessment a: assessmentsToDelete){
                    repository.delete(a);
                }
                populateAssessments();
                return true;
            case R.id.shareCourseNotes:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, currentCourse.getNotes());
                shareIntent.putExtra(Intent.EXTRA_TITLE, "Course Notes");
                shareIntent.setType("text/plain");
                Intent sendIntent = Intent.createChooser(shareIntent, null);
                startActivity(sendIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list_of_assessments, menu);
        return true;
    }

    public void addAssessment(View view){
        Intent intent = new Intent(this, AssessmentDetail.class);
        intent.putExtra("isEdit", false);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }
}