package com.example.shbaek.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    int interval = 1000;

    MediaPlayer mediaPlayer;
    TextView textView;
    SeekBar seekBar;
    Boolean counterIsActive = false;
    Button startButton;
    CountDownTimer countDownTimer;

    public void resetTimer() {
        textView.setText("00:30");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        countDownTimer.cancel();
        startButton.setText("START!");
        counterIsActive = false;
    }

    public void startTimer(View view){

        if(counterIsActive){

            resetTimer();

        } else {

            counterIsActive = true;
            seekBar.setEnabled(false);
            startButton.setText("Stop!");

            countDownTimer = new CountDownTimer(seekBar.getProgress()*1000, interval){

                public void onTick(long l){

                    updateTimer((int) l/1000);

                }

                public void onFinish(){
                    textView.setText("Done!!!!!");
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        startButton = findViewById(R.id.button);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void updateTimer(int secondLeft){

        int minutes = secondLeft / 60 ;
        int seconds = secondLeft - (minutes * 60);

        String minutesString = Integer.toString(minutes);
        String secondsString = Integer.toString(seconds);

        if(minutes < 10) {
            minutesString = "0"+minutesString;
        }

        if(seconds < 10) {
            secondsString = "0"+secondsString;
        }

        textView.setText(minutesString +  ":" + secondsString);

    }

}
