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

import pl.hypeapp.materialtimelineview.MaterialTimelineView;

public class TimelineActivity extends AppCompatActivity {

    private static final int[] COLOR_BY_STEP = { 0xff000000, 0xfffbe5d6, 0xfff8cbad, 0xfff4b183, 0xffff6d6d, 0xffff3f3f };
    // [0] will be used as bgColor for line object

    private static final int COLOR_WHITE = 0xffffffff;
    private static final int COLOR_BLACK = 0xff000000;
    private static final int COLOR_RED = 0xffff0000;
    private final MaterialTimelineView RED_CARD =
            makeObj(COLOR_RED, "Something went wrong!", COLOR_WHITE,
                    MaterialTimelineView.Companion.getTIMELINE_TYPE_LINE(),
                    MaterialTimelineView.Companion.getPOSITION_FIRST());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // programmatically added items
        try
        {
            ArrayList<MaterialTimelineView> mtvs = new ArrayList<>();

            mtvs.add(makeCard(1, "Programmatically", COLOR_BLACK,
                    MaterialTimelineView.Companion.getPOSITION_FIRST()));
            mtvs.add(makeLine("Added", COLOR_WHITE,
                    MaterialTimelineView.Companion.getPOSITION_MIDDLE()));
            mtvs.add(makeCard(5, "Items!", COLOR_BLACK,
                    MaterialTimelineView.Companion.getPOSITION_LAST()));


            for (MaterialTimelineView mtv: mtvs)
                addObj(mtv);
        }
        catch (Exception e)
        {
            addObj(RED_CARD);
            addObj(makeLine(Log.getStackTraceString(e), COLOR_WHITE,
                    MaterialTimelineView.Companion.getPOSITION_MIDDLE()));

        }

        // programmatically changing like this
        // MaterialTimelineView material_timeline_view2 = (MaterialTimelineView)findViewById(R.id.material_timeline_view2);
        // material_timeline_view2.setBackgroundColor(0xffc0ffee);

    }

    // TODO: can these be private?
    public MaterialTimelineView makeObj(@ColorInt int bgColor, String text, @ColorInt int textColor, int type, int position)
    {
        try
        {
            MaterialTimelineView mtv = new MaterialTimelineView(this);

            // size
            float timeline_card_height = getResources().getDimension(R.dimen.timeline_card_height);
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
                    (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
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
            throw new IllegalArgumentException(String.format("step must be in [1, %d]", max));
        }

        return makeObj(COLOR_BY_STEP[step], text, textColor, MaterialTimelineView.Companion.getTIMELINE_TYPE_ITEM(), position);
    }

    public MaterialTimelineView makeLine(String text, @ColorInt int textColor, int position)
    {
        return makeObj(COLOR_BY_STEP[0], text, textColor, MaterialTimelineView.Companion.getTIMELINE_TYPE_LINE(), position);
    }

    public void addObj(MaterialTimelineView mtv)
    {
        LinearLayout timeline_linear = (LinearLayout)findViewById(R.id.timeline_linear);
        timeline_linear.addView(mtv);
    }

}
