package com.example.user.jolp_v0;

import android.Manifest;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Maincontent2 extends Fragment {
    View v;
    Timer mTimer;
    TextView timeTv;
    InputMethodManager imm;
    PendingIntent sentPI;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private String Message = "비상상황입니다."; // 문자 보낼 메시지
    private String Phone = "01040304324";  // 문자 보낼 휴대폰 번호

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate메소드는 XML데이터를 가져와서 실제 View객체로 만드는 작업을 합니다.
        v = inflater.inflate(R.layout.content_main2, container, false);
        //imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        timeTv = (TextView) v.findViewById(R.id.timeTv);
        ImageButton em_btn = (ImageButton) v.findViewById(R.id.emg_button);
        em_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                em_click(v);
            }
        });
        //실시간 표시 기능
        MainTimerTask timerTask = new MainTimerTask();
        Timer timer = new Timer();
        timer.schedule(timerTask,500,1000);



        return v;
    }
    //이미지 버튼 클릭시 수행
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void em_click(View v){
        PendingIntent mpend = PendingIntent.getActivity(getActivity().getApplicationContext(),0 ,
                new Intent(getActivity().getApplicationContext(),Maincontent2.class) ,PendingIntent.FLAG_CANCEL_CURRENT);
        //상단바 알림
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        // 채널 ID
        String id = "my_channel_01";
        // 채널 이름
        CharSequence name = "test";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // 알림 채널에 사용할 설정을 구성
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mChannel.setShowBadge(false);
        mNotificationManager.createNotificationChannel(mChannel);

        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";
        // 알림을 만들고 알림 채널을 설정
        Notification notification = new Notification.Builder(getActivity().getApplicationContext())
                .setContentTitle("App Name")
                .setContentText("비상상황입니다. 보호자 번호로 문자를 발송했습니다.")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(mpend)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(CHANNEL_ID)
                .build();

        // Issue the notification.
        mNotificationManager.notify(notifyID, notification);
        //문자 보내기
        sendSMS(Phone, Message);
    }
    private void sendSMS(String phoneNumber, String message) {
        // 권한이 허용되어 있는지 확인한다
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.SEND_SMS);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.SEND_SMS},1);
            Toast.makeText(getActivity(),"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
        } else {
            SmsManager sms = SmsManager.getDefault();

            // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
            sms.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getActivity(),"전송을 완료하였습니다",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        timeTv = (TextView) v.findViewById(R.id.timeTv);
        //실시간 표시 기능
        MainTimerTask timerTask = new MainTimerTask();
        Timer timer = new Timer();
        timer.schedule(timerTask,500,1000);

    }
    //실시간 표시 함수
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Date rightNow = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy.MM.dd hh:mm:ss ");
            String dateString = formatter.format(rightNow);
            timeTv.setText(dateString);
        }
    };
    class MainTimerTask extends TimerTask {
        public void run() {
            mHandler.post(mUpdateTimeTask);
        }
    }

}