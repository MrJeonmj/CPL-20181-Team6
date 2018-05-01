package com.example.user.jolp_v0;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.StringTokenizer;


public class Emergency extends AppCompatActivity {
    phpdo task;
    String id;
    Button step1,step2,step3,step4,step5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Intent intent = getIntent();
        id = (String) intent.getStringExtra("id");

        step1 = (Button)findViewById(R.id.emstep1);
        step2 = (Button)findViewById(R.id.emstep2);
        step3 = (Button)findViewById(R.id.emstep3);
        step4 = (Button)findViewById(R.id.emstep4);
        step5 = (Button)findViewById(R.id.emstep5);

        task = new phpdo();
        task.execute(id);




        step1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Emergency.this, Emergency_History.class);
                intent1.putExtra("id",id);
                intent1.putExtra("step",1);
                startActivity(intent1);
            }
        });
        step2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Emergency.this, Emergency_History.class);
                intent1.putExtra("id",id);
                intent1.putExtra("step",2);
                startActivity(intent1);
            }
        });
        step3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Emergency.this, Emergency_History.class);
                intent1.putExtra("id",id);
                intent1.putExtra("step",3);
                startActivity(intent1);
            }
        });
        step4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Emergency.this, Emergency_History.class);
                intent1.putExtra("id",id);
                intent1.putExtra("step",4);
                startActivity(intent1);
            }
        });
        step5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Emergency.this, Emergency_History.class);
                intent1.putExtra("id",id);
                intent1.putExtra("step",5);
                startActivity(intent1);
            }
        });
    }

    private class phpdo extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {
                String id = (String)arg0[0];

                String link = "http://show8258.ipdisk.co.kr:8000/emergencynum.php?ID="+id;
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
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            step1.setText(result);
            StringTokenizer tokens = new StringTokenizer(result);
            step1.setText(tokens.nextToken(","));
            step2.setText(tokens.nextToken(","));
            step3.setText(tokens.nextToken(","));
            step4.setText(tokens.nextToken(","));
            step5.setText(tokens.nextToken(","));

        }
    }
}
