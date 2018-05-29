package com.example.user.jolp_v0;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

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

public class ServiceThread extends Thread{
    Handler handler;
    boolean isRun = true;
    private static String TAG = "phptest";
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID="ID";
    private static final String TAG_BREATH="BREATH";
    private static final String TAG_DATE="DATE";
    private static final String TAG_STEP1="STEP1";
    private static final String TAG_STEP2="STEP2";
    private static final String TAG_STEP3="STEP3";
    private static final String TAG_STEP4="STEP4";
    private static final String TAG_STEP5="STEP5";
    private static final String TAG_INDEX="INDEX";

    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;
    static int index=-1;
    int kk = -1;
    int tag=-1;
    int gg=0;
    Date temp = new Date();




    public ServiceThread(Handler handler){
        this.handler = handler;
        mArrayList = new ArrayList<>();



    }

    public void stopForever(){
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run(){
        //반복적으로 수행할 작업을 한다.
        while(isRun){
            handler.sendEmptyMessage(0);//쓰레드에 있는 핸들러에게 메세지를 보냄
            try{
                //Thread.sleep(5000); //10초씩 쉰다.



                String serverURL = "http://show8258.ipdisk.co.kr:8000/breathlist.php?ID="+Main2Activity.id+"&INDEX="+index;




                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.connect();


                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "response code - " + responseStatusCode);

                    InputStream inputStream;
                    if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    }
                    else{
                        inputStream = httpURLConnection.getErrorStream();
                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = bufferedReader.readLine()) != null){
                        sb.append(line);
                    }


                    bufferedReader.close();

                    mJsonString = sb.toString().trim();
                    showResult();


                   // return sb.toString().trim();






                //GetData task = new GetData();
                //task.execute("http://show8258.ipdisk.co.kr:8000/breathlist.php?ID="+Main2Activity.id+"&INDEX="+index);
                //index = Integer.parseInt(Main2Activity.pref.getString("SET_1",Integer.toString(index)));

                //Main2Activity.editor.putString("SET_1",Integer.toString(index));
                //Main2Activity.editor.commit();
            }catch (Exception e) {}
        }
    }



    private void showResult(){
        mArrayList.clear();
        //Temp.date_Data.clear();
        //Temp.step_Data.clear();
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            int j;
            for(j=0;j<jsonArray.length();j++){

                JSONObject item = jsonArray.getJSONObject(j);

                String id = item.getString(TAG_ID);
                String breath = item.getString(TAG_BREATH);
                String date = item.getString(TAG_DATE);



                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_BREATH, breath);
                hashMap.put(TAG_DATE, date);


                mArrayList.add(hashMap);
            }
            JSONObject item = jsonArray.getJSONObject(j-1);
            Step.step_sec[1] = Integer.parseInt(item.getString(TAG_STEP1));
            Step.step_sec[2] = Integer.parseInt(item.getString(TAG_STEP2));
            Step.step_sec[3] = Integer.parseInt(item.getString(TAG_STEP3));
            Step.step_sec[4] = Integer.parseInt(item.getString(TAG_STEP4));
            Step.step_sec[5] = Integer.parseInt(item.getString(TAG_STEP5));
            //index = Integer.parseInt(item.getString(TAG_INDEX));
            if(kk == -1){
                index = Integer.parseInt(item.getString(TAG_INDEX));
                Temp.step_Data.clear();
                Temp.date_Data.clear();
                kk=1;
            }


            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

           //tag = -1;
            //Date temp = transFormat.parse("201702011535");


            int k;
            for(k=gg;k<mArrayList.size();k++){
                if(Double.parseDouble(mArrayList.get(k).get(TAG_BREATH)) == 0.0 && tag == 1){

                }
                else if(Double.parseDouble(mArrayList.get(k).get(TAG_BREATH)) == 0.0){
                    temp = transFormat.parse(mArrayList.get(k).get(TAG_DATE));
                    Temp.date_Data.add(temp);
                    tag = 1;
                }
                else if(Double.parseDouble(mArrayList.get(k).get(TAG_BREATH)) != 0.0 && tag == 1){
                    long second = (transFormat.parse(mArrayList.get(k).get(TAG_DATE)).getTime()-temp.getTime())/1000;
                    Temp.step_Data.add(second);
                    tag = -1;
                }
//                if((mArrayList.size()-1)==k && tag == 1){
//                    long second = (transFormat.parse(mArrayList.get(k).get(TAG_DATE)).getTime()-temp.getTime())/1000;
//                    Temp.step_Data.add(second);
//                    tag = -1;
//                }

            }
            gg = k;
            int i;
            for(i = index;i < Temp.step_Data.size();i++){
                Step.vib_occur((int) (long) Temp.step_Data.get(i),UpdateService.vib);
                //Toast.makeText(get, "회원가입 성공", Toast.LENGTH_SHORT).show();
                //Step.vib_occur(1000,UpdateService.vib);
            }
            index = Temp.step_Data.size();
            //GetData1 task1 = new GetData1();
            //task1.execute("http://show8258.ipdisk.co.kr:8000/breathlist.php?ID="+Main2Activity.id+"&INDEX="+index);


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private class GetData1 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressDialog = ProgressDialog.show(getBaseContext(),
//                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);


            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult1();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult1(){

    }
}

