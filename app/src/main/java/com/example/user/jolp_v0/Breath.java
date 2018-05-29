package com.example.user.jolp_v0;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import lecho.lib.hellocharts.model.Line;

/**
 * Created by kch on 2017. 9. 22..
 */

public class Breath extends Fragment
{
    // constants
    private static final int DATA_GETTING_MODE_YEAR = 0;
    private static final int DATA_GETTING_MODE_MONTH = 1;
    private static final int DATA_GETTING_MODE_WEEK = 2;
    private static final int DATA_GETTING_MODE_DAY = 3;
    private static final int DATA_GETTING_MODE_HOUR = 4;
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "ID";
    private static final String TAG_DATE = "DATE";
    private static final String TAG_TEMP = "TEMP";
    // private final int DATA_GETTING_RECENT = 5;
    // private final int DATA_GETTING_RECENT_LENGTH = 50;
    private static String TAG = "phptest";
    private View v;
    private ArrayList<String> labels;
    private ArrayList<Entry> entries;
    // private ArrayList<String> tempData = new ArrayList<>();
    private ArrayList<HashMap<String, String>> mArrayList;
    private String mJsonString;

    private void makeLabelsAndEntries(Date now, int dataGettingMode)
    {
        // constants
        final String[] sdfPatterns = {"yyyy/MM", "yy/MM/dd", "MM/dd", "dd/HH:00", "dd/HH:mm"};
        final int[] lengths = {12, 30, 7, 24, 60};
        final int[] intervals
                = {Calendar.MONTH, Calendar.DAY_OF_YEAR, Calendar.DAY_OF_YEAR, Calendar.HOUR_OF_DAY, Calendar.MINUTE};

        // initializations
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(now);
        calNow.setLenient(true);

        Calendar calTemp = Calendar.getInstance();
        calTemp.setTime(now);
        calTemp.setLenient(true);

        // get labels
        SimpleDateFormat f = new SimpleDateFormat(sdfPatterns[dataGettingMode], Locale.KOREA);
        for (int i = 0; i < lengths[dataGettingMode]; ++i)
        {
            labels.add(f.format(calTemp.getTime()));
            calTemp.add(intervals[dataGettingMode], -1);
        }
        Collections.reverse(labels);

        // get entries; average of e.g. each month for 12 months
        double minValue = Double.MAX_VALUE;
        double avgValue = 0;
        int totalCount = 0;
        double maxValue = Double.MIN_VALUE;

        double[] sums = new double[lengths[dataGettingMode]];
        int[] count = new int[lengths[dataGettingMode]];
        for (HashMap<String, String> h : mArrayList)
        {
            try
            {
                String datestr = h.get(TAG_DATE); // like 2018-05-27 01:21:15
                Date then = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).parse(datestr);
                Calendar calThen = Calendar.getInstance();
                calThen.setTime(then);
                calThen.setLenient(true);

                int diff;
                long diffTemp = now.getTime() - then.getTime(); // in milliseconds

                switch (dataGettingMode)
                {
                    case DATA_GETTING_MODE_YEAR: // `diff` is difference in months
                        diff = (calNow.get(Calendar.YEAR) - calThen.get(Calendar.YEAR)) * 12
                                + (calNow.get(Calendar.MONTH) - calThen.get(Calendar.MONTH));
                        break;

                    case DATA_GETTING_MODE_MONTH:
                    case DATA_GETTING_MODE_WEEK: // in both cases,`diff` is difference in days
                        diff = (int) ((diffTemp / (1000 * 60 * 60 * 24)));
                        break;

                    case DATA_GETTING_MODE_DAY: // `diff` is difference in hours
                        diff = (int) ((diffTemp / (1000 * 60 * 60)));

                        break;

                    case DATA_GETTING_MODE_HOUR: // `diff` is difference in minutes
                        diff = (int) ((diffTemp / (1000 * 60)));
                        break;

                    default:
                        throw new IllegalArgumentException("Wrong `dataGettingMode` specified");
                }

                if (0 <= diff && diff < lengths[dataGettingMode])
                {
                    double d = Double.parseDouble(h.get(TAG_TEMP));

                    // calculate sum, avg
                    sums[diff] += d;
                    avgValue += d; // now acting as "total sum"; later this will be divided into `totalCount`
                    ++count[diff];

                    // determine minimum
                    if (d < minValue)
                        minValue = d;

                    // determine maximum
                    if (d > maxValue)
                        maxValue = d;

                    //Log.d("Breath", String.format(Locale.KOREA, "diff: %d, value = %f, count = %d, current avg = %f",
                    //        diff, d, count[diff], sums[diff] / count[diff]));
                }


            }
            catch (Exception e)
            {
                Log.println(Log.ERROR, "Breath", e.getClass().toString());
                e.printStackTrace();
            }
        }

