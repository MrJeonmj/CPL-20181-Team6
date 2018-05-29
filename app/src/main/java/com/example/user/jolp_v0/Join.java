package com.example.user.jolp_v0;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
// import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.net.URLEncoder;


public class Join extends FragmentActivity {

    phpdo task;

    EditText eid;
    EditText epw, pnum, devicenum;
    EditText ename, econtact, eaddress;
    TextView ebirth;
    //String epatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_join);

        eid = (EditText)findViewById(R.id.join_id);
        epw = (EditText)findViewById(R.id.join_pw);
        ename = (EditText) findViewById(R.id.join_name);
        econtact = (EditText)findViewById(R.id.join_contact);
        eaddress = (EditText)findViewById(R.id.join_address);
        ebirth = (TextView) findViewById(R.id.join_birth_text);
        devicenum = (EditText)findViewById(R.id.join_device);
        pnum = (EditText)findViewById(R.id.join_pnum);

//        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);



        Button buttondate = (Button)findViewById(R.id.join_birth_btn);
        buttondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);

//                DatePickerDialog dialog = new DatePickerDialog(this, listener, 2013, 10, 22);
//                dialog.show();


            }


        });






        Button buttonInsert = (Button)findViewById(R.id.join_insert_btn);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = eid.getText().toString();
                String pw = epw.getText().toString();
                String name = ename.getText().toString();
                String contact = econtact.getText().toString();
                String address = eaddress.getText().toString();
                String birth = ebirth.getText().toString();
                String devi = devicenum.getText().toString();
                String pn = pnum.getText().toString();


//                final RadioGroup rg = (RadioGroup)findViewById(R.id.btn_is_patient);
//                int radio_id = rg.getCheckedRadioButtonId();
//                RadioButton rb = (RadioButton) findViewById(radio_id);
//                if(rb.getText().equals("환자")){
//                    epatient = Integer.toString(1);
//                }
//                else{
//                    epatient = Integer.toString(0);
//                }




                task = new phpdo();
                task.execute(id, pw, name, contact, address, birth, devi, pn);
                //finish();

            }
        });

    }

    protected Dialog onCreateDialog(int id) {

//        int datePickerThemeResId = 0;
//        if (android.os.Build.VERSION.SDK_INT >= 21) {
//            datePickerThemeResId = android.R.style.Widget_Material_NumberPicker;
//        }



        DatePickerDialog dpd = new DatePickerDialog
                (Join.this, // 현재화면의 제어권자
                        AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view,
                                                  int year, int monthOfYear,int dayOfMonth) {
                                String month;
                                if(monthOfYear<10){
                                    month = "0"+(monthOfYear+1);
                                }
                                else{
                                    month = Integer.toString(monthOfYear+1);
                                }
                                String day;
                                if(dayOfMonth<10){
                                    day = "0"+(dayOfMonth);
                                }
                                else{
                                    day = Integer.toString(dayOfMonth);
                                }

                                ebirth.setText(year+"-"+month+"-"+day);
                            }
                        }
                        , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                        //    호출할 리스너 등록
                        2015, 6, 21); // 기본값 연월일
        return dpd;
        //return super.onCreateDialog(id);




    }

//    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
//
//        @Override
//
//        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//            Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
//
//        }
//
//    };


    private class phpdo extends AsyncTask<String,Void,String> {

        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... arg0) {

            try {
                String id = (String)arg0[0];
                String pw = (String)arg0[1];
                String name = (String)arg0[2];
                String contact = (String)arg0[3];
                String address = (String)arg0[4];
                String birth = (String)arg0[5];
                String dev = (String)arg0[6];
                String pn = (String)arg0[7];

                String link = "http://show8258.ipdisk.co.kr:8000/join.php?ID="+id+"&PW="+pw+"&NAME="+name+"&CONTACT="+contact+"&ADDRESS="+address+"&BIRTHDATE="+birth+"&P_NUM="+pn+"&DEVICE="+dev;
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
                case "join_success":
                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case "id_overlap":
                    Toast.makeText(getApplicationContext(), "ID가 중복됩니다.", Toast.LENGTH_SHORT).show();
                    break;
                case "blank_existed":
                    Toast.makeText(getApplicationContext(), "빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }

}
