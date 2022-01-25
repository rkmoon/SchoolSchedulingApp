package com.ryanmoonscheduleapp.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.R;
import com.ryanmoonscheduleapp.myapplication.database.Repository;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class TermDetail extends AppCompatActivity {
    private boolean isEdit = false;
    private int termID;
    private Term termToEdit;
    private Repository repository;
    private EditText termTitle;
    private EditText termStartDate;
    private EditText termEndDate;
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener startDatePicker;
    private DatePickerDialog.OnDateSetListener endDatePicker;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        termID = getIntent().getIntExtra("termID", 0);
        repository = new Repository(getApplication());
        termStartDate = findViewById(R.id.termStartDate);
        termEndDate = findViewById(R.id.courseEndDate);
        termTitle = findViewById(R.id.termDetailTitle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (isEdit == true) {
            termToEdit = repository.getTermByID(termID);
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

        termStartDate.setOnClickListener(v -> new DatePickerDialog(TermDetail.this, startDatePicker,
                startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH)).show());

        termEndDate.setOnClickListener(v -> new DatePickerDialog(TermDetail.this, endDatePicker,
                endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH)).show());
        updateLabels();

    }

    private void setInitialInformation() {
        getSupportActionBar().setTitle(termToEdit.getTitle());

        termTitle.setText(termToEdit.getTitle());
        startDate.setTime(termToEdit.getStartDate());
        endDate.setTime(termToEdit.getEndDate());
        termStartDate.setText(sdf.format(startDate.getTime()));
        termEndDate.setText(sdf.format(endDate.getTime()));

    }

    private void updateLabels() {
        termStartDate.setText(sdf.format(startDate.getTime()));
        termEndDate.setText(sdf.format(endDate.getTime()));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveTerm(View view) {
        if (TextUtils.isEmpty(termTitle.getText().toString()) || TextUtils.isEmpty(termStartDate.getText().toString()) || TextUtils.isEmpty(termEndDate.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Fill in All Fields", Toast.LENGTH_SHORT).show();
        }
        else {
            Term term = new Term(termTitle.getText().toString(), Date.valueOf(dateFormat.format(startDate.getTime())),
                    Date.valueOf(dateFormat.format(endDate.getTime())));
            if (isEdit) {
                term.setTermID(termToEdit.getTermID());
                repository.update(term);
            }
            else {
                repository.insert(term);
            }
            this.finish();
        }
    }
}