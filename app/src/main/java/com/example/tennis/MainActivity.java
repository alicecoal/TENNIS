package com.example.tennis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    // create variables of the two class

    SharedPreferences sharedPreferences;
    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    public EditText name;
    Button btnSubmit;
    TextView createAcc;
    DBHelper dbHelper;
    static String player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        sharedPreferences = getSharedPreferences("my_pref",0);
        Boolean e=false,p=false;
        name=findViewById(R.id.text_email);
        btnSubmit = findViewById(R.id.btnSubmit_login);
        dbHelper = new DBHelper(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameCheck = name.getText().toString();
                Cursor cursor = dbHelper.getData();
                if (checkName(nameCheck)){
                    boolean b =dbHelper.insetUserData(nameCheck,"0");player=nameCheck;
                    name.setText("");
                    GameView gameView = new GameView(view.getContext());
                    setContentView(gameView);
                    dbHelper.close();
                }
            }
        });
        createSensors();

    }

    public boolean checkName(String player_name){
        if (player_name=="") return false;
        return true;
    }

    public void hideRate(View view){
        LinearLayout l = findViewById(R.id.list_rate);
        ListView lv = findViewById(R.id.user_list);
        LinearLayout m = findViewById(R.id.start);
        l.setVisibility(View.GONE);
        m.setVisibility(View.VISIBLE);
        lv.setAdapter(null);
    }

    public static String getName(){
        return player;
    }

    public void showRate(View view){
        Cursor cursor = dbHelper.getData();
        ListView lv = findViewById(R.id.user_list);
        LinearLayout l = findViewById(R.id.list_rate);
        LinearLayout m = findViewById(R.id.start);
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("name",cursor.getString(0));
            user.put("number",cursor.getString(1));
            userList.add(user);
        }
        ListAdapter adapter = new SimpleAdapter(this, userList,
                R.layout.list_row, new String[]{"name","number"},
                new int[]{R.id.name, R.id.number});
        lv.setAdapter(adapter);
        m.setVisibility(View.GONE);
        l.setVisibility(View.VISIBLE);

    }

    public static boolean loginCheck(Cursor cursor,String nameCheck) {
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(nameCheck)) {
                return true;
            } else return false;
        }
        return false;
    }

    public void createSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        sensorEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotationMatrix = new float[16];
                SensorManager.getRotationMatrixFromVector(
                        rotationMatrix, event.values);
                float[] remappedRotationMatrix = new float[16];
                SensorManager.remapCoordinateSystem(rotationMatrix,
                        SensorManager.AXIS_Y,
                        SensorManager.AXIS_Z,
                        remappedRotationMatrix);
                float[] orientations = new float[3];
                SensorManager.getOrientation(remappedRotationMatrix, orientations);
                for (int i = 0; i < 3; i++)
                    orientations[i] = (float) (Math.toDegrees(orientations[i]));

                int xPosition = ((int) orientations[1]) * 20;
                int yPosition = ((int) orientations[2] + 90) * 10;
                Log.v("x",""+xPosition);
                Log.v("y",""+yPosition);
                GameView.paddleX += xPosition/10;
                if (GameView.paddleX<0) GameView.paddleX=0;
                if (GameView.paddleX>900) GameView.paddleX=900;
            }
            @Override
            public void onAccuracyChanged (Sensor sensor,int accuracy){
            }
        };
    }


    /*public void startGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }*/

    // create on resume method
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    // create on pause method
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
    }

}