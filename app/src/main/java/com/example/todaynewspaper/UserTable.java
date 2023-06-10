package com.example.todaynewspaper;

public class UserTable {
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT)";

    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
}

