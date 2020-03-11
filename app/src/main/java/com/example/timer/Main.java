package com.example.timer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private TextView timer;
    private CountDownTimer count;
    private  long timeLeft = 3600000;
    private boolean timerRunning = false;
    private Button start_btn;
    private Button reset_btn;
    private Button minus_btn;
    private Button plus_btn;
    private MediaPlayer mp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        start_btn = (Button) findViewById(R.id.start_btn);
        start_btn.setOnClickListener(this);

        reset_btn = (Button) findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(this);

        minus_btn = (Button) findViewById(R.id.minus_btn);
        minus_btn.setOnClickListener(this);

        plus_btn = (Button) findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(this);


        timer = (TextView) findViewById(R.id.timer);

        mp = MediaPlayer.create(this, R.raw.music);



    }



    @Override
    public void onClick(View v){
        switch (v.getId()) {

            case R.id.start_btn: {

                if (timerRunning != true) {
                    timerRunning = true;
                    start_btn.setBackgroundResource(R.drawable.stop);
                    startTimer();

                } else {
                    timerRunning = false;
                    start_btn.setBackgroundResource(R.drawable.play);
                    pauseTimer();


                }
            }
            break;


            case R.id.reset_btn: {
                timerRunning = false;
                resetTimer();
                start_btn.setBackgroundResource(R.drawable.play);
                break;
            }

            case R.id.minus_btn: {

                minusTimer();
                start_btn.setBackgroundResource(R.drawable.play);
                break;
            }
            case R.id.plus_btn: {

                plusTimer();
                start_btn.setBackgroundResource(R.drawable.play);
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

        if(timerRunning ){
            count.cancel();
            start_btn.setBackgroundResource(R.drawable.play);
            Toast.makeText(Main.this, "pause timer", Toast.LENGTH_SHORT).show();
        }

    }


    public void resetTimer(){

        pauseTimer();
        Toast.makeText(Main.this, "Reset timer", Toast.LENGTH_SHORT).show();
        timeLeft = 3600000;
        updateTimer();

    }

    public void updateTimer(){
        int minutes =  (int) timeLeft / 60000;
        int seconds =  (int) timeLeft % 60000 / 1000;

        if((int)timeLeft < 1000){
            pauseTimer();
            mp.start();
            start_btn.setBackgroundResource(R.drawable.play);
            startActivity(new Intent(Main.this, PopUp_button.class));
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

        timer.setText(text);

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

