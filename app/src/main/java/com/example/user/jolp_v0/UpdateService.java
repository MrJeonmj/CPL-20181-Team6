package com.example.user.jolp_v0;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UpdateService extends Service {

    ServiceThread thread;
    static Vibrator vib;
    static int index=0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();

//        GetData task = new GetData();
//        task.execute("http://show8258.ipdisk.co.kr:8000/breathlist.php?ID="+Main2Activity.id);
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // 서비스가 종료될 때 실행

    }

    class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            //Intent intent = new Intent(UpdateService.this, Main2Activity.class);
            //PendingIntent pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            for(int i = index;i<Temp.step_Data.size();i++){
//                Step.vib_occur((int) (long) Temp.step_Data.get(i),vib);
//            }
//            index = Temp.step_Data.size();

        }
    };


}
