package com.example.user.jolp_v0;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class Step extends AppCompatActivity {

//    static Integer[] step_sec = new Integer[6];
    static int[] step_sec = {0,5,10,15,20,25};
    //static Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        ImageButton button1 = (ImageButton)findViewById(R.id.step1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",1);
                startActivity(intent);
            }
        });
        ImageButton button2 = (ImageButton)findViewById(R.id.step2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",2);
                startActivity(intent);
            }
        });
        ImageButton button3 = (ImageButton)findViewById(R.id.step3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",3);
                startActivity(intent);
            }
        });
        ImageButton button4 = (ImageButton)findViewById(R.id.step4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Step.this,Alarm.class);
                intent.putExtra("step",4);
                startActivity(intent);
            }
        });
        ImageButton button5 = (ImageButton)findViewById(R.id.step5);
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
//    private static void sendSMS(String phoneNumber, String message)
//    {
//        // 권한이 허용되어 있는지 확인한다
//        int permissionCheck = ContextCompat.checkSelfPermission(Main2Activity.kk, android.Manifest.permission.SEND_SMS);
//
//        if (permissionCheck == PackageManager.PERMISSION_DENIED)
//        {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
//            Toast.makeText(Main2Activity.kk, "권한을 허용하고 재전송해주세요", Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            SmsManager sms = SmsManager.getDefault();
//
//            // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
//            sms.sendTextMessage(phoneNumber, null, message, null, null);
//            Toast.makeText(Main2Activity.kk, "전송을 완료하였습니다", Toast.LENGTH_LONG).show();
//        }
//        return;
//    }
}
