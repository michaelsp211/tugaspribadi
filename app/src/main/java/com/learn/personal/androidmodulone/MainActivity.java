package com.learn.personal.androidmodulone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.learn.personal.androidmodulone.db.DatabaseHandler;
import com.learn.personal.androidmodulone.db.StudentHealthDao;
import com.learn.personal.androidmodulone.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, nikEditText;
    private TextView temperatureTextView;
    private CheckBox coughCheckBox, fluCheckBox, diarrheaCheckBox;
    private SeekBar temperatureSeekBar;
    private RadioGroup genderRadioGroup;

    private StudentHealthDao studentHealthDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());
        studentHealthDao = new StudentHealthDao(db);

        logStudentHealthInfo();

        Objects.requireNonNull(getSupportActionBar()).setTitle("Form Keluhan");

        nameEditText = findViewById(R.id.editTextName);
        nikEditText = findViewById(R.id.editTextNIK);
        temperatureSeekBar = findViewById(R.id.seekBarTemperature);
        temperatureTextView = findViewById(R.id.textViewTemperatureValue);
        genderRadioGroup = findViewById(R.id.radioGroupGender);
        coughCheckBox = findViewById(R.id.checkBoxCough);
        fluCheckBox = findViewById(R.id.checkBoxFlu);
        diarrheaCheckBox = findViewById(R.id.checkBoxDiarrhea);

        setupSeekBar();

        Button submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(view -> showDialog());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.list) {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        nameEditText.getText().clear();
        nikEditText.getText().clear();
        nameEditText.getText().clear();
        genderRadioGroup.clearCheck();
        temperatureSeekBar.setProgress(0);
        temperatureTextView.setText(R.string._0);
        coughCheckBox.setChecked(false);
        fluCheckBox.setChecked(false);
        diarrheaCheckBox.setChecked(false);
        super.onResume();
    }

    @Override
    protected void onStop() {
        Toast.makeText(getApplicationContext(), "Data diterima!", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Konfirmasi Data");

        RadioButton genderRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());

        String name = nameEditText.getText().toString();
        String nik = nikEditText.getText().toString();
        String gender = genderRadioButton.getText().toString();
        String temperature = temperatureTextView.getText().toString();
        ArrayList<String> problems = (ArrayList<String>) getCheckedBoxValue();

        dialogBuilder.setMessage(
                "Apakah anda sudah yakin dengan data berikut?\n\n" +
                        "Nama: " + name + "\n" +
                        "NIK: " + nik + "\n" +
                        "Jenis Kelamin: " + gender + "\n" +
                        "Suhu Tubuh: " + temperature + "\n" +
                        "Keluhan: " + problems.toString() + "\n");
        dialogBuilder.setPositiveButton("Ya", (dialogInterface, i) -> {
            Student student = new Student(name, nik, gender, temperature, problems.toString());
            studentHealthDao.insert(student);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra(ResultActivity.PARCEL, student);
            startActivity(intent);

            dialogInterface.cancel();
        });
        dialogBuilder.setNegativeButton("Tidak", (dialogInterface, i) -> dialogInterface.cancel());
        dialogBuilder.setCancelable(true);

        AlertDialog confirmDialog = dialogBuilder.create();

        confirmDialog.show();
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

    private void logStudentHealthInfo() {
        List<Student> student = studentHealthDao.getAll();
        for (Student details: student) {
            System.out.println("Student " + details.getName());
        }
    }
}