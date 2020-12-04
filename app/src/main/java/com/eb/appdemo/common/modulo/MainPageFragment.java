package com.eb.appdemo.common.modulo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.DataModulo;
import com.eb.appdemo.entidades.ModeloCardView;
import com.eb.appdemo.entidades.Modulo;
import com.eb.appdemo.entidades.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainPageFragment extends Fragment {

    private static final String TAG = "MyActivity";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView modelosRecycleView;
    private RecyclerView.Adapter modelosRecycleViewAdapter;
    private RecyclerView.LayoutManager modelosRvLayoutManager;

    ArrayList<ModeloCardView> modeloCardViewArrayList;

    private String mParam1;
    private String mParam2;

    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainPageFragment.
     */

    public static MainPageFragment newInstance(String param1, String param2) {
        MainPageFragment fragment = new MainPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);

        hooks(view);

        modeloCardViewArrayList =  new ArrayList<>();

        getModulosData();

        setListaModulosAdapter();


        return view;
    }

    private void hooks(View view) {
        modelosRecycleView =  view.findViewById(R.id.modelosRecycleView);
    }

    private void setListaModulosAdapter() {
        modelosRecycleView.setNestedScrollingEnabled(false);
        modelosRecycleView.setHasFixedSize(false);


        modelosRvLayoutManager =  new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false);

        modelosRecycleView.setLayoutManager(modelosRvLayoutManager);


        modelosRecycleViewAdapter = new ModuloListaAdapter(modeloCardViewArrayList);
        modelosRecycleView.setAdapter(modelosRecycleViewAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    private void getModulosData() {

         User sUser = User.singletonUser;

        Log.i(TAG, "MainPageFragment getModulosData sUser:"
                + sUser.getModulos());

        //TODO obtener los id de los modulos que posee el usuario
        List<String> modulosUser = sUser.getModulos();

        Log.i(TAG, "MainPageFragment getModulosData modulosUser:"
                + modulosUser);

        //TODO obtener la data general y la última data y actualizar automaticamente
        ModeloCardView modeloCardView =  new ModeloCardView();

        for (String s : modulosUser) {

            getModuloData(s, modeloCardView);
            //getLiveDataModulo(s);
            getLastLiveDataModulo(s, modeloCardView);

            modeloCardViewArrayList.add(modeloCardView);
            Log.i(TAG, "MainPageFragment getModulosData modeloCardView:"
                    + modeloCardView.toString());
            modeloCardView = new ModeloCardView();
        }

        //TODO poblar los card con la data


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getLastLiveDataModulo(String id, ModeloCardView modeloCardView) {

        String path = "modulos/"+id+"/data";

        Query lastDataQuery = FirebaseDatabase.getInstance()
                .getReference(path).limitToLast(1);

        lastDataQuery.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DataModulo dataModulo = snapshot.getValue(DataModulo.class);

                modeloCardView.setVariableData(dataModulo);

                modelosRecycleViewAdapter.notifyDataSetChanged();

                Log.i(TAG, "MainPageFragment getLastLiveDataModulo " +
                        "addChildEventListener:"
                        + dataModulo.toString());
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
        });

    }

    private void getLiveDataModulo(String id) {

        String path = "modulos/"+id+"/data";

        Query dataQuery = FirebaseDatabase.getInstance()
                .getReference(path);

        Log.i(TAG, "MainPageFragment getLiveDataModulo " +
                "path:"
                + path);

        dataQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                DataModulo dataModulo = snapshot.getValue(DataModulo.class);

                Log.i(TAG, "MainPageFragment getLiveDataModulo " +
                        "addChildEventListener:"
                        + dataModulo.toString());
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
        });

        /*
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    Log.i(TAG, "MainPageFragment getLiveDataModulo " +
                            "onDataChange data:"
                            + data.toString());

                    DataModulo dataModulo = data.getValue(DataModulo.class);

                    Log.i(TAG, "MainPageFragment getLiveDataModulo " +
                            "DataModulo:"
                            + dataModulo.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dataQuery.addValueEventListener(eventListener);

        dataQuery.removeEventListener(eventListener);

         */
    }


    private void getModuloData(String id, ModeloCardView modeloCardView){

        Query moduloQuery = FirebaseDatabase.getInstance()
                .getReference("modulos")
                .orderByChild("id")
                .equalTo(id);

        moduloQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()) {
                    Modulo modulo = data.getValue(Modulo.class);
                    modeloCardView.setFixedData(modulo);

                    Log.i(TAG, "MainPageFragment getModulosData modulo:"
                            + modulo.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}