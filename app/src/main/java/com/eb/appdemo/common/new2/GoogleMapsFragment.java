package com.eb.appdemo.common.new2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.eb.appdemo.R;
import com.eb.appdemo.common.modulo.NewModuloFragment;
import com.eb.appdemo.common.util.Constantes;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
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
public class GoogleMapsFragment extends Fragment  {

    private static final String TAG = "MyActivity";
    private NewModuloFragment moduloFragment;

    FusedLocationProviderClient fusedLocationProviderClientient;

    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;




    private GoogleMap googleMap;
    private MapView mMapView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GoogleMapsFragment() {
        // Required empty public constructor
    }

    public GoogleMapsFragment(NewModuloFragment moduloFragment) {
        this.moduloFragment = moduloFragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleMapsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleMapsFragment newInstance(String param1, String param2) {
        GoogleMapsFragment fragment = new GoogleMapsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        //moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        View view = layoutInflater.inflate(R.layout.fragment_google_maps, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        Log.i(TAG,"mapFragment = " + mapFragment) ;


        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            //googleMap.setTrafficEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            fusedLocationProviderClientient = LocationServices.getFusedLocationProviderClient(this.getActivity());

            if(ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            } else {

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            }

            /*
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-12.04592,-77.030565))
                    .title("Centro de Lima")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            googleMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(-12.04592,-77.030565),15));

             */

            googleMap.setOnMapClickListener(latLng -> {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + ":" + latLng.longitude);
                googleMap.clear();

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        latLng, 10
                ));

                googleMap.addMarker(markerOptions);
            });
        });


        /*
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(bundle);
        mMapView.onResume();

        Log.i(TAG,"mMapView = " + mMapView) ;

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
            moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(googleMap -> {
            mMap = googleMap;

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setTrafficEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(-12.04592,-77.030565))
                    .title("Centro de Lima")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            mMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(-12.04592,-77.030565),15));
            moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
        });

         */

        return view;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
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


    private void getCurrentLocation(){
        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClientient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    LatLng latLng = new LatLng(location.getLongitude(),location.getLongitude());

                    MarkerOptions options = new MarkerOptions().position(latLng).title("Here");

                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                    googleMap.addMarker(options);
                }
            }
        });

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
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
        moduloFragment.setTitle(Constantes.GOOGLE_MAPS_TITLE);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}