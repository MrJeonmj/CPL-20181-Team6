package com.example.user.jolp_v0;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
//import android.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class Graph_Statistics extends AppCompatActivity {

    //toolbar
//    private Toolbar toolbar;
//    private String[] category=null;
    static String choose_year = "2018";
    static int choose_month = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph__statistics);


        /*
         * 스피너 관련
         */
        Spinner spinner=(Spinner)findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choose_year = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //데이터를 저장하게 되는 리스트
        List<String> spinner_items = new ArrayList<>();

        //스피너와 리스트를 연결하기 위해 사용되는 어댑터
        ArrayAdapter<String> spinner_adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinner_items);
        SimpleDateFormat year = new SimpleDateFormat( "yyyy" );
        int temp = 0;
        for(int i=0;i<Temp.date_Data.size();i++){
            for(int j=0;j<spinner_items.size();j++){
                if(year.format(Temp.date_Data.get(i)).equals(spinner_items.get(j))){
                    temp = 1;
                    break;
                }
            }
            if(temp == 0){
                spinner_items.add(year.format(Temp.date_Data.get(i)));
                temp = 0;
            }
        }

//        spinner_items.add("사과");
//        spinner_items.add("배");
//        spinner_items.add("귤");
//        spinner_items.add("바나나");

        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //스피너의 어댑터 지정
        spinner.setAdapter(spinner_adapter);

