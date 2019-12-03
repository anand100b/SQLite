package com.example.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    //create database constants
    public static final String DATABASE_NAME ="monster.db";
    public static final Integer DATABASE_VERSION =2;
    public static final String TABLE_NAME ="monster";


    //create columns of monster table
    public static final String COL_ID ="ID";
    public static final String COL_NAME ="NAME";
    public static final String COL_DESCRIPTION ="DESCRIPTION";
    public static final String COL_SCARINESS ="SCARINESS";
    public static final String COL_IMAGE ="IMAGE";


    private static final String CREATE_TABLE_ST = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION TEXT, SCARINESS INTEGER, IMAGE TEXT)";
    private static final String DROP_TABLE_ST = "DROP TABLE IF EXISTS "+ TABLE_NAME;
    private static final String GET_ALL_ST = "SELECT * FROM " + TABLE_NAME ;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_ST);
        onCreate(db);

    }

    public boolean insert(String name, String description, Integer scariness){
        //create an instance of sqlite database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, name);
        cv.put(COL_DESCRIPTION, description);
        cv.put(COL_SCARINESS, scariness);
        cv.put(COL_IMAGE,getRandomImageName());
        long result = db.insert(TABLE_NAME, null, cv);
        return result==-1? false:true;
    }


    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(GET_ALL_ST, null);
        return cursor;

    }

    public boolean update(Integer id, String name, String description, Integer scariness){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID,id);
        cv.put(COL_NAME, name);
        cv.put(COL_DESCRIPTION, description);
        cv.put(COL_SCARINESS, scariness);

       int numRowsUpdated = db.update(TABLE_NAME, cv, "ID = ?", new String[]{id.toString()});
        return  numRowsUpdated == 1? true : false;

    }
    public boolean delete( Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRowsDelete = db.delete(TABLE_NAME, "ID = ?", new String[]{id.toString()});
        return  numRowsDelete == 1? true : false;

    }


    private String getRandomImageName() {
        Random ran  = new Random();
        int value =ran.nextInt(30)+1;
        return "ic_monster_" + value;
    }
}
