package com.ryanmoonscheduleapp.myapplication.Entities;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    public enum Status{
        INPROGRESS,
        COMPLETED,
        DROPPED,
        PLANTOTAKE
    }

    private String title;
    private Date startDate;
    private Date endDate;
    private Status status;
    private String instructorName;
    private String instructorPhoneNumber;
    private String instructorEmail;

    public Course(String title, Date startDate, Date endDate, Status status, String instructorName, String instructorPhoneNumber, String instructorEmail) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.instructorEmail = instructorEmail;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorPhoneNumber() {
        return instructorPhoneNumber;
    }

    public void setInstructorPhoneNumber(String instructorPhoneNumber) {
        this.instructorPhoneNumber = instructorPhoneNumber;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }




}
