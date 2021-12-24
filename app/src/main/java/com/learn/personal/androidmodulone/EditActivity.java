package com.learn.personal.androidmodulone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.learn.personal.androidmodulone.db.DatabaseHandler;
import com.learn.personal.androidmodulone.db.StudentHealthDao;
import com.learn.personal.androidmodulone.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditActivity extends AppCompatActivity {

    private TextView temperatureTextView, nameTextView, nikTextView;
    private CheckBox coughCheckBox, fluCheckBox, diarrheaCheckBox;
    private SeekBar temperatureSeekBar;
    private RadioGroup genderRadioGroup;
    
    public static String PARCEL = "edit_student";

    private StudentHealthDao studentHealthDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());
        studentHealthDao = new StudentHealthDao(db);

        nameTextView = findViewById(R.id.edit_student_name);
        nikTextView = findViewById(R.id.edit_student_nik);
        temperatureSeekBar = findViewById(R.id.edit_student_seek_bar);
        temperatureTextView = findViewById(R.id.edit_student_temperature_value);
        genderRadioGroup = findViewById(R.id.edit_student_gender_group);
        coughCheckBox = findViewById(R.id.edit_student_cough_checkbox);
        fluCheckBox = findViewById(R.id.edit_student_flu_checkbox);
        diarrheaCheckBox = findViewById(R.id.edit_student_diarrhea_checkbox);

        setupSeekBar();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Keluhan");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(view -> submitData());
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

    private void submitData() {
        RadioButton genderRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());

        String name = nameTextView.getText().toString();
        String nik = nikTextView.getText().toString();
        String gender = genderRadioButton.getText().toString();
        String temperature = temperatureTextView.getText().toString();
        ArrayList<String> problems = (ArrayList<String>) getCheckedBoxValue();

        Student student = new Student(name, nik, gender, temperature, problems.toString());
        studentHealthDao.update(student);

        onBackPressed();
    }

    private List<String> getCheckedBoxValue() {
        ArrayList<String> values = new ArrayList<>();

        if (coughCheckBox.isChecked()) {
            values.add(coughCheckBox.getText().toString());
        }
        if (fluCheckBox.isChecked()) {
            values.add(fluCheckBox.getText().toString());
        }
        if (diarrheaCheckBox.isChecked()) {
            values.add(diarrheaCheckBox.getText().toString());
        }

        return values;
    }

    @Override
    protected void onStart() {
        Student student = getIntent().getParcelableExtra(PARCEL);
        nameTextView.setText(student.getName());
        nikTextView.setText(student.getNik());
        
        if (student.getGender().equals("Male")) {
            genderRadioGroup.check(R.id.edit_student_radio_button_male);
        } else {
            genderRadioGroup.check(R.id.edit_student_radio_button_female);
        }

        temperatureTextView.setText(student.getTemperature());
        for (String problem: student.getProblemsList()) {
            switch (problem) {
                case "Batuk":
                    coughCheckBox.setChecked(true);
                    break;
                case "Pilek":
                    fluCheckBox.setChecked(true);
                    break;
                case "Diare":
                    diarrheaCheckBox.setChecked(true);
                    break;
            }
        }
        super.onStart();
    }

    private void setupSeekBar() {
        int MIN = 350;
        int MAX = 400;
        int STEP = 1;

        temperatureSeekBar.setMax((MAX - MIN) / STEP);

        temperatureSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float computeProgress = (MIN + (progress * STEP)) * 0.1f;
                temperatureTextView.setText(String.format("%.1f °C", computeProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}