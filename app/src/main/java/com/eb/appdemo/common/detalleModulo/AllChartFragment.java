package com.eb.appdemo.common.detalleModulo;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.DataModulo;
import com.eb.appdemo.entidades.SinglentonUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllChartFragment extends Fragment {

    private static final String TAG = "MyActivity";

    private final LineChart[] lineCharts = new LineChart[5];

    Query liveDataQuery;
    ChildEventListener childEventListenerLiveData;

    ArrayList<Entry> temperatureEntries = new ArrayList<>();
    ArrayList<Entry> humedadEntries = new ArrayList<>();
    ArrayList<Entry> luzEntries = new ArrayList<>();
    ArrayList<Entry> soilEntries = new ArrayList<>();
    ArrayList<Entry> cropEntries = new ArrayList<>();

    int counterEntry = 0;

    public AllChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AllChartFragment.
     */
    public static AllChartFragment newInstance(String param1, String param2) {
        AllChartFragment fragment = new AllChartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_chart, container, false);

        hooks(view);

        getLiveDataModulo();

        return view;
    }

    private void hooks(View view){
        lineCharts[0] = view.findViewById(R.id.temperatura_chart);
        lineCharts[1] = view.findViewById(R.id.humedad_chart);
        lineCharts[2] = view.findViewById(R.id.luminosidad_chart);
        lineCharts[3] = view.findViewById(R.id.ph_suelo_chart);
        lineCharts[4] = view.findViewById(R.id.crop_chart);
    }

    private void setLineCharts(){

        for (int i = 0; i < lineCharts.length; i++) {

            ArrayList<Entry> entries = getEntry(i);

            LineData data = getLineData(entries);

            setupChart(lineCharts[i], data, colors[i % colors.length]);

        }
    }

    private void getLiveDataModulo() {

        String path = "modulos/"+ SinglentonUtil.singlentonUtil.getIdModulo() +"/data";

        liveDataQuery = FirebaseDatabase.getInstance()
                .getReference(path);

        Log.i(TAG, "HumedadChartFragment getLiveDataModulo " +
                "path:"
                + path);

        childEventListenerLiveData = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DataModulo dataModulo = snapshot.getValue(DataModulo.class);

                setEntries(dataModulo);

                counterEntry++;

                setLineCharts();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        liveDataQuery.addChildEventListener(childEventListenerLiveData);

    }


    private void setupChart(LineChart chart, LineData data, int color) {

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);

        chart.getDescription().setEnabled(false);

        chart.setDrawGridBackground(false);

        chart.setTouchEnabled(true);

        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        chart.setPinchZoom(false);

        chart.setBackgroundColor(color);

        chart.setViewPortOffsets(10, 0, 10, 0);

        chart.setData(data);


        Legend l = chart.getLegend();
        l.setEnabled(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setEnabled(false);

        chart.invalidate();
        //chart.animateX(2500);
    }

    private final int[] colors = new int[] {
            Color.rgb(250, 104, 104),
            Color.rgb(89, 199, 250),
            Color.rgb(240, 240, 30),
            Color.rgb(51, 0, 25),
            Color.rgb(137, 230, 81)

    };

    private LineData getLineData(ArrayList<Entry> data){

        LineDataSet lineDataSet =  new LineDataSet(null,null);

        lineDataSet.setValues(data);
        lineDataSet.setLineWidth(1.75f);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleHoleRadius(2.5f);
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setCircleColor(Color.WHITE);
        lineDataSet.setHighLightColor(Color.WHITE);
        lineDataSet.setDrawValues(false);

        return new LineData(lineDataSet);
    }

    private void setEntries(DataModulo dataModulo) {

        temperatureEntries.add(new Entry(counterEntry,Float.valueOf(dataModulo.getTemperatura())));
        humedadEntries.add(new Entry(counterEntry,Float.valueOf(dataModulo.getHumedad())));
        luzEntries.add(new Entry(counterEntry,Float.valueOf(dataModulo.getLuminosidad())));
        soilEntries.add(new Entry(counterEntry,Float.valueOf(dataModulo.getPh_suelo())));
        cropEntries.add(new Entry(counterEntry,Float.valueOf(dataModulo.getCrop())));

    }

    private ArrayList<Entry> getEntry(int index) {
        switch (index) {
            case 0:
                return temperatureEntries;
            case 1:
                return humedadEntries;
            case 2:
                return luzEntries;
            case 3:
                return soilEntries;
            case 4:
                return cropEntries;
            default:
                return temperatureEntries;

        }
    }
}