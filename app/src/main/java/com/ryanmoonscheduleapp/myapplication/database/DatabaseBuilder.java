package com.ryanmoonscheduleapp.myapplication.database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.ryanmoonscheduleapp.myapplication.DAO.AssessmentDAO;
import com.ryanmoonscheduleapp.myapplication.DAO.CourseDAO;
import com.ryanmoonscheduleapp.myapplication.DAO.TermDAO;
import com.ryanmoonscheduleapp.myapplication.Entities.Assessment;
import com.ryanmoonscheduleapp.myapplication.Entities.Course;
import com.ryanmoonscheduleapp.myapplication.Entities.Term;
import com.ryanmoonscheduleapp.myapplication.Utilities.Converters;

@Database(entities = {Assessment.class, Course.class, Term.class}, version=1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseBuilder extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();

    public static volatile DatabaseBuilder INSTANCE;

    static DatabaseBuilder getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (DatabaseBuilder.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),DatabaseBuilder.class, "SchedulingDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
