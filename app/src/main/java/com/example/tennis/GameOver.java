package com.example.tennis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;
    DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest) ;
        int points = getIntent().getExtras().getInt("points");
        String name = getIntent().getStringExtra("name");
        tvPoints.setText(""+points);
        sharedPreferences = getSharedPreferences("my_pref", 0);
        int highest = sharedPreferences.getInt("highest",0);
        if (check(points, highest)){
            ivNewHighest.setVisibility(View.VISIBLE);
            dbHelper = new DBHelper(this);
            Cursor cursor = dbHelper.getData();
            while (cursor.moveToNext()){
                if (cursor.getString(0).equals(name)) {
                        dbHelper.updateUserData(name, Integer.toString(points));
                }
            }
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest",highest);
            editor.commit();
        }
        tvHighest.setText(""+highest);
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        finish();
    }
    public boolean check(int a, int b){
        if (a>b) return true;
        else return false;
    }
}
