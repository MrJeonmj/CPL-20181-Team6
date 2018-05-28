package com.example.user.jolp_v0;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.hypeapp.materialtimelineview.MaterialTimelineView;

public class TimelineActivity extends AppCompatActivity
{

    private static final int[] COLOR_BY_STEP = {0xff000000, 0xfffbe5d6, 0xfff8cbad, 0xfff4b183, 0xffff6d6d, 0xffff3f3f};
    // [0] will be used as bgColor for line object

    private static final int COLOR_WHITE = 0xffffffff;
    private static final int COLOR_BLACK = 0xff000000;
    private static final int COLOR_RED = 0xffff0000;

    public class CardData
    {
        private int hour, minute, second, steplevel;

        public CardData(int hour, int minute, int second, int steplevel)
        {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.steplevel = steplevel;
        }

        public int getHour()
        {
            return hour;
        }

        public int getMinute()
        {
            return minute;
        }

        public int getSecond()
        {
            return second;
        }

        public int getSteplevel()
        {
            return steplevel;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get intent
        int[] ymd = new int[3];
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if (extras != null)
            {
                ymd[0] = extras.getInt("year");
                ymd[1] = extras.getInt("month");
                ymd[2] = extras.getInt("day");
            }
        }
        else
        {
            ymd[0] = (int) savedInstanceState.getSerializable("year");
            ymd[1] = (int) savedInstanceState.getSerializable("month");
            ymd[2] = (int) savedInstanceState.getSerializable("day");
        }

        String xtitle = String.format("%d년 %d월 %d일", ymd[0], ymd[1], ymd[2]);
        // Toast.makeText(this.getApplicationContext(), xtitle, Toast.LENGTH_SHORT).show();

        Log.println(Log.DEBUG, "TimelineActivity", "TimelineActivity");
        try
        {
            android.support.v7.app.ActionBar sab = getSupportActionBar();
            Log.println(Log.DEBUG, "TimelineActivity", sab.toString());
            sab.setTitle(xtitle);
        }
        catch (Exception e)
        {
            String error = Log.getStackTraceString(e);
            Log.println(Log.ERROR, "TimelineActivity", error);

        }

        // programmatically added items
        // get data to make cards from them

        ArrayList<CardData> alCardData = new ArrayList<>();

        Log.d("TimelineActivity", "Temp.date_Data.size() : " + Temp.date_Data.size() );
        Log.d("TimelineActivity", "Temp.step_Data.size() : " + Temp.step_Data.size() );