        for (int i = 0; i < lengths[dataGettingMode]; ++i)
        {
            entries.add(new Entry((float) (sums[i] / count[i]), lengths[dataGettingMode] - 1 - i));
            totalCount += count[i];
        }

        avgValue /= totalCount;

        // show min, avg, max

        TextView minV = v.findViewById(R.id.text_minbeat);
        TextView avgV = v.findViewById(R.id.text_avgbeat);
        TextView maxV = v.findViewById(R.id.text_maxbeat);

        minV.setText(minValue == Double.MAX_VALUE? "-":String.format("%.1f", minValue));
        avgV.setText(Double.isNaN(avgValue)? "-": String.format("%.1f", avgValue));
        maxV.setText(maxValue == Double.MIN_VALUE? "-":String.format("%.1f", maxValue));
    }

    private void getLabelsAndEntries(int dataGettingMode)
    {
        labels = new ArrayList<>();
        entries = new ArrayList<>();

        Date now = new Date();
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(now);
        calNow.setLenient(true);

        makeLabelsAndEntries(now, dataGettingMode);
    }

    private void showResult()
    {
        try
        {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++)
            {

                JSONObject item = jsonArray.getJSONObject(i);

                String id = item.getString(TAG_ID);
                String date = item.getString(TAG_DATE);
                String temp = item.getString(TAG_TEMP);


                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_DATE, date);
                hashMap.put(TAG_TEMP, temp);


                mArrayList.add(hashMap);
            }

            getLabelsAndEntries(DATA_GETTING_MODE_YEAR);
            initGraph();

        }
        catch (JSONException e)
        {

            Log.d(TAG, "showResult : ", e);
        }

    }

    private void initGraph()
    {
        LineChart lineChart = v.findViewById(R.id.chart);
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
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(0xFF000000);
        // lineChart.setMaxVisibleValueCount(maxVisibleValueCount);

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_breath, container, false);
        mArrayList = new ArrayList<>();
        GetData task = new GetData();
        String id = Main2Activity.id;
        task.execute("http://show8258.ipdisk.co.kr:8000/templist.php?ID=" + id);

        Spinner spinner = v.findViewById(R.id.spinner2);
        String values[] = getResources().getStringArray(R.array.graph_scope);
        /*ArrayAdapter<String> adapter= new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Spinner s = (Spinner) parent;
                int selected = (int) s.getSelectedItemId();
                getLabelsAndEntries(selected);
                initGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                getLabelsAndEntries(DATA_GETTING_MODE_YEAR);
            }
        });

        // default
        getLabelsAndEntries(DATA_GETTING_MODE_YEAR);
        initGraph();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();


    }

    private class GetData extends AsyncTask<String, Void, String>
    {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", "Loading...", true, true);
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);


            if (result == null)
            {

                //mTextViewResult.setText(errorString);
            }
            else
            {

                mJsonString = result;
                showResult();

            }
        }


        @Override
        protected String doInBackground(String... params)
        {

            String serverURL = params[0];


            try
            {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK)
                {
                    inputStream = httpURLConnection.getInputStream();
                }
                else
                {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null)
                {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString().trim();


            }
            catch (Exception e)
            {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }

        /*protected void onDestroyView()
        {
            Log.d("Breath", "Breath.onDestroy()");
            entries.clear();
            labels.clear();
        }*/
    }
}
