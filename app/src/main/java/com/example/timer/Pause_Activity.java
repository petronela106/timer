package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Pause_Activity extends AppCompatActivity implements View.OnClickListener  {


    private TextView pause;
    private CountDownTimer count;
    private  long timeLeft = 900000;
    private boolean timerRunning = false;
    private Button start_btn;
    private Button reset_btn;
    private Button minus_btn;
    private Button plus_btn;
    private Button back_btn;
    private MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pause_);

        start_btn = (Button) findViewById(R.id.startBtn);
        start_btn.setOnClickListener(this);

        reset_btn = (Button) findViewById(R.id.resetBtn);
        reset_btn.setOnClickListener(this);

        minus_btn = (Button) findViewById(R.id.minusBtn);
        minus_btn.setOnClickListener(this);

        plus_btn = (Button) findViewById(R.id.plusBtn);
        plus_btn.setOnClickListener(this);

        back_btn = (Button) findViewById(R.id.backBtn);
        back_btn.setOnClickListener(this);

        pause = (TextView) findViewById(R.id.pause);

        mp = MediaPlayer.create(this, R.raw.music);


    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {

            case R.id.startBtn: {

                if (timerRunning != true) {
                    timerRunning = true;
                    start_btn.setBackgroundResource(R.drawable.stop);
                    startTimer();

                } else {
                    timerRunning = false;
                    start_btn.setBackgroundResource(R.drawable.play);
                    pauseTimer();


                }
                break;
            }



            case R.id.resetBtn: {
                timerRunning = false;
                resetTimer();
                start_btn.setBackgroundResource(R.drawable.play);
                break;
            }

            case R.id.minusBtn: {

                minusTimer();
                start_btn.setBackgroundResource(R.drawable.play);
                break;
            }
            case R.id.plusBtn: {

                plusTimer();
                start_btn.setBackgroundResource(R.drawable.play);
                break;
            }
            case R.id.backBtn: {
                Intent intent = new Intent(Pause_Activity.this, Main.class);
                startActivity(intent);
                break;
            }

        }
    }

    public void startTimer(){
        count = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void pauseTimer(){

        if(timerRunning == true){
            count.cancel();
            Toast.makeText(Pause_Activity.this, "pause timer", Toast.LENGTH_SHORT).show();
        }

    }


    public void resetTimer(){

        pauseTimer();
        Toast.makeText(Pause_Activity.this, "Reset timer", Toast.LENGTH_SHORT).show();
        timeLeft = 900000;
        updateTimer();

    }

    public void updateTimer(){
        int minutes =  (int) timeLeft / 60000;
        int seconds =  (int) timeLeft % 60000 / 1000;

        if((int)timeLeft < 1000){
            pauseTimer();
            mp.start();
            start_btn.setBackgroundResource(R.drawable.play);

        }

        String text = "";

        if(minutes >= 60){

            text = "" + (minutes / 60 );
            text +=  ":";



            if(minutes % 60 < 10){
                text += "0";
            }

            text += minutes % 60;
            text += ":";

        }else{
            text = minutes + ":";
        }

        if(seconds <= 9){
            text += "0" + seconds;
        }else{
            text += seconds;
        }

        pause.setText(text);

    }

    public void  minusTimer(){

        timeLeft -= 60000;
        updateTimer();
        pauseTimer();

    }

    public void  plusTimer(){
        pauseTimer();
        timeLeft += 60000;
        updateTimer();

    }





    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
