package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ryanmoonscheduleapp.myapplication.DAO.CourseDAO;
import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CourseDetail extends AppCompatActivity {

    private boolean isEdit;
    private int termID;
    private Course courseToEdit;
    private Repository repository;
    private EditText courseName;
    private EditText courseStartDate;
    private EditText courseEndDate;
    private Spinner statusSpinner;
    private EditText instName;
    private EditText instPhone;
    private EditText instEmail;
    private EditText courseNotes;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDatePicker;
    private DatePickerDialog.OnDateSetListener endDatePicker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        termID = getIntent().getIntExtra("termID", 0);

        linkFields();
        if (isEdit) {
            courseToEdit = repository.getCourseByID(getIntent().getIntExtra("courseID", 0));
            setInitialInformation();
        }
        startDatePicker = (view, year, monthOfYear, dayOfMonth) -> {
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, monthOfYear);
            startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        };

        endDatePicker = (view, year, monthOfYear, dayOfMonth) -> {
            endDate.set(Calendar.YEAR, year);
            endDate.set(Calendar.MONTH, monthOfYear);
            endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        };

        courseStartDate.setOnClickListener(v -> new DatePickerDialog(CourseDetail.this, startDatePicker,
                startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH)).show());

        courseEndDate.setOnClickListener(v -> new DatePickerDialog(CourseDetail.this, endDatePicker,
                endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH)).show());
        updateLabels();
    }

    private void setInitialInformation() {
        courseName.setText(courseToEdit.getTitle());
        statusSpinner.setSelection(getIndexOfSpinner(courseToEdit.getStatus()));
        startDate.setTime(courseToEdit.getStartDate());
        endDate.setTime(courseToEdit.getEndDate());
        instName.setText(courseToEdit.getInstructorName());
        instPhone.setText(courseToEdit.getInstructorPhoneNumber());
        instEmail.setText(courseToEdit.getInstructorEmail());
        courseNotes.setText(courseToEdit.getNotes());
        updateLabels();
    }


    private void linkFields() {
        courseName = findViewById(R.id.courseDetailTitle);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseEndDate = findViewById(R.id.courseEndDate);
        statusSpinner = (Spinner) findViewById(R.id.courseStatusSpinner);
        statusSpinner.setAdapter(new ArrayAdapter<Course.Status>(this, android.R.layout.simple_spinner_item, Course.Status.values()));
        instName = findViewById(R.id.courseInstName);
        instEmail = findViewById(R.id.courseInstEmail);
        instPhone = findViewById(R.id.courseInstPhoneNumber);
        courseNotes = findViewById(R.id.courseNotes);


    }

    private void updateLabels() {
        courseStartDate.setText(sdf.format(startDate.getTime()));
        courseEndDate.setText(sdf.format(endDate.getTime()));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getIndexOfSpinner(Course.Status status) {
        int index;
        for (index = 0; index < statusSpinner.getCount(); index++) {
            if (statusSpinner.getItemAtPosition(index) == status) {
                return index;
            }
        }
        return 0;
    }

   /* private Course.Status getValueOfSpinner(){
        if(statusSpinner.getSelectedItem() == Course.Status.PLANTOTAKE){

        }
    }*/

    public void saveCourse(View view) {
        if (TextUtils.isEmpty(courseName.getText().toString()) || TextUtils.isEmpty(courseStartDate.getText().toString())
                || TextUtils.isEmpty(courseEndDate.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill in Course Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            Course course = new Course(courseName.getText().toString(), Date.valueOf(dateFormat.format(startDate.getTime())),
                    Date.valueOf(dateFormat.format(endDate.getTime())), (Course.Status) statusSpinner.getSelectedItem(),
                    instName.getText().toString(), instPhone.getText().toString(), instEmail.getText().toString(), termID,
                    courseNotes.getText().toString());

            if (isEdit) {
                course.setCourseID(courseToEdit.getCourseID());
                repository.update(course);
            }
            else {
                repository.insert(course);
            }
            this.finish();
        }

    }
}