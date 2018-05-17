package com.example.user.jolp_v0;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;


public class Calendar_Statistics extends AppCompatActivity {

    String time,kcal,menu;
    Cursor cursor;
    MaterialCalendarView materialCalendarView;
    ArrayList<DayInfo> dayInfoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_statistics);

        materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2017, 0, 1)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(2030, 11, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();


        //String[] result = {"2017,03,18","2017,04,18","2017,05,18","2017,06,18"};

        //new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int Year = date.getYear();
                int Month = date.getMonth() + 1;
                int Day = date.getDay();

                Log.i("Year test", Year + "");
                Log.i("Month test", Month + "");
                Log.i("Day test", Day + "");

                String shot_Day = Year + "," + Month + "," + Day;

                Log.i("shot_Day test", shot_Day + "");
                materialCalendarView.clearSelection();

                Toast.makeText(getApplicationContext(), shot_Day , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        addDayInfoArrayList(0,Color.RED,2018,5,17);
        addDayInfoArrayList(0,Color.GRAY,2018,5,11);
        addDayInfoArrayList(0,Color.BLUE,2018,5,10);
        for(DayInfo k : dayInfoArrayList){
            ArrayList<CalendarDay> dates = new ArrayList<>();
            dates.add(k.getCal());
            materialCalendarView.addDecorator(new EventDecorator(k.getColor(), dates));
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

    public void addDayInfoArrayList(int importance, int color, int year, int month, int day) {
        DayInfo k = new DayInfo(importance,color,year,month,day);
        dayInfoArrayList.add(k);
    }
}