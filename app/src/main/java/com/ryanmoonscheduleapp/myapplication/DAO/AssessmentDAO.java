package com.ryanmoonscheduleapp.myapplication.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ryanmoonscheduleapp.myapplication.Entities.Assessment;

import java.util.List;


@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM ASSESSMENTS ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENTS WHERE courseID = :courseID")
    List<Assessment> getAssessmentsInCourse(int courseID);

    @Query("SELECT * FROM ASSESSMENTS WHERE assessmentID = :assessmentID")
    Assessment getAssessmentByID(int assessmentID);
}
