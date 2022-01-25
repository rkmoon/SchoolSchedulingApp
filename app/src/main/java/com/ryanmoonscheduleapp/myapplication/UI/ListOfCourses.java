package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListOfCourses extends AppCompatActivity {
    private Repository repository;
    private int termID;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_courses);
        termID = getIntent().getIntExtra("termID", 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        recyclerView = findViewById(R.id.recyclerviewcourses);
        repository = new Repository(getApplication());
        populateCourses();
    }

    private void populateCourses(){
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for(Course c: repository.getAllCourses()){
            if(c.getTermID()== termID)
                filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateCourses();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.editTerm:
                Intent intent = new Intent(this, TermDetail.class);
                intent.putExtra("termID", termID);
                intent.putExtra("isEdit", true);
                this.startActivity(intent);
            case R.id.deleteTerm:
                Term termToDelete = repository.getTermByID(termID);
                repository.delete(termToDelete);
                this.finish();
            case R.id.deleteCoursesInTerm:
                List<Course> coursesToDelete = repository.getCoursesInTerm(termID);
                for(Course c: coursesToDelete){
                    repository.delete(c);
                }
                populateCourses();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_list_of_courses, menu);
        return true;
    }
}