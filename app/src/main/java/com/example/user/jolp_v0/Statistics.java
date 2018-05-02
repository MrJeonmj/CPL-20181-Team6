package com.example.user.jolp_v0;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.components.Legend;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        //ArrayList<Entry> lines = new ArrayList<Entry>();
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("4/20");
        labels.add("4/21");
        labels.add("4/22");
        labels.add("4/23");
        labels.add("4/24");
        labels.add("4/25");

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(71, 0));
        entries.add(new Entry(78, 1));
        entries.add(new Entry(90, 2));
        entries.add(new Entry(80, 3));
        entries.add(new Entry(88, 4));
        entries.add(new Entry(79, 5));

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
}
