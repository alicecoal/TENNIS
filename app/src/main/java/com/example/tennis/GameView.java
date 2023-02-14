package com.example.tennis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;

import java.util.Random;

public class GameView extends View {
    Context context;
    float ballX, ballY;
    Velocity velocity = new Velocity(25,32);
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    static float paddleX, paddleY;
    float oldX, oldPaddleX;
    int points = 0;
    int life = 3;
    Bitmap ball, paddle;
    int dWidth, dHeight;
    Random random;
    static String name;

    public GameView(Context context) {
        super(context);
        this.context = context;
        ball = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        paddle = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
        handler = new Handler();
        runnable = new Runnable(){
            @Override
            public void run(){
                invalidate();
            }
        };
        textPaint.setColor(Color.rgb(197,205,190));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        healthPaint.setColor(Color.GREEN);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        random = new Random();
        ballX = random.nextInt(dWidth);
        paddleY = (dHeight*4)/5;
        paddleX = dWidth/2 - paddle.getWidth()/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.rgb(33,99,00));
        ballX += velocity.getX();
        ballY += velocity.getY();
        if ((ballX >= dWidth - ball.getWidth()) || (ballX<=0)){
            velocity.setX(velocity.getX()*-1);
        }
        if (ballY<=0){
            velocity.setY(velocity.getY()*-1);
        }
        if (ballY > paddleY+paddle.getHeight()){
            ballX = 1+random.nextInt(dWidth - ball.getWidth()-1);
            ballY = 0;
            velocity.setX(xVelocity());
            velocity.setY(32);
            life--;
            if (life == 0){
                Intent intent = new Intent(context, GameOver.class);
                intent.putExtra("points",points);
                intent.putExtra("name",name);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        }
        if (((ballX + ball.getWidth()) >= paddleX) && (ballX <= paddleX + paddle.getWidth()) && (ballY + paddle.getHeight() >= paddleY) && (ballY + ball.getHeight() <= paddleY + paddle.getHeight())){
            velocity.setX(velocity.getX()+1);
            velocity.setY((velocity.getY()+1)*-1);
            points++;
        }
        canvas.drawBitmap(ball, ballX, ballY, null);
        canvas.drawBitmap(paddle, paddleX, paddleY, null);
        canvas.drawText(""+points, 20, TEXT_SIZE, textPaint);
        if (life == 2){
            healthPaint.setColor(Color.YELLOW);
        }else if (life == 1){
            healthPaint.setColor(Color.RED);
        }
        canvas.drawRect(dWidth-200, 30, dWidth-200+60*life, 80, healthPaint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    public static void setName(String name1){
        name=name1;
    }


    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= paddleY) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
                oldPaddleX = paddleX;
            }
            if (action == MotionEvent.ACTION_MOVE) {



            }
        }
        return true;
    }*/

    private int xVelocity() {
        int[] values= {-35, -30, -25, 25, 30, 35};
        int index = random.nextInt(6);
        return values[index];
    }
}
