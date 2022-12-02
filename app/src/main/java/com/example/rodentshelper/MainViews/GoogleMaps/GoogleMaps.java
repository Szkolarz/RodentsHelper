package com.example.rodentshelper.MainViews.GoogleMaps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import com.example.rodentshelper.Alerts;
import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.AppDatabase;
import com.example.rodentshelper.ROOM.DAOVets;
import com.example.rodentshelper.ROOM.DAOWeight;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.example.rodentshelper.ROOM.Vet.VetModel;
import com.example.rodentshelper.ROOM._MTM._RodentVet.VetWithRodentsCrossRef;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

public class GoogleMaps extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    TextView tt, textViewName_map, textViewAddress_map, textViewPhone_map;
    SupportMapFragment vetMap;
    Button buttonAddVet_map;
    LinearLayout linearLayoutData_map;


    private GoogleMap map;
    private CameraPosition cameraPosition;
    private static final String TAG = GoogleMaps.class.getSimpleName();


    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(52.237049, 21.017532);
    private static final int FAR_ZOOM = 10;
    private static final int DEFAULT_ZOOM = 14;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(final Marker marker) {

       // Integer clickCount = (Integer) marker.getTag();

        linearLayoutData_map.setVisibility(View.VISIBLE);

        String[] parts = marker.getSnippet().split("[\n]");

        String name = marker.getTitle();
        String address = parts[0];
        String phone = parts[1];

        textViewName_map.setText(name);
        textViewAddress_map.setText(address);
        textViewPhone_map.setText(phone);



        buttonAddVet_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddVetFromMap addVetFromMap = new AddVetFromMap();
                addVetFromMap.onClickAddVet(name, address, phone, GoogleMaps.this);
            }
        });

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vet_map);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.vetMap);
        mapFragment.getMapAsync(this);

        tt = findViewById(R.id.tt);
        textViewName_map = findViewById(R.id.textViewName_map);
        textViewAddress_map = findViewById(R.id.textViewAddress_map);
        textViewPhone_map = findViewById(R.id.textViewPhone_map);
        buttonAddVet_map = findViewById(R.id.buttonAddVet_map);

        linearLayoutData_map = findViewById(R.id.linearLayoutData_map);
        linearLayoutData_map.setVisibility(View.GONE);

        /*tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            onClickTxt();
            }
        });*/

// Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }


    public void loadMarkers() {

        /*qqq = map.addMarker(new MarkerOptions()
                .title("j i j")
                .position(new LatLng (51.16496939725749, 16.949910944054086))
                .snippet("57-220 Ziębice, ul Kolejowa 56;\n555444333"));


        LatLng wroclaw = new LatLng(51.172831, 16.946519);
        map.addMarker(new MarkerOptions().position(wroclaw).title("Wrocław ."));
        map.moveCamera(CameraUpdateFactory.newLatLng(wroclaw));
*/

        Markers markers = new Markers();
        markers.createMarkers(map);
        map.setOnMarkerClickListener(this);


        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getApplicationContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady (GoogleMap googleMap){
        this.map = googleMap;

      //  map.setMyLocationEnabled(true);


       // LatLng wroclaw = new LatLng(51.172831, 16.946519);
       // map.addMarker(new MarkerOptions().position(wroclaw).title("Wrocław ."));
       // map.moveCamera(CameraUpdateFactory.newLatLng(wroclaw));

        getLocationPermission();
        updateLocationUI();
        getDeviceLocation();

        loadMarkers();

    }






    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        System.out.println(";lkijuhygfd\nuhgytgf");
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                updateLocationUI();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            locationPermissionGranted = true;

        }
        updateLocationUI();
    }


    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                getDeviceLocation();
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                //getLocationPermission();
                map.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, FAR_ZOOM));
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }







    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Alerts alert = new Alerts();
                            alert.simpleError("f", "gfd", GoogleMaps.this);

                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }


                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

        }
    }





    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            viewRodents();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private void viewMap() {
        Intent intent = new Intent(GoogleMaps.this, ViewRodents.class);
        startActivity(intent);
    }





}