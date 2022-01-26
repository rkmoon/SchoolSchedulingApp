package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ryanmoonscheduleapp.myapplication.Entities.Assessment;
import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AssessmentDetail extends AppCompatActivity {

    private boolean isEdit;
    private int courseID;
    private Assessment assessmentToEdit;
    private Repository repository;
    private EditText assessmentTitle;
    private EditText assessmentStartDate;
    private EditText assessmentEndDate;
    private Spinner typeSpinner;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDatePicker;
    private DatePickerDialog.OnDateSetListener endDatePicker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repository = new Repository(getApplication());
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        courseID = getIntent().getIntExtra("courseID", 0);
        linkFields();
        if (isEdit) {
            assessmentToEdit = repository.getAssessmentByID(getIntent().getIntExtra("assessmentID", 0));
            setInitialInformation();
        }
        startDatePicker = (view, year, monthOfYear, dayOfMonth) -> {
            startDate.set(Calendar.YEAR, year);
            startDate.set(Calendar.MONTH, monthOfYear);
            startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabels();
        };

        endDatePicker = (view, year, monthOfYear, dayOfMonth) -> {
            endDate.set(Calendar.YEAR, year);
            endDate.set(Calendar.MONTH, monthOfYear);
            endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabels();
        };

        assessmentStartDate.setOnClickListener(v -> new DatePickerDialog(AssessmentDetail.this, startDatePicker,
                startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH)).show());

        assessmentEndDate.setOnClickListener(v -> new DatePickerDialog(AssessmentDetail.this, endDatePicker,
                endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH)).show());
        updateLabels();
    }

    private void setInitialInformation(){
        assessmentTitle.setText(assessmentToEdit.getTitle());
        startDate.setTime(assessmentToEdit.getStartDate());
        endDate.setTime(assessmentToEdit.getEndDate());
        typeSpinner.setSelection(getIndexOfSpinner(assessmentToEdit.getType()));
        updateLabels();
    }
    private void updateLabels(){
        assessmentStartDate.setText(sdf.format(startDate.getTime()));
        assessmentEndDate.setText(sdf.format(endDate.getTime()));
    }

    private void linkFields(){
        assessmentTitle = findViewById(R.id.assessmentDetailTitle);
        assessmentStartDate = findViewById(R.id.assessmentStartDate);
        assessmentEndDate = findViewById(R.id.assessmentEndDate);
        typeSpinner = (Spinner) findViewById(R.id.assessmentTypeSpinner);
        typeSpinner.setAdapter(new ArrayAdapter<Assessment.Type>(this, android.R.layout.simple_spinner_item, Assessment.Type.values()));
    }

    public void saveAssessment(View view){
        if (TextUtils.isEmpty(assessmentTitle.getText().toString()) || TextUtils.isEmpty(assessmentStartDate.getText().toString())
                || TextUtils.isEmpty(assessmentEndDate.getText().toString())){
            Toast.makeText(getApplicationContext(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show();
        }
        else{
            Assessment assessment = new Assessment(assessmentTitle.getText().toString(), Date.valueOf(dateFormat.format(startDate.getTime())),
                    Date.valueOf(dateFormat.format(endDate.getTime())), (Assessment.Type) typeSpinner.getSelectedItem(),
                    courseID);
            if(isEdit){
                assessment.setAssessmentID(assessmentToEdit.getAssessmentID());
                repository.update(assessment);
            }
            else{
                repository.insert(assessment);
            }
            setNotifications();
            this.finish();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int getIndexOfSpinner(Assessment.Type type) {
        int index;
        for (index = 0; index < typeSpinner.getCount(); index++) {
            if (typeSpinner.getItemAtPosition(index) == type) {
                return index;
            }
        }
        return 0;
    }

    private void setNotifications(){
        Date dStartDate = Date.valueOf(dateFormat.format(startDate.getTime()));
        Date dEndDate = Date.valueOf(dateFormat.format(endDate.getTime()));
        long startTrigger = dStartDate.getTime();
        long endTrigger = dEndDate.getTime();
        Intent startIntent = new Intent(AssessmentDetail.this, Receiver.class);
        Intent endIntent = new Intent(AssessmentDetail.this, Receiver.class);
        startIntent.putExtra("key", "Assessment " + assessmentTitle.getText().toString() + " starts today.");
        endIntent.putExtra("key", "Assessment " + assessmentTitle.getText().toString() + " ends today.");
        PendingIntent startSender = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, startIntent, 0);
        PendingIntent endSender = PendingIntent.getBroadcast(AssessmentDetail.this, ++MainActivity.numAlert, endIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
        alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);

    }
}
