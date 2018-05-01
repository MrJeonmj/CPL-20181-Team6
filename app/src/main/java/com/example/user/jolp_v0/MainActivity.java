package com.example.user.jolp_v0;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends FragmentActivity {

    phpdo task;

    static EditText eid;
    static EditText epw;
    static String id;
    static String pw;
    TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        eid = (EditText)findViewById(R.id.login_id);
        epw = (EditText)findViewById(R.id.login_pw);
//        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);


        Button buttonInsert = (Button)findViewById(R.id.login_btn);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent a = new Intent(MainActivity.this, Menur.class);
//                startActivity(a);
//
                id = eid.getText().toString();
                pw = epw.getText().toString();
//
//
                task = new phpdo();
                task.execute(id, pw);



            }
        });

        Button buttonjoin = (Button)findViewById(R.id.login_join_btn);
        buttonjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent a = new Intent(MainActivity.this, Join.class);
                startActivity(a);


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
                String pw = (String)arg0[1];

                String link = "http://show8258.ipdisk.co.kr:8000/logintest.php?ID="+id+"&PW="+pw;
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
            //mTextViewResult.setText(result);

            if(result.equals("login_success")){
                Intent intent1 = new Intent(MainActivity.this, Menur.class);
                intent1.putExtra("id",eid.getText().toString());
                startActivity(intent1);
                eid.setText("");
                epw.setText("");
                //finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "ID, PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        }
    }

}