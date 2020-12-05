package com.eb.appdemo.common.new2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.eb.appdemo.R;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoogleMapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapsFragment extends Fragment {

    private static final String TAG = "MyActivity";

    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 44;

    SupportMapFragment supportMapFragment;

      /*
    private Location lastKnownLocation;

    private GoogleMap googleMap;
    private MapView mMapView;

     */


    public GoogleMapsFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GoogleMapsFragment.
     */
    public static GoogleMapsFragment newInstance(String param1, String param2) {
        GoogleMapsFragment fragment = new GoogleMapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        //moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        View view = layoutInflater.inflate(R.layout.fragment_google_maps, null, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        Log.i(TAG, "mapFragment = " + supportMapFragment);

        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(this.getContext());

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {
            getCurrentLocation();
        }




        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        Log.i(TAG, "onRequestPermissionsResult requestCode = " + requestCode);
        Log.i(TAG, "onRequestPermissionsResult permissions[] = " + permissions.toString());
        Log.i(TAG, "onRequestPermissionsResult grantResults[] = " + grantResults.toString());

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


    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {

            Task<Location> task = fusedLocationProviderClient.getLastLocation();

            Log.i(TAG, "getCurrentLocation TASK= " + task.toString());

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {

                                LatLng latLng = new LatLng(location.getLongitude(), location.getLongitude());

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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        /*
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        Log.i(TAG,"mapFragment = " + mapFragment) ;
        mapFragment.getMapAsync(this);

         */

    }

    /*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-12.04592,-77.030565))
                .title("Centro de Lima")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        /* Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));



        mMap.animateCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(-12.04592,-77.030565),15));
    }

     */

    @Override
    public void onResume() {
        super.onResume();
        //moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //mMapView.onLowMemory();
    }
}