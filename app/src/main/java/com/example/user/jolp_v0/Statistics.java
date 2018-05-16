package com.example.user.jolp_v0;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Statistics extends AppCompatActivity {

    private ArrayList<String> labels;
    private ArrayList<Entry> entries;

    // constants
    private final int DATA_GETTING_MODE_YEAR = 0;
    private final int DATA_GETTING_MODE_MONTH = 1;
    private final int DATA_GETTING_MODE_WEEK = 2;
    private final int DATA_GETTING_MODE_DAY = 3;
    private final int DATA_GETTING_MODE_HOUR = 4;

    private ArrayList<String> getLabels(int dataGettingMode)
    {
        ArrayList<String> r = new ArrayList<>();

        // TODO: get actual data
        switch (dataGettingMode)
        {
            case DATA_GETTING_MODE_YEAR:
                for (int i = 1; i <= 12; ++i)
                    r.add(String.valueOf(i));
                break;

            case DATA_GETTING_MODE_MONTH:
                for (int i = 1; i <= 30; ++i)
                    r.add("4/" + String.valueOf(i));
                break;

            case DATA_GETTING_MODE_WEEK:
                for (int i = 19; i <= 25; ++i)
                    r.add("4/" + String.valueOf(i));
                break;

            case DATA_GETTING_MODE_DAY:
                for (int i = 0; i <= 23; ++i)
                    r.add(String.valueOf(i) + ":00");
                break;

            case DATA_GETTING_MODE_HOUR:
                for (int i = 0; i <= 59; ++i)
                    r.add("12:" + String.valueOf(i));
                break;
        }


        return r;
    }

    private ArrayList<Entry> getEntries(int dataGettingMode)
    {
        ArrayList<Entry> r = new ArrayList<>();

        // TODO: get actual data
        int[] data = {
                73, 93, 94, 80, 78, 83, 79, 71, 83, 90,
                94, 72, 83, 87, 89, 84, 70, 76, 92, 75,
                85, 88, 94, 99, 82, 70, 98, 73, 91, 71,
                81, 89, 87, 90, 79, 85, 77, 96, 93, 86,
                74, 75, 92, 97, 97, 80, 82, 99, 94, 91,
                91, 90, 70, 84, 93, 70, 88, 91, 71, 75
        }; // randomly generated example data

        switch (dataGettingMode)
        {
            case DATA_GETTING_MODE_YEAR:
                for (int i = 0; i < 12; ++i)
                    r.add(new Entry(data[i], i));
                break;

            case DATA_GETTING_MODE_MONTH:
                for (int i = 0; i < 30; ++i)
                    r.add(new Entry(data[i], i));
                break;

            case DATA_GETTING_MODE_WEEK:
                for (int i = 0; i < 7; ++i)
                    r.add(new Entry(data[i], i));
                break;

            case DATA_GETTING_MODE_DAY:
                for (int i = 0; i < 24; ++i)
                    r.add(new Entry(data[i], i));
                break;

            case DATA_GETTING_MODE_HOUR:
                for (int i = 0; i < 60; ++i)
                    r.add(new Entry(data[i], i));
                break;
        }

        return r;
    }

    private void initGraph()
    {
        LineChart lineChart = (LineChart) findViewById(R.id.chart);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        /*
        Button buttonY = (Button)findViewById(R.id.buttonY);
        buttonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                labels = getLabels(DATA_GETTING_MODE_YEAR);
                entries = getEntries(DATA_GETTING_MODE_YEAR);
                initGraph();
            }
        });

        Button buttonM = (Button)findViewById(R.id.buttonM);
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                labels = getLabels(DATA_GETTING_MODE_MONTH);
                entries = getEntries(DATA_GETTING_MODE_MONTH);
                initGraph();
            }
        });

        Button buttonW = (Button)findViewById(R.id.buttonW);
        buttonW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                labels = getLabels(DATA_GETTING_MODE_WEEK);
                entries = getEntries(DATA_GETTING_MODE_WEEK);
                initGraph();
            }
        });

        Button buttonD = (Button)findViewById(R.id.buttonD);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                labels = getLabels(DATA_GETTING_MODE_DAY);
                entries = getEntries(DATA_GETTING_MODE_DAY);
                initGraph();
            }
        });

        Button buttonH = (Button)findViewById(R.id.buttonH);
        buttonH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                labels = getLabels(DATA_GETTING_MODE_HOUR);
                entries = getEntries(DATA_GETTING_MODE_HOUR);
                initGraph();
            }
        });
        */

        final Spinner spinner = (Spinner)findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               Spinner s = (Spinner)parent;
               int selected = (int) s.getSelectedItemId();
               labels = getLabels(selected);
               entries = getEntries(selected);
               initGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                labels = getLabels(DATA_GETTING_MODE_MONTH);
                entries = getEntries(DATA_GETTING_MODE_MONTH);
            }
        });

        // default
        labels = getLabels(DATA_GETTING_MODE_MONTH);
        entries = getEntries(DATA_GETTING_MODE_MONTH);
        initGraph();
    }
}
