package com.example.user.jolp_v0;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class Alarm extends AppCompatActivity {
    TextView tv;
    NumberPicker np;
    int step;
    //EditText et;
    phpdo task;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        step = getIntent().getIntExtra("step",1);
        np = findViewById(R.id.setsecond);
        np.setMinValue(1);
        np.setMaxValue(60);
        np.setValue(Step.step_sec[step]);
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
                        task = new phpdo();
                        task.execute(Integer.toString(step),Integer.toString(np.getValue()));


                  }
            }
        );
    }

    private class phpdo extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {

                String step = (String)arg0[0];
                String value = (String)arg0[1];

                String link = "http://show8258.ipdisk.co.kr:8000/join.php?ID="+Main2Activity.id+"&STEP"+step+"="+value;
                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result){
            //txtview.setText("Login Successful");
            switch (result){
                case "success":
                    Toast.makeText(Alarm.this, "설정되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "설정 실패", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }



}
