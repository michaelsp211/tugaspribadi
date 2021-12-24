package com.learn.personal.androidmodulone.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.learn.personal.androidmodulone.models.Student;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentHealthDao {
    private final DatabaseHandler dbHandler;

    public StudentHealthDao(DatabaseHandler db) {
        dbHandler = db;
    }

    public List<Student> getAll() {
        List<Student> studentHealthList = new ArrayList<Student>();

        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] columns = {
                dbHandler.HEALTH_KEY_ID,
                dbHandler.HEALTH_KEY_NAME,
                dbHandler.HEALTH_KEY_NIK,
                dbHandler.HEALTH_KEY_GENDER,
                dbHandler.HEALTH_KEY_TEMPERATURE,
                dbHandler.HEALTH_KEY_PROBLEMS
        };

        @SuppressLint("Recycle")
        Cursor cursor = db.query(dbHandler.TABLE_HEALTH, columns, null, null,
                null, null, null);

        while (cursor.moveToNext()) {
            Student student = new Student(
                    cursor.getString(cursor.getColumnIndex(dbHandler.HEALTH_KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(dbHandler.HEALTH_KEY_NIK)),
                    cursor.getString(cursor.getColumnIndex(dbHandler.HEALTH_KEY_GENDER)),
                    cursor.getString(cursor.getColumnIndex(dbHandler.HEALTH_KEY_TEMPERATURE)),
                    cursor.getString(cursor.getColumnIndex(dbHandler.HEALTH_KEY_PROBLEMS))
            );

            studentHealthList.add(student);
        }

        return studentHealthList;
    }

    public Student getByNik(String studentNik) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return this.getAll().stream().filter(student -> studentNik.equals(student.getNik())).findAny().orElse(null);
        }
        return null;
    }

    public void insert(@NotNull Student student) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHandler.HEALTH_KEY_NAME, student.getName());
        values.put(dbHandler.HEALTH_KEY_NIK, student.getNik());
        values.put(dbHandler.HEALTH_KEY_GENDER, student.getGender());
        values.put(dbHandler.HEALTH_KEY_TEMPERATURE, student.getTemperature());
        values.put(dbHandler.HEALTH_KEY_PROBLEMS, student.getProblems());

        db.insert(dbHandler.TABLE_HEALTH, null, values);
    }

    public void update(@NotNull Student student) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(dbHandler.HEALTH_KEY_NAME, student.getName());
        values.put(dbHandler.HEALTH_KEY_NIK, student.getNik());
        values.put(dbHandler.HEALTH_KEY_GENDER, student.getGender());
        values.put(dbHandler.HEALTH_KEY_TEMPERATURE, student.getTemperature());
        values.put(dbHandler.HEALTH_KEY_PROBLEMS, student.getProblems());

        db.update(dbHandler.TABLE_HEALTH, values, "nik=?", new String[]{student.getNik()});
    }

    public void delete(@NotNull Student student) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete(dbHandler.TABLE_HEALTH, "nik=?", new String[]{student.getNik()});
    }
}
