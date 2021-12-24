package com.learn.personal.androidmodulone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.learn.personal.androidmodulone.db.DatabaseHandler;
import com.learn.personal.androidmodulone.db.StudentHealthDao;
import com.learn.personal.androidmodulone.models.Student;

import java.util.List;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {
    ListAdapter adapter;
    List<Student> students;

    private StudentHealthDao studentHealthDao;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());
        studentHealthDao = new StudentHealthDao(db);

        Objects.requireNonNull(getSupportActionBar()).setTitle("List Keluhan Murid");

        initStudentListFromDb();

        RecyclerView recyclerView = findViewById(R.id.rv_student_list);
        adapter = new ListAdapter(students,
                deletedStudent -> {
                    studentHealthDao.delete(deletedStudent);
                    Toast.makeText(this, deletedStudent.getName(), Toast.LENGTH_SHORT);
                },
                updatedStudent -> {
                    Student student = studentHealthDao.getByNik(updatedStudent.getNik());
                    Intent intent = new Intent(this, EditActivity.class);
                    intent.putExtra(EditActivity.PARCEL, student);
                    startActivity(intent);
                }
        );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initStudentListFromDb() {
        students = studentHealthDao.getAll();
    }
}