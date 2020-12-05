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
import android.widget.SeekBar;
import android.widget.TextView;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.DataModulo;
import com.eb.appdemo.entidades.DataPoint;
import com.eb.appdemo.entidades.SinglentonUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperaturaChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperaturaChartFragment extends Fragment  {

    private static final String TAG = "MyActivity";

    Query liveDataQuery;
    ChildEventListener childEventListenerLiveData;

    LineChart lineChart;
    LineDataSet lineDataSet =  new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    ArrayList<Entry> tmpEntryList = new ArrayList<>();
    int counterEntry = 0;

    public TemperaturaChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TemperaturaChartFragment.
     */
    public static TemperaturaChartFragment newInstance(String param1, String param2) {
        TemperaturaChartFragment fragment = new TemperaturaChartFragment();
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
        View view = inflater.inflate(R.layout.fragment_temperatura_chart, container, false);

        hooks(view);

        getLiveDataModulo();

        return view;
    }

    private void hooks(View view) {
        lineChart = view.findViewById(R.id.temperature_line_chart);

    }

    private void configChart() {

        lineChart.setTouchEnabled(true);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(true);
        lineChart.setHighlightPerDragEnabled(true);
        //lineChart.animateX(1500);

    }

    private void getLiveDataModulo() {

        String path = "modulos/"+ SinglentonUtil.singlentonUtil.getIdModulo() +"/data";

        liveDataQuery = FirebaseDatabase.getInstance()
                .getReference(path);

        Log.i(TAG, "TemperaturaChartFragment getLiveDataModulo " +
                "path:"
                + path);

        childEventListenerLiveData = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DataModulo dataModulo = snapshot.getValue(DataModulo.class);

                tmpEntryList.add(new Entry(counterEntry,Float.valueOf(dataModulo.getTemperatura())));

                counterEntry++;

                Log.i(TAG, "TemperaturaChartFragment getLiveDataModulo " +
                        "addChildEventListener:"
                        + dataModulo.toString());

                showChart(tmpEntryList);
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

        /*

        dataQuery.removeEventListener(eventListener);

         */
    }

    private void showChart(ArrayList<Entry> data) {
        lineDataSet.setValues(data);
        lineDataSet.setLabel("Temperatura");
        lineDataSet.setColor(Color.RED);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);

        lineData =  new LineData(iLineDataSets);

        Description description =  new Description();
        description.setText("Variación de Temperatura cada 30s");

        lineChart.clear();
        lineChart.setDescription(description);
        configChart();

        lineChart.setData(lineData);
        lineChart.invalidate();

    }


}