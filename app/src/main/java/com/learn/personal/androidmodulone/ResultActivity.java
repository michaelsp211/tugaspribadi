package com.learn.personal.androidmodulone;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.learn.personal.androidmodulone.models.Student;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    public static String PARCEL = "student";
    public TextView nameTextView, nikTextView, genderTextView, temperatureTextView, problemsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Rincian");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nameTextView = findViewById(R.id.student_name);
        nikTextView = findViewById(R.id.student_nik);
        genderTextView = findViewById(R.id.student_gender);
        temperatureTextView = findViewById(R.id.student_temperature);
        problemsTextView = findViewById(R.id.student_problems);
    }

    @Override
    protected void onStart() {
        Student student = getIntent().getParcelableExtra(PARCEL);
        nameTextView.setText(String.format("Nama: %s", student.getName()));
        nikTextView.setText(String.format("NIK: %s", student.getNik()));
        genderTextView.setText(String.format("Jenis Kelamin: %s", student.getGender()));
        temperatureTextView.setText(String.format("Temperatur: %s", student.getTemperature()));
        problemsTextView.setText(String.format("Keluhan: %s", student.getProblems()));
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(getApplicationContext(), "Kembali ke Form Keluhan", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}