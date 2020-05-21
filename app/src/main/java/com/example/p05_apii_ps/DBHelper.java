package com.example.p05_apii_ps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "P05.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NOTE = "songs";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SONG = "song";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STAR = "star";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SONG + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_YEAR + " TEXT,"
                + COLUMN_STAR + " TEXT ) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_NOTE + " ADD COLUMN module_name TEXT ");
    }


    public long insertNote(String song, String name, String year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG, song);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_STAR, stars);
        long result = db.insert(TABLE_NOTE, null, values);
        db.close();
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        else {
            Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1
        }
        return result;
    }

    public ArrayList<Song> getAllNotes() {
        ArrayList<Song> notes = new ArrayList<Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_SONG + ", "
                + COLUMN_NAME + ", "
                + COLUMN_YEAR + ", "
                + COLUMN_STAR
                + " FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songContent = cursor.getString(1);
                String nameContent = cursor.getString(2);
                String yearContent = cursor.getString(3);
                int starContent = cursor.getInt(4);
                Song note = new Song(id, songContent, nameContent, yearContent, starContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }


    public ArrayList<Song> getSpecificByStar(String keyword) {
        ArrayList<Song> notes = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SONG, COLUMN_NAME, COLUMN_YEAR, COLUMN_STAR};
        String condition = COLUMN_STAR + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_NOTE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songContent = cursor.getString(1);
                String nameContent = cursor.getString(2);
                String yearContent = cursor.getString(3);
                int starContent = cursor.getInt(4);
                Song note = new Song(id, songContent, nameContent, yearContent, starContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public ArrayList<Song> getSpecificByYear(String keyword) {
        ArrayList<Song> notes = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_SONG, COLUMN_NAME, COLUMN_YEAR, COLUMN_STAR};
        String condition = COLUMN_YEAR + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_NOTE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String songContent = cursor.getString(1);
                String nameContent = cursor.getString(2);
                String yearContent = cursor.getString(3);
                int starContent = cursor.getInt(4);
                Song note = new Song(id, songContent, nameContent, yearContent, starContent);
                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    public int updateNote(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SONG, data.getTitle());
        values.put(COLUMN_NAME, data.getSinger());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STAR, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_NOTE, values, condition, args);
        db.close();
        return result;
    }

    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NOTE, condition, args);
        db.close();
        return result;
    }
}
