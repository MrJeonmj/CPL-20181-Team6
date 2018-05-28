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

public class Alarm extends AppCompatActivity {
    TextView tv;
    NumberPicker np;
    EditText et;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        np = findViewById(R.id.setsecond);
        np.setMinValue(1);
        np.setMaxValue(60);
        np.setValue(0);
        et = findViewById(R.id.vibrateNum);

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
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                int repeat;
                int myNum = 0;
                myNum = Integer.parseInt(et.getText().toString());
                long[] pattern = {500, np.getValue()*1000};
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
        );
    }
}
