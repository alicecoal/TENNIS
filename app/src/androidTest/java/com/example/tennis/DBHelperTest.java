package com.example.tennis;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DBHelperTest {

    private DBHelper databaseHelper;

    @Before
    public void setUp() throws Exception {
        getApplicationContext().deleteDatabase("UserData");
        databaseHelper = new DBHelper(getApplicationContext());
    }

    @After
    public void tearDown() throws Exception{
        databaseHelper.close();
    }
    @Test
    public void add() throws Exception {
        databaseHelper.insetUserData("EDDI","2");
        Cursor cursor = databaseHelper.getData();
        while(cursor.moveToNext()){
            assertTrue(cursor.getString(0).equals("EDDI"));
        }

    }

    @Test
    public void add1() throws Exception {
        databaseHelper.insetUserData("Mark","3");
        Cursor cursor = databaseHelper.getData();
        while(cursor.moveToNext()){
            assertTrue(cursor.getString(1).equals("3"));
        }

    }

    @Test
    public void update() throws Exception {
        databaseHelper.insetUserData("Mark","3");
        databaseHelper.updateUserData("Mark","2");
        Cursor cursor = databaseHelper.getData();
        while(cursor.moveToNext()){
            assertTrue(cursor.getString(1).equals("2"));
        }

    }

    @Test
    public void update1() throws Exception {
        databaseHelper.insetUserData("Mark","3");
        databaseHelper.updateUserData("Mark","4");
        Cursor cursor = databaseHelper.getData();
        while(cursor.moveToNext()){
            assertTrue(cursor.getString(1).equals("4"));
        }

    }

    @Test
    public void delete() throws Exception {
        databaseHelper.insetUserData("Mark","3");
        databaseHelper.updateUserData("Race","4");
        databaseHelper.deleteCourse("Race");
        Cursor cursor = databaseHelper.getData();
        int kol=0;
        while(cursor.moveToNext()){
            if (cursor.getString(0).equals("Race")) kol++;
        }
        assertTrue(kol==0);
    }

    @Test
    public void delete2() throws Exception {
        databaseHelper.insetUserData("Mark","3");
        databaseHelper.deleteCourse("Mark");
        Cursor cursor = databaseHelper.getData();
        int kol=0;
        while(cursor.moveToNext()){
            if (cursor.getString(0).equals("Mark")) kol++;
        }
        assertTrue(kol==0);
    }
    

}