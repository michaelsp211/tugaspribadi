package com.learn.personal.androidmodulone.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

public class Student implements Parcelable {
    private final String name;
    private final String nik;
    private final String gender;
    private final String temperature;
    private final String problems;

    public String getName() {
        return name;
    }

    public String getNik() {
        return nik;
    }

    public String getGender() {
        return gender;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getProblems() {
        return problems;
    }

    public ArrayList<String> getProblemsList () {
        String[] problemsString = problems.replace("[", "").replace("]", "").split(", ");
        return new ArrayList<>(Arrays.asList(problemsString));
    }

    protected Student(Parcel in) {
        name = in.readString();
        nik = in.readString();
        gender = in.readString();
        temperature = in.readString();
        problems = in.readString();
    }

    public Student(String name, String nik, String gender, String temperature, String problems) {
        this.name = name;
        this.nik = nik;
        this.gender = gender;
        this.temperature = temperature;
        this.problems = problems;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nik);
        dest.writeString(gender);
        dest.writeString(temperature);
        dest.writeString(problems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