        for (int i = 0; i < Temp.date_Data.size(); ++i)
        {
            Date date = Temp.date_Data.get(i);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Log.d("TimeLineActivity", String.format("i: %d", i));
            Log.d("TimeLineActivity",
                    String.format("cal: %04d-%02d-%02d",
                            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
            boolean sameDate
                    = (cal.get(Calendar.YEAR) == ymd[0]) &&
                    (cal.get(Calendar.MONTH)/*zero-based index*/ + 1 == ymd[1] /*one-based index*/) &&
                    (cal.get(Calendar.DAY_OF_MONTH) == ymd[2]);
            if (!sameDate)
                continue;

            long stepTime = Temp.step_Data.get(i);
            int stepLevel = 0;

            if (stepTime == 0)
                continue;
            else if (0 < stepTime && stepTime < 20)
                stepLevel = 1;
            else if (20 <= stepTime && stepTime < 40)
                stepLevel = 2;
            else if (40 <= stepTime && stepTime < 60)
                stepLevel = 3;
            else if (60 <= stepTime && stepTime < 80)
                stepLevel = 4;
            else if (80 <= stepTime)
                stepLevel = 5;

            CardData cd = new CardData
                    (cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND), stepLevel);
            alCardData.add(cd);
            Log.d("TimelineActivity",
                    String.format("%02d:%02d:%02d level %d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),
                    cal.get(Calendar.SECOND), stepLevel));
        }

        /*
        ArrayList<MaterialTimelineView> mtvs = new ArrayList<>();
        mtvs.add(makeCard(1, "Programmatically", COLOR_BLACK,
                MaterialTimelineView.Companion.getPOSITION_FIRST()));
        mtvs.add(makeLine("Added", COLOR_WHITE,
                MaterialTimelineView.Companion.getPOSITION_MIDDLE()));
        mtvs.add(makeCard(5, "Items!", COLOR_BLACK,
                MaterialTimelineView.Companion.getPOSITION_MIDDLE()));
        mtvs.add(makeLine("VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! VERY LONG TEXT! ",
                COLOR_WHITE,
                MaterialTimelineView.Companion.getPOSITION_LAST()));


        for (MaterialTimelineView mtv : mtvs)
            addObj(mtv);
        */

        for (int i = 0; i < alCardData.size(); ++i)
        {
            // add card
            int pos = pos = MaterialTimelineView.Companion.getPOSITION_MIDDLE();
            if (i == 0)
                pos = MaterialTimelineView.Companion.getPOSITION_FIRST();
            else if (i == alCardData.size() - 1)
                pos = MaterialTimelineView.Companion.getPOSITION_LAST();

            addObj(makeCard(alCardData.get(i).getSteplevel() + 1,
                    String.format("%02d:%02d:%02d level %d",
                            alCardData.get(i).getHour(), alCardData.get(i).getMinute(), alCardData.get(i).getSecond(),
                            alCardData.get(i).getSteplevel() + 1),
                    COLOR_BLACK, pos));

            // add line
            if (i != alCardData.size() - 1)
                addObj(makeLine("", COLOR_WHITE, MaterialTimelineView.Companion.getPOSITION_MIDDLE()));
        }



        // programmatically changing like this
        // MaterialTimelineView material_timeline_view2 = (MaterialTimelineView)findViewById(R.id.material_timeline_view2);
        // material_timeline_view2.setBackgroundColor(0xffc0ffee);

    }

    public MaterialTimelineView makeObj(@ColorInt int bgColor, String text, @ColorInt int textColor, int type, int position)
    {
        try
        {
            MaterialTimelineView mtv = new MaterialTimelineView(this);

            // size
            float timeline_card_height = getResources().getDimension(R.dimen.timeline_card_height);
            // float mp = getResources().getDimension();
            MaterialTimelineView.LayoutParams mtv_para = new MaterialTimelineView.LayoutParams
                    (MaterialTimelineView.LayoutParams.MATCH_PARENT, (int) timeline_card_height);
            mtv.setLayoutParams(mtv_para);

            // properties
            mtv.setTimelineType(type);
            mtv.setPosition(position);
            mtv.setBackgroundColor(bgColor);


            // text
            TextView tv = new TextView(this);
            tv.setText(text);
            tv.setTextColor(textColor);
            ViewGroup.LayoutParams paramsExample = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mtv.addView(tv);


            return mtv;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public MaterialTimelineView makeCard(int step, String text, @ColorInt int textColor, int position)
            throws IllegalArgumentException
    {
        int max = COLOR_BY_STEP.length - 1;
        if (step < 1 || step > max)
        {
            throw new IllegalArgumentException(String.format("step must be in [1, %d]; %d received", max, step));
        }

        return makeObj(COLOR_BY_STEP[step], text, textColor, MaterialTimelineView.Companion.getTIMELINE_TYPE_ITEM(), position);
    }

    public MaterialTimelineView makeLine(String text, @ColorInt int textColor, int position)
    {
        return makeObj(COLOR_BY_STEP[0], text, textColor, MaterialTimelineView.Companion.getTIMELINE_TYPE_LINE(), position);
    }

    public void addObj(MaterialTimelineView mtv)
    {
        LinearLayout timeline_linear = (LinearLayout) findViewById(R.id.timeline_linear);
        timeline_linear.addView(mtv);
    }

}
