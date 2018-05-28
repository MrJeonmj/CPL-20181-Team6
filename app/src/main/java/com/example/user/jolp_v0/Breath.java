package com.example.user.jolp_v0;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by kch on 2017. 9. 22..
 */

public class Breath extends Fragment {
    View v;

    private ArrayList<String> labels;
    private ArrayList<Entry> entries;
    private ArrayList<String> breathData = new ArrayList<>();

    // constants
    private final int DATA_GETTING_MODE_YEAR = 0;
    private final int DATA_GETTING_MODE_MONTH = 1;
    private final int DATA_GETTING_MODE_WEEK = 2;
    private final int DATA_GETTING_MODE_DAY = 3;
    private final int DATA_GETTING_MODE_HOUR = 4;
    // private final int DATA_GETTING_RECENT = 5;
    // private final int DATA_GETTING_RECENT_LENGTH = 50;

    private static String TAG = "phptest";
    private static final String TAG_JSON="webnautes";
    private static final String TAG_ID="ID";
    private static final String TAG_DATE="DATE";
    private static final String TAG_BREATH ="BREATH";

    private ArrayList<HashMap<String, String>> mArrayList;
    private String mJsonString;

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);


            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
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

    private void getLabelsAndEntries(int dataGettingMode)
    {
        labels = new ArrayList<>();
        entries = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();

        Date accessTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(accessTime);
        cal.setLenient(true);
        SimpleDateFormat f;

        String[] str_data = breathData.toArray(new String[breathData.size()]);
        int[] data = new int[str_data.length];
        for (int i = 0; i < data.length; ++i)
            data[i] = Integer.parseInt(str_data[i]);

        if (dataGettingMode == DATA_GETTING_MODE_YEAR)
        {
            // get labels
            f = new SimpleDateFormat("yyyy/MM", Locale.KOREA);
            for (int i = 0; i < 12; ++i)
            {
                temp.add(f.format(cal.getTime()));
                cal.add(Calendar.MONTH, -1);
            }
            Collections.reverse(temp);

            // get entries; TODO: average of each month for 12 months
            double[] sums = new double[12];
            int[] count = new int[12];
            double[] averages = new double[12];
            for (HashMap<String,String> h: mArrayList)
            {
                try
                {
                    String datestr = h.get(TAG_DATE); // 2018-05-27 01:21:15
                    Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).parse(datestr);
                    long diff = accessTime.getTime() - d.getTime();
                    // https://stackoverflow.com/questions/5351483/calculate-date-time-difference-in-java
                    long diff_day = TimeUnit.MILLISECONDS.toDays(diff);
                    // int diff_mon = (int);

                    if (diff_day <= 365)
                    {
                        // TODO
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else if (dataGettingMode == DATA_GETTING_MODE_MONTH)
        {
            // get labels
            f = new SimpleDateFormat("MM/dd", Locale.KOREA);
            for (int i = 0; i < 30; ++i)
            {
                temp.add(f.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            Collections.reverse(temp);

            // get entries; TODO: average of each day for 30 days
        }
        else if (dataGettingMode == DATA_GETTING_MODE_WEEK)
        {
            // get labels
            f = new SimpleDateFormat("MM/dd", Locale.KOREA);
            for (int i = 0; i < 7; ++i)
            {
                temp.add(f.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            Collections.reverse(temp);

            // get entries; TODO: average of each day for 7 days
        }
        else if (dataGettingMode == DATA_GETTING_MODE_DAY)
        {

        }
        else if (dataGettingMode == DATA_GETTING_MODE_HOUR)
        {

        }
        else
        {

        }

        switch (dataGettingMode)
        {
            case DATA_GETTING_MODE_YEAR:


                break;

            case DATA_GETTING_MODE_MONTH:


            case DATA_GETTING_MODE_WEEK:

                break;

            case DATA_GETTING_MODE_DAY:
                // get labels
                f = new SimpleDateFormat("HH:mm", Locale.KOREA);
                for (int i = 0; i < 7; ++i)
                {
                    temp.add(f.format(cal.getTime()));
                    cal.add(Calendar.HOUR_OF_DAY, -1);
                }
                Collections.reverse(temp);

                // get entries; TODO: average of each hour for 24 hours
                break;

            case DATA_GETTING_MODE_HOUR:
                // get labels
                f = new SimpleDateFormat("HH:mm", Locale.KOREA);
                for (int i = 0; i < 60; ++i)
                {
                    temp.add(f.format(cal.getTime()));
                    cal.add(Calendar.MINUTE, -1);
                }
                Collections.reverse(temp);

                // get entries; TODO: average of each minute for 60 minutes
                break;
        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0; i<jsonArray.length(); i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String date = item.getString(TAG_DATE);
                String breath = item.getString(TAG_BREATH);


                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_DATE, date);
                hashMap.put(TAG_BREATH, breath);


                mArrayList.add(hashMap);
            }

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }



    private void initGraph()
    {
        LineChart lineChart = (LineChart) v.findViewById(R.id.chart);
        LineDataSet lineDataSet = new LineDataSet(entries, "심박수");
        lineDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        //lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(false); //선아래로 색상표시
        lineDataSet.setDrawValues(true);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineChart.setDescription("");

        LineData lineData = new LineData(labels, lineDataSet);

        lineChart.setData(lineData); // set the data and list of labels into chart

        //MarkerView mv = new CustomMarkerView(this,R.layout.content_marker_view);

        //lineChart.setMarkerView(mv);

        lineChart.setDrawMarkerViews(true);



        YAxis y = lineChart.getAxisLeft();
        y.setTextColor(Color.BLACK);
        y.setAxisMaxValue(120);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);



        XAxis x = lineChart.getXAxis();
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setTextColor(Color.BLACK);



        Legend legend = lineChart.getLegend();

        legend.setTextColor(Color.BLACK);

        lineChart.animateXY(2000, 2000); //애니메이션 기능 활성화
        lineChart.invalidate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        String id = Main2Activity.id;
        v = inflater.inflate(R.layout.fragment_breath, container, false);
        mArrayList = new ArrayList<>();
        GetData task = new GetData();
        task.execute("http://show8258.ipdisk.co.kr:8000/breathlist.php?ID=" + id); // TODO: error here



        Spinner spinner = (Spinner)v.findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spinner s = (Spinner)parent;
                int selected = (int) s.getSelectedItemId();
                getLabelsAndEntries(selected);
                initGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                getLabelsAndEntries(DATA_GETTING_MODE_MONTH);
            }
        });

        // default
        getLabelsAndEntries(DATA_GETTING_MODE_MONTH);
        initGraph();


        return v;
    }
}
