package com.example.user.jolp_v0;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class Temp extends Fragment
{
    private static final String TAG_JSON = "webnautes";
    private static final String TAG_ID = "ID";
    private static final String TAG_BREATH = "BREATH";
    private static final String TAG_DATE = "DATE";
    static ArrayList<Date> date_Data = new ArrayList<>();
    static ArrayList<Long> step_Data = new ArrayList<>();
    //server
    private static String TAG = "phptest";
    View v;
    String time, kcal, menu;
    Cursor cursor;
    MaterialCalendarView materialCalendarView;
    ArrayList<DayInfo> dayInfoArrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;
    String id;

    //ListView mlistView;
    //


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        //inflate메소드는 XML데이터를 가져와서 실제 View객체로 만드는 작업을 합니다.
        v = inflater.inflate(R.layout.fragment_temp, container, false);

        //SERVER
        id = Main2Activity.id;
        //mlistView = (ListView) findViewById(R.id.listView_main_list);
        mArrayList = new ArrayList<>();

        GetData task = new GetData();
        task.execute("http://show8258.ipdisk.co.kr:8000/breathlist.php?ID=" + id);
        //

        materialCalendarView = (MaterialCalendarView) v.findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();


        //String[] result = {"2017,03,18","2017,04,18","2017,05,18","2017,06,18"};

        //new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener()
        {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected)
            {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

                String shot_Day = Year + "," + Month + "," + Day;

                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();

                Intent intent1 = new Intent(getActivity(), TimelineActivity.class);
                intent1.putExtra("year", Year);
                intent1.putExtra("month", Month);
                intent1.putExtra("day", Day);

                // Toast.makeText(getActivity().getApplicationContext(), shot_Day , Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });

        Button buttonjoin = (Button) v.findViewById(R.id.button_graph);
        buttonjoin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Intent a = new Intent(getActivity(), Graph_Statistics.class);
                startActivity(a);


            }
        });
        return v;
    }

    @Override
    public void onResume()
    {
//        addDayInfoArrayList(0,Color.RED,2018,5,17);
//        addDayInfoArrayList(0,Color.GRAY,2018,5,11);
//        addDayInfoArrayList(0,Color.BLUE,2018,5,10);
//        for(DayInfo k : dayInfoArrayList){
//            ArrayList<CalendarDay> dates = new ArrayList<>();
//            dates.add(k.getCal());
//            materialCalendarView.addDecorator(new EventDecorator(k.getColor(), dates));
//        }
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        long lastRefresh = sp.getLong("refresh", 0);
        long now = System.currentTimeMillis();
        if (lastRefresh == 0 || now - lastRefresh > 5000)
        {
            GetData task = new GetData();
            task.execute("http://show8258.ipdisk.co.kr:8000/breathlist.php?ID=" + id);
            sp.edit().putLong("refresh", now).commit();
        }
        super.onResume();
    }
