package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    ImageButton start, stop;

    private boolean isResume;
    Handler handler;
    long tMilliSec, tStart, tBuff, tUpdate = 0L;
    int sec, min, milliSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        start = findViewById(R.id.btnStart);
        stop = findViewById(R.id.btnStop);

        handler = new Handler();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    stop.setVisibility(View.GONE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black_24dp));
                }
                else {
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    stop.setVisibility(View.VISIBLE);
                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isResume){

                    start.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                    tMilliSec = 0L;
                    tStart = 0L;
                    tBuff = 0L;
                    sec = 0;
                    min = 0;
                    milliSec = 0;
                    chronometer.setText("00:00:00");
                }
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min)+":"+
                    String.format("%02d", sec)+":"+
                    String.format("%02d", milliSec));
            handler.postDelayed(this, 50);
        }
    };

}