//        //toolbar
//        category = getResources().getStringArray(R.array.category);
//
//        toolbar = (Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setLogo(R.drawable.ic_menu_camera);
//
//        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.spinner_dropdown_item);
//        Spinner navigationSpinner = new Spinner(this.getSupportActionBar().getThemedContext());
//        navigationSpinner.setAdapter(spinnerAdapter);
//        toolbar.addView(navigationSpinner, 0);
//
//        navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //Toast.makeText(MainActivity.this,“you selected: ” + category[position],Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }


    public static class PlaceholderFragment extends Fragment {
        public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec",};

        public static String[] days = new String[]{"1", "2", "3", "4", "5", "6", "7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        final static Integer[] length = new Integer[]{31,28,31,30,31,30,31,31,30,31,30,31};

        private LineChartView chartTop;
        private ColumnChartView chartBottom;

        private LineChartData lineData;
        private ColumnChartData columnData;



        public PlaceholderFragment() {
        }

        //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_line_column_dependency, container, false);

            // *** TOP LINE CHART ***
            chartTop = (LineChartView) rootView.findViewById(R.id.chart_top);

            // Generate and set data for line chart
            generateInitialLineData();

            // *** BOTTOM COLUMN CHART ***

            chartBottom = (ColumnChartView) rootView.findViewById(R.id.chart_bottom);

            generateColumnData();



            return rootView;
        }

        private void generateColumnData() {

            int numSubcolumns = 1;
            int numColumns = months.length;
            ArrayList<Float> bottom_graph_values = new ArrayList<>();
            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            //1월~12월 값
            SimpleDateFormat year = new SimpleDateFormat( "yyyy" );
            SimpleDateFormat month = new SimpleDateFormat( "MM" );
            int sum = 0;
            String m="";
            for(int i=1;i<=12;i++){
                if(i<10){
                    m = "0"+Integer.toString(i);
                }
                else{
                    m = Integer.toString(i);
                }
                for(int j=0;j<Temp.date_Data.size();j++){
                    if(year.format(Temp.date_Data.get(j)).equals(choose_year) && month.format(Temp.date_Data.get(j)).equals(m)){
                        sum += Temp.step_Data.get(j);
                    }
                }
                bottom_graph_values.add((float) sum);
                sum = 0;
            }
//            bottom_graph_values.add((float) 10.0);
//            bottom_graph_values.add((float) 25.0);
//            bottom_graph_values.add((float) 30.0);
//            bottom_graph_values.add((float) 40.0);
//            bottom_graph_values.add((float) 50.0);
//            bottom_graph_values.add((float) 60.0);
//            bottom_graph_values.add((float) 70.0);
//            bottom_graph_values.add((float) 80.0);
//            bottom_graph_values.add((float) 90.0);
//            bottom_graph_values.add((float) 100.0);
//            bottom_graph_values.add((float) 110.0);
//            bottom_graph_values.add((float) 120.0);

            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(bottom_graph_values.get(i), ChartUtils.pickColor()));
                }

                axisValues.add(new AxisValue(i).setLabel(months[i]));

                columns.add(new Column(values).setHasLabelsOnlyForSelected(true));//columns 배열은 밑에 그래프 y축 범위를 위한 배열임.
            }

            columnData = new ColumnChartData(columns);

            columnData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            columnData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));//밑에 그래프 y축에 있는 숫자 길이

            chartBottom.setColumnChartData(columnData);

            // Set value touch listener that will trigger changes for chartTop.
            chartBottom.setOnValueTouchListener(new ValueTouchListener());

            // Set selection mode to keep selected month column highlighted.
            chartBottom.setValueSelectionEnabled(true);

            chartBottom.setZoomType(ZoomType.HORIZONTAL);

            // chartBottom.setOnClickListener(new View.OnClickListener() {
            //
            // @Override
            // public void onClick(View v) {
            // SelectedValue sv = chartBottom.getSelectedValue();
            // if (!sv.isSet()) {
            // generateInitialLineData();
            // }
            //
            // }
            // });

        }

        /**
         * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
         * will select value on column chart.
         */
        private void generateInitialLineData() {
            int numValues = length[choose_month-1];
            //위에 그래프 value
            ArrayList<Integer> kk = new ArrayList<>();

            //1월~12월 값
            SimpleDateFormat year = new SimpleDateFormat( "yyyy" );
            SimpleDateFormat month = new SimpleDateFormat( "MM" );
            SimpleDateFormat day = new SimpleDateFormat( "dd" );

            int sum = 0;
            String d="";
            String m="";

            for(int i=1;i<=length[choose_month-1];i++){
                //days[i-1] = Integer.toString(i);
                if(i<10){
                    d = "0"+Integer.toString(i);
                }
                else{
                    d = Integer.toString(i);
                }
                if(choose_month<10){
                    m = "0"+Integer.toString(choose_month);
                }
                else{
                    m = Integer.toString(choose_month);
                }
                for(int j=0;j<Temp.date_Data.size();j++){
                    if(year.format(Temp.date_Data.get(j)).equals(choose_year) && month.format(Temp.date_Data.get(j)).equals(m) && day.format(Temp.date_Data.get(j)).equals(d)){
                        sum += Temp.step_Data.get(j);
                    }
                }
                kk.add(sum);
                sum = 0;
            }

//
//            kk.add(10);
//            kk.add(20);
//            kk.add(30);
//            kk.add(40);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);
//            kk.add(70);
//            kk.add(50);
//            kk.add(60);



            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            List<PointValue> values = new ArrayList<PointValue>();//위에 그래프 값
            for (int i = 0; i < numValues; ++i) {
                values.add(new PointValue(i, kk.get(i)));//위에 그래프 x,y, 값
                axisValues.add(new AxisValue(i).setLabel(days[i]));
            }

            Line line = new Line(values);
            line.setColor(ChartUtils.COLOR_GREEN).setCubic(true);

            List<Line> lines = new ArrayList<Line>();
            lines.add(line);

            lineData = new LineChartData(lines);
            lineData.setAxisXBottom(new Axis(axisValues).setHasLines(true));
            lineData.setAxisYLeft(new Axis().setHasLines(true).setMaxLabelChars(3));

            chartTop.setLineChartData(lineData);

            // For build-up animation you have to disable viewport recalculation.
            chartTop.setViewportCalculationEnabled(false);

            // And set initial max viewport and current viewport- remember to set viewports after data.
            Viewport v = new Viewport(0, 110, length[choose_month-1]-1, 0);//위 그래프 축범위(left,right x축, 나머지 y축)
            chartTop.setMaximumViewport(v);
            chartTop.setCurrentViewport(v);

            chartTop.setZoomType(ZoomType.HORIZONTAL);
        }

        private void generateLineData(int color, float range) {
            // Cancel last animation if not finished.
            chartTop.cancelDataAnimation();

            // Modify data targets
            Line line = lineData.getLines().get(0);// For this example there is always only one line.
            line.setColor(color);
            for (PointValue value : line.getValues()) {
                // Change target only for Y value.
                value.setTarget(value.getX(), value.getY());
            }

            // Start new data animation with 300ms duration;
            chartTop.startDataAnimation(300);
        }

        private class ValueTouchListener implements ColumnChartOnValueSelectListener {

            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

                generateLineData(value.getColor(), 100);
                Toast.makeText(getActivity(),Integer.toString(columnIndex), Toast.LENGTH_SHORT).show();
                choose_month = columnIndex + 1;
                generateInitialLineData();

            }

            @Override
            public void onValueDeselected() {

                generateLineData(ChartUtils.COLOR_GREEN, 0);

            }
        }
    }
}
