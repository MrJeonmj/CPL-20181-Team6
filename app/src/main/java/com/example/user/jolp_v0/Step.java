package com.example.user.jolp_v0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Step extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Button button1 = (Button)findViewById(R.id.step1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button)findViewById(R.id.step2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                startActivity(intent);
            }
        });
        Button button3 = (Button)findViewById(R.id.step3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                startActivity(intent);
            }
        });
        Button button4 = (Button)findViewById(R.id.step4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                startActivity(intent);
            }
        });
        Button button5 = (Button)findViewById(R.id.step5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                startActivity(intent);
            }
        });
    }
}
