package com.example.user.jolp_v0;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class Alarm extends AppCompatActivity {
    TextView tv;
    NumberPicker np;
    int step;
    //EditText et;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        step = getIntent().getIntExtra("step",1);
        np = findViewById(R.id.setsecond);
        np.setMinValue(1);
        np.setMaxValue(60);
        np.setValue(1);
        //et = findViewById(R.id.vibrateNum);


        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView tv = (TextView) findViewById(R.id.time);
                tv.setText(np.getValue() + "");
            }
        });
        Button button = (Button) findViewById(R.id.test);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                        Step.step_sec[step] = np.getValue();
                        finish();
                Toast.makeText(Alarm.this, "설정되었습니다.", Toast.LENGTH_SHORT).show();
                  }
            }
        );
    }



}
