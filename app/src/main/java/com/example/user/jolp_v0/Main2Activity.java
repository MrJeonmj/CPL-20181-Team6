package com.example.user.jolp_v0;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView timeTv;
    InputMethodManager imm;
    PendingIntent sentPI;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private String Message = "비상상황입니다."; // 문자 보낼 메시지
    private String Phone = "01040304324";  // 문자 보낼 휴대폰 번호

    static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        id = getIntent().getStringExtra("id");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        timeTv = (TextView) findViewById(R.id.timeTv);
        //실시간 표시 기능
        MainTimerTask timerTask = new MainTimerTask();
        Timer timer = new Timer();
        timer.schedule(timerTask,500,1000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView bottomavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        // BottomNavigationViewHelperでアイテムのサイズ、アニメーションを調整
        BottomNavigationViewHelper.disableShiftMode(bottomavigation);
        // BottomNavigationViewを選択したときのリスナー
        bottomavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager manager = getFragmentManager();

                // 各選択したときの処理
                switch (item.getItemId()) {
                    case R.id.nav_temp:
                        manager.beginTransaction().add(R.id.content_main, new Temp()).commit();

                        return true;
                    case R.id.nav_home:
                        manager.beginTransaction().replace(R.id.content_main, new Maincontent2()).commit();

                        return true;
                    case R.id.nav_breath:
                        manager.beginTransaction().replace(R.id.content_main, new Breath()).commit();

                        return true;
                }

                return false;
            }
        });
        Intent intent1 = new Intent(this,Temp.class);
        startService(intent1);
        while(true){
            try {
                Thread.sleep(2000);
                Log.d(Integer.toString(Temp.date_Data.size()),"kkk");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    //비상상황 알림
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void em_click(View v){
        PendingIntent mpend = PendingIntent.getActivity(Main2Activity.this,0 ,
                new Intent(getApplicationContext(),Main2Activity.class) ,PendingIntent.FLAG_CANCEL_CURRENT);
        //상단바 알림
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
        Notification notification = new Notification.Builder(Main2Activity.this)
                .setContentTitle("App Name")
                .setContentText("비상상황입니다. 보호자 번호로 문자를 발송했습니다.")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(mpend)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(CHANNEL_ID)
                .build();
        notification.defaults |= Notification.DEFAULT_VIBRATE;//진동 울리기 안먹힘;;
        // Issue the notification.
        mNotificationManager.notify(notifyID, notification);
        //문자 보내기
        sendSMS(Phone, Message);
    }
    private void sendSMS(String phoneNumber, String message) {
        // 권한이 허용되어 있는지 확인한다
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},1);
            Toast.makeText(this,"권한을 허용하고 재전송해주세요",Toast.LENGTH_LONG).show();
        } else {
            SmsManager sms = SmsManager.getDefault();

            // 아래 구문으로 지정된 핸드폰으로 문자 메시지를 보낸다
            sms.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this,"전송을 완료하였습니다",Toast.LENGTH_LONG).show();
        }
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager = getFragmentManager();
        if (id == R.id.nav_home) {
            manager.beginTransaction().add(R.id.content_main, new Maincontent2()).commit();


        } else if (id == R.id.nav_temp) {
            manager.beginTransaction().add(R.id.content_main, new Temp()).commit();


        } else if (id == R.id.nav_breath) {
            manager.beginTransaction().add(R.id.content_main, new Breath()).commit();
        } else if (id == R.id.nav_setting) {
            Intent intent1 = new Intent(Main2Activity.this, User_Setting.class);
            startActivity(intent1);

        } else if (id == R.id.nav_logout) {
            Intent intent1 = new Intent(Main2Activity.this, Login.class);
            finish();
            startActivity(intent1);
        } else if (id == R.id.nav_asetting) {
            Intent intent1 = new Intent(Main2Activity.this, Step.class);
            startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
