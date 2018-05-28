package com.example.user.jolp_v0;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Step extends AppCompatActivity {

//    static Integer[] step_sec = new Integer[6];
    static int[] step_sec = {0,5,10,15,20,25};
    //static Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Button button1 = (Button)findViewById(R.id.step1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",1);
                startActivity(intent);
            }
        });
        Button button2 = (Button)findViewById(R.id.step2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",2);
                startActivity(intent);
            }
        });
        Button button3 = (Button)findViewById(R.id.step3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",3);
                startActivity(intent);
            }
        });
        Button button4 = (Button)findViewById(R.id.step4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",4);
                startActivity(intent);
            }
        });
        Button button5 = (Button)findViewById(R.id.step5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",5);
                startActivity(intent);
            }
        });
    }

    static void vib_occur(int second, Vibrator vibrator){

        int repeat;
        int myNum = 0;

        if(step_sec[5]<second){//step5발동
            myNum = 5;
        }
        else if(step_sec[4]<second){
            myNum = 4;
        }
        else if(step_sec[3]<second){
            myNum = 3;
        }
        else if(step_sec[2]<second){
            myNum = 2;
        }
        else if(step_sec[1]<second){
            myNum = 1;
        }


        long[] pattern = {500, 1000};
        for(repeat = 0; repeat < myNum ;repeat++) {
            vibrator.vibrate(pattern, -1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
