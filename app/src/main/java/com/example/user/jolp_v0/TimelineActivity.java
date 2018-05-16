package com.example.user.jolp_v0;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pl.hypeapp.materialtimelineview.MaterialTimelineView;

public class TimelineActivity extends AppCompatActivity {

    private static final int TIMELINE_TYPE_LINE = 0;
    private static final int TIMELINE_TYPE_ITEM = 1;
    private static final int POSITION_FIRST = 0;
    private static final int POSITION_MIDDLE = 1;
    private static final int POSITION_LAST = 2;
    // TODO: submit an issue being constants in class private
    // https://github.com/hypeapps/MaterialTimelineView/issues/6

    private static final int[] COLOR_BY_STEP = { 0xff000000, 0xfffbe5d6, 0xfff8cbad, 0xfff4b183, 0xffff6d6d, 0xffff3f3f };
    // [0] is used as bgColor for line object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // programmatically added items
        ArrayList<MaterialTimelineView> mtvs = new ArrayList<>();

        // set color in form of 0xAARRGGBB
        mtvs.add(makeCard(COLOR_BY_STEP[1], "Programmatically", 0xff000000, TIMELINE_TYPE_ITEM, POSITION_FIRST));
        mtvs.add(makeCard(COLOR_BY_STEP[0], "Added", 0xffffffff, TIMELINE_TYPE_LINE, POSITION_MIDDLE));
        mtvs.add(makeCard(COLOR_BY_STEP[5], "Items!", 0xff000000, TIMELINE_TYPE_ITEM, POSITION_LAST));


        for (MaterialTimelineView mtv: mtvs)
            addCard(mtv);

        // programmatically changing like this
        // MaterialTimelineView material_timeline_view2 = (MaterialTimelineView)findViewById(R.id.material_timeline_view2);
        // material_timeline_view2.setBackgroundColor(0xffc0ffee);

    }

    // TODO: can these be private?
    public MaterialTimelineView makeCard(@ColorInt int bgColor, String text, @ColorInt int textColor, int type, int position)
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

    public void addCard(MaterialTimelineView mtv)
    {
        LinearLayout timeline_linear = (LinearLayout)findViewById(R.id.timeline_linear);
        timeline_linear.addView(mtv);
    }

}
