package com.ryanmoonscheduleapp.myapplication.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")/*, foreignKeys = {@ForeignKey(entity = Course.class,
        parentColumns = "courseID", childColumns = "courseID", onDelete = ForeignKey.CASCADE)})*/
public class Assessment {

    public enum Type{
        OBJECTIVE,
        PERFORMANCE
    }
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private int courseID;
    private String title;
    private Date startDate;
    private Date endDate;
    private Type type;

    public Assessment(String title, Date startDate, Date endDate, Type type) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }
    public int getAssessmentID() {
        return assessmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String typeToString(){
        String typeString = null;
        switch (this.type){
            case OBJECTIVE:
                typeString = "Objective";
                break;
            case PERFORMANCE:
                typeString = "Performance";
                break;
        }
        return typeString;
    }
}