//
//    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
//
//        String[] Time_Result;
//
//        ApiSimulator(String[] Time_Result){
//            this.Time_Result = Time_Result;
//        }
//
//        @Override
//        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            //CalendarDay kk = CalendarDay.from(2018,6,13);
//            //ArrayList<CalendarDay> dates = new ArrayList<>();
//            //dates.add(kk);
//            addDayInfoArrayList(0,0,2018,5,17);
//
//
//
//            return dayInfoArrayList;
//        }
//
//        @Override
//        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
//            super.onPostExecute(calendarDays);
//
//            if (isFinishing()) {
//                return;
//            }
//            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));
//            //materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays,MainActivity.this));
//        }
//
//
//    }

    public void addDayInfoArrayList(int importance, int color, int year, int month, int day)
    {
        DayInfo k = new DayInfo(importance, color, year, month, day);
        dayInfoArrayList.add(k);
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
                String breath = item.getString(TAG_BREATH);
                String date = item.getString(TAG_DATE);


                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_ID, id);
                hashMap.put(TAG_BREATH, breath);
                hashMap.put(TAG_DATE, date);


                mArrayList.add(hashMap);
            }
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            int tag = -1;
            //Date temp = transFormat.parse("201702011535");
            Date temp = new Date();


            for (int i = 0; i < mArrayList.size(); i++)
            {
                if (Double.parseDouble(mArrayList.get(i).get(TAG_BREATH)) == 0.0 && tag == 1)
                {

                }
                else if (Double.parseDouble(mArrayList.get(i).get(TAG_BREATH)) == 0.0)
                {
                    temp = transFormat.parse(mArrayList.get(i).get(TAG_DATE));
                    date_Data.add(temp);
                    tag = 1;
                }
                else if (Double.parseDouble(mArrayList.get(i).get(TAG_BREATH)) != 0.0 && tag == 1)
                {
                    long second = (transFormat.parse(mArrayList.get(i).get(TAG_DATE)).getTime() - temp.getTime()) / 1000;
                    step_Data.add(second);
                    tag = -1;
                }
                if ((mArrayList.size() - 1) == i && tag == 1)
                {
                    long second = (transFormat.parse(mArrayList.get(i).get(TAG_DATE)).getTime() - temp.getTime()) / 1000;
                    step_Data.add(second);
                    tag = -1;
                }

            }
            SimpleDateFormat year = new SimpleDateFormat("yyyy");
            SimpleDateFormat month = new SimpleDateFormat("MM");
            SimpleDateFormat day = new SimpleDateFormat("dd");
            Date temp1 = new Date();
            if (date_Data.size() > 1)
            {
                temp1 = date_Data.get(0);
            }
            //String kk = format.format(temp1);
            int sum = 0;
            for (int i = 0; i < date_Data.size(); i++)
            {
                if (year.format(temp1).equals(year.format(date_Data.get(i))) && month.format(temp1).equals(month.format(date_Data.get(i))) && day.format(temp1).equals(day.format(date_Data.get(i))))
                {
                    sum += step_Data.get(i);
                    temp1 = date_Data.get(i);
                }
                else
                {
                    if (0 < sum && sum < 20)
                    {
                        addDayInfoArrayList(0, Color.GREEN, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
                    }
                    else if (20 <= sum && sum < 40)
                    {
                        addDayInfoArrayList(0, Color.BLUE, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
                    }
                    else if (40 <= sum && sum < 60)
                    {
                        addDayInfoArrayList(0, Color.YELLOW, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
                    }
                    else if (60 <= sum && sum < 80)
                    {
                        addDayInfoArrayList(0, Color.BLACK, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
                    }
                    else if (80 <= sum)
                    {
                        addDayInfoArrayList(0, Color.RED, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
                    }
                    sum = (int) (long) step_Data.get(i);
                    temp1 = date_Data.get(i);
                }


            }
            if (0 < sum && sum < 20)
            {
                addDayInfoArrayList(0, Color.GREEN, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
            }
            else if (20 <= sum && sum < 40)
            {
                addDayInfoArrayList(0, Color.BLUE, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
            }
            else if (40 <= sum && sum < 60)
            {
                addDayInfoArrayList(0, Color.YELLOW, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
            }
            else if (60 <= sum && sum < 80)
            {
                addDayInfoArrayList(0, Color.BLACK, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
            }
            else if (80 <= sum)
            {
                addDayInfoArrayList(0, Color.RED, Integer.parseInt(year.format(temp1)), Integer.parseInt(month.format(temp1)), Integer.parseInt(day.format(temp1)));
            }

            //addDayInfoArrayList(0,Color.RED,2018,5,17);
            //addDayInfoArrayList(0,Color.GRAY,2018,5,11);
            //addDayInfoArrayList(0,Color.BLUE,2018,5,10);
            for (DayInfo k : dayInfoArrayList)
            {
                ArrayList<CalendarDay> dates = new ArrayList<>();
                dates.add(k.getCal());
                materialCalendarView.addDecorator(new EventDecorator(k.getColor(), dates));
            }

/*
            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), mArrayList, R.layout.item_list,
                    new String[]{TAG_ID,TAG_BREATH, TAG_DATE},
                    new int[]{R.id.id,  R.id.breath, R.id.date}
            );

            mlistView.setAdapter(adapter);
            */


            // Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();

        } catch (JSONException e)
        {

            Log.d(TAG, "showResult : ", e);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

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
                    "Please Wait", null, true, true);
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


            } catch (Exception e)
            {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

}