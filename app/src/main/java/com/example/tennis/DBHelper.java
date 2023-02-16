package com.example.tennis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context ) {
        super(context,"UserData",null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE USERDETAILS (NAME TEXT PRIMARY KEY, NUMBER INTEGER NOT NULL);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists USERDETAILS");
    }
    public Boolean insetUserData(String name,String number){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        long result= DB.insert("USERDETAILS",null, contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public void updateUserData(String name,String number){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number",number);
        DB.update("USERDETAILS",contentValues, "name = ?", new String[]{name});
    }

    public Cursor getData(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM USERDETAILS ORDER BY number DESC",null);
        return cursor;
    }

    public void deleteCourse(String courseName){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("USERDETAILS", "name=?", new String[]{courseName});
        DB.close();
    }
}
