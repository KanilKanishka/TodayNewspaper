package com.example.todaynewspaper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NewspaperDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "newspaper.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "newspapers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public NewspaperDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_DATE + " TEXT" +
                ")";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertNewspaper(Newspaper newspaper) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newspaper.getTitle());
        values.put(COLUMN_DESCRIPTION, newspaper.getDescription());
        values.put(COLUMN_DATE, newspaper.getDate());

        long rowId = db.insert(TABLE_NAME, null, values);
        db.close();
        return rowId;
    }

    public List<Newspaper> getAllNewspapers() {
        List<Newspaper> newspapers = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                Newspaper newspaper = new Newspaper(id, title, description, date);
                newspapers.add(newspaper);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return newspapers;
    }

    public int updateNewspaper(Newspaper newspaper) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newspaper.getTitle());
        values.put(COLUMN_DESCRIPTION, newspaper.getDescription());
        return 0;
    }
}

