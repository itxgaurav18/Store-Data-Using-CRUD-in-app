package com.example.logininfo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_NAME = "user_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_PHONE + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_ADDRESS + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);
    }

    // Upgrade the database if necessary
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert a new user
    public long insertUser(String firstName, String lastName, String phone, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ADDRESS, address);

        long id = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    // Get all users
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Update a user

    // Delete a user
    // Update a user
    public boolean updateUser(int id, String firstName, String lastName, String phone, String email, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ADDRESS, address);

        int rowsUpdated = db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsUpdated > 0;
    }

    // Delete a user
    public boolean deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

}



