package com.eb.appdemo.common.detalleModulo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.common.new2.ContactsAdapter;
import com.eb.appdemo.entidades.Modulo;
import com.eb.appdemo.entidades.Responsables;
import com.eb.appdemo.entidades.SinglentonUtil;
import com.eb.appdemo.entidades.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleModeloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleModeloFragment extends Fragment {

    private static final String TAG = "MyActivity";

    private RecyclerView rvResponsables;
    private RecyclerView.Adapter rvResponsablesAdapter;
    private RecyclerView.LayoutManager rvResposanblesLayoutManager;

    ArrayList<Responsables> responsablesArrayList;

    Query responsablesQuery;
    ValueEventListener valueEventListenerResponsables;


    public DetalleModeloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetalleModeloFragment.
     */
    public static DetalleModeloFragment newInstance(String param1, String param2) {
        DetalleModeloFragment fragment = new DetalleModeloFragment();
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
        View view = inflater.inflate(R.layout.fragment_detalle_modelo, container, false);

        responsablesArrayList =  new ArrayList<>();

        hooks(view);

        getResposablesList();

        //setResponsablesAdapterList();

        return view;
    }

    private void hooks(View view) {
        rvResponsables = view.findViewById(R.id.contacts_responsables);

    }

    private void setResponsablesAdapterList(){

        rvResponsables.setNestedScrollingEnabled(false);
        rvResponsables.setHasFixedSize(false);
        rvResposanblesLayoutManager =  new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL,false);

        rvResponsables.setLayoutManager(rvResposanblesLayoutManager);

        rvResponsablesAdapter =  new ResponsablesListAdapter(responsablesArrayList);
        rvResponsables.setAdapter(rvResponsablesAdapter);
    }

    private void getResposablesList() {


        String path = "modulos/"+ SinglentonUtil.singlentonUtil.getIdModulo()+"/responsables";

        Log.i(TAG, "getResposablesList path:" + path);

        responsablesQuery = FirebaseDatabase.getInstance()
                .getReference(path);

        Log.i(TAG, "getResposablesList responsablesQuery:" + responsablesQuery.getRef());

        valueEventListenerResponsables =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data : snapshot.getChildren()) {

                    Responsables responsable = data.getValue(Responsables.class);
                    responsablesArrayList.add(responsable);
                    Log.i(TAG, "DetalleModeloFragment getResposablesList:" + responsable.toString());
                }

                setResponsablesAdapterList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        responsablesQuery.addValueEventListener(valueEventListenerResponsables);


    }
}