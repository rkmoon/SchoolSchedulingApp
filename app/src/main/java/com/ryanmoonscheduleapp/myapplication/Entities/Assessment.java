package com.ryanmoonscheduleapp.myapplication.Entities;

import androidx.room.Entity;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    private String title;
    private Date startDate;
    private Date endDate;

    public Assessment(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
