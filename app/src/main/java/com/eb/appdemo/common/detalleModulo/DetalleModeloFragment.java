package com.eb.appdemo.common.detalleModulo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eb.appdemo.R;
import com.eb.appdemo.entidades.Responsables;
import com.eb.appdemo.entidades.SinglentonUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 44;

    SupportMapFragment supportMapFragment;

    //


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

        /*
        getChildFragmentManager().beginTransaction().replace(R.id.mapFragment, new GoogleMapsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

         */
        setMap();

        return view;
    }

    //TODO Doesn't enter after request permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        Log.i(TAG, "onRequestPermissionsResult requestCode = " + requestCode);
        Log.i(TAG, "onRequestPermissionsResult grantResults[0] = " + grantResults[0]);
        Log.i(TAG, "onRequestPermissionsResult grantResults.length = " + grantResults.length);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
            }
        }
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

    private void setMap(){
        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        Log.i(TAG, "mapFragment = " + supportMapFragment);

        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this.getActivity());

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {

        //TODO location error: I/MyActivity: getCurrentLocation  location= null
            @SuppressLint("MissingPermission") Task<Location> task =
                    fusedLocationProviderClient.getLastLocation();

            Log.i(TAG, "getCurrentLocation TASK= " + task.toString());

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.i(TAG, "getCurrentLocation  location= " + location);
                    if (location != null) {

                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {

                                LatLng latLng = new LatLng(location.getLongitude(), location.getLatitude());

                                MarkerOptions options = new MarkerOptions().position(latLng).title("Here");

                                Log.i(TAG, "getCurrentLocation  options= " + options.toString());

                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                                googleMap.addMarker(options);
                            }
                        });


                    }
                }
            });



    }

}