package com.learn.personal.androidmodulone.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler INSTANCE;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "student";

    public final String TABLE_HEALTH = "student_health";
    public final String HEALTH_KEY_ID = "id";
    public final String HEALTH_KEY_NAME = "name";
    public final String HEALTH_KEY_NIK = "nik";
    public final String HEALTH_KEY_GENDER = "gender";
    public final String HEALTH_KEY_TEMPERATURE = "temperature";
    public final String HEALTH_KEY_PROBLEMS = "problems";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance(Context context)
    {
        if (INSTANCE == null)
            INSTANCE = new DatabaseHandler(context);

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HEALTH_TABLE =
                "CREATE TABLE " + TABLE_HEALTH + "("
                + HEALTH_KEY_ID + " INTEGER PRIMARY KEY,"
                + HEALTH_KEY_NAME + " TEXT,"
                + HEALTH_KEY_NIK + " TEXT,"
                + HEALTH_KEY_GENDER + " TEXT,"
                + HEALTH_KEY_TEMPERATURE + " TEXT,"
                + HEALTH_KEY_PROBLEMS + " TEXT"
                + ")";
        db.execSQL(CREATE_HEALTH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH);
        onCreate(db);
    }
}
