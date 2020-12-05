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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HumedadChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HumedadChartFragment extends Fragment {

    private static final String TAG = "MyActivity";

    Query liveDataQuery;
    ChildEventListener childEventListenerLiveData;

    LineChart lineChart;
    LineDataSet lineDataSet =  new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    ArrayList<Entry> tmpEntryList = new ArrayList<>();
    int counterEntry = 0;

    public HumedadChartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HumedadChartFragment.
     */
    public static HumedadChartFragment newInstance(String param1, String param2) {
        HumedadChartFragment fragment = new HumedadChartFragment();
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
        View view = inflater.inflate(R.layout.fragment_humedad_chart, container, false);

        hooks(view);

        getLiveDataModulo();

        return view;
    }

    private void hooks(View view) {
        lineChart = view.findViewById(R.id.humedad_line_chart);
    }

    private void configChart() {

        lineChart.setTouchEnabled(true);

        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.animateX(100);

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

                tmpEntryList.add(new Entry(counterEntry,Float.valueOf(dataModulo.getHumedad())));

                counterEntry++;

                Log.i(TAG, "HumedadChartFragment getLiveDataModulo " +
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
        lineDataSet.setLabel("Humedad");
        lineDataSet.setColor(Color.BLUE);

        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);

        lineData =  new LineData(iLineDataSets);

        Description description =  new Description();
        description.setText("Variación de Humedad cada 30s");

        lineChart.clear();
        configChart();
        lineChart.setDescription(description);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}