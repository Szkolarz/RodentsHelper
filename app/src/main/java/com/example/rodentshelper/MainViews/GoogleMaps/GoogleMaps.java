package com.example.rodentshelper.MainViews.GoogleMaps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.rodentshelper.MainViews.ViewOther;
import com.example.rodentshelper.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

//AppCompatActivity extends FragmentActivity; there has to be AppCompat, cause of Toolbar
public class GoogleMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private TextView textViewName_map, textViewAddress_map, textViewPhone_map;
    private Button buttonAddVet_map, buttonLoadMap_map;
    private LinearLayout linearLayoutData_map, linearLayoutMap_map;


    private GoogleMap map;
    private static final String TAG = GoogleMaps.class.getSimpleName();


    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Warszawa) and default zoom to use when location permission is not granted.
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

        linearLayoutData_map.setVisibility(View.VISIBLE);

        String[] parts = Objects.requireNonNull(marker.getSnippet()).split("[\n]");

        String name = marker.getTitle();
        String address = parts[0];
        String phone = parts[1];

        textViewName_map.setText(name);
        textViewAddress_map.setText(address);
        textViewPhone_map.setText(phone);

        ImageButton imageButtonCall_map = findViewById(R.id.imageButtonCall_map);


        imageButtonCall_map.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        });

        buttonAddVet_map.setOnClickListener(view -> {
            AddVetFromMap addVetFromMap = new AddVetFromMap();
            addVetFromMap.onClickAddVet(name, address, phone, GoogleMaps.this);
        });

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


    public void closeProgressDialog(ViewOther viewOther) {
        Intent intent = new Intent(viewOther, GoogleMaps.class);
        viewOther.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_maps, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info_maps) {
            String link = "<a href=\"https://stowarzyszenie.forum-szynszyla.pl/\">Stowarzyszenie Miłośników Szynszyli Małej</a>";
            String linkOriginalMap ="<a href=\"https://www.google.com/maps/d/u/0/viewer?mid=13Dn6XmgOhnlnB8zFTQ6QxABR6MPUsg0o&ll=52.38497927622458%2C19.90813578222235&z=7&fbclid=IwAR16s6IUjow4yXDb0t9tW2NTsfmIf3f1chR58JHWB14xAlmH73D8WIog3rA/\">tutaj</a>";

            String alertText = "Znaczniki na mapie zostały oznaczone różnymi kolorami w zależności od " +
                    "ich pochodzenia.<br><br>" +
                    "<font color='#b33429'>Czerwone</font> - sprawdzeni i polecani weterynarze przez " + link + ";<br><br>" +
                    "<font color='#206399'>Niebieskie</font> - weterynarze polecani przez użytkowników for oraz grup " +
                    "dyskusyjnych, zweryfikowani przez " + link + ";<br><br>" +
                    "<font color='#6b15cf'>Fioletowe</font> - weterynarze wybrani przez autora aplikacji (na podstawie dobrych ocen " +
                    "według Opinii Google).<br><br>Ostatnia aktualizacja znaczników: 18.01.2023<br><br>" +
                    "Oryginalna i aktualizowana na bieżąco mapa Stowarzyszenia Miłośników Szynszyli Małej znajduje się " + linkOriginalMap + ".";


            AlertDialog.Builder alert = new AlertDialog.Builder(GoogleMaps.this, R.style.InfoDialogStyle);
            alert.setTitle("Informacja o znacznikach");
            alert.setMessage(Html.fromHtml(alertText, 0));

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            TextView msgTxt = alertDialog.findViewById(android.R.id.message);
            msgTxt.setMovementMethod(LinkMovementMethod.getInstance());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vet_map);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Mapa weterynarzy");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> finish());

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            //CameraPosition cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }



        textViewName_map = findViewById(R.id.textViewName_map);
        textViewAddress_map = findViewById(R.id.textViewAddress_map);
        textViewPhone_map = findViewById(R.id.textViewPhone_map);
        buttonAddVet_map = findViewById(R.id.buttonAddVet_map);
        buttonLoadMap_map = findViewById(R.id.buttonLoadMap_map);


        SharedPreferences prefsLoadMap = getApplicationContext().getSharedPreferences("prefsLoadMap", Context.MODE_PRIVATE);

        linearLayoutMap_map = findViewById(R.id.linearLayoutMap_map);

        linearLayoutData_map = findViewById(R.id.linearLayoutData_map);
        linearLayoutData_map.setVisibility(View.GONE);


        buttonLoadMap_map.setOnClickListener(view -> {
            buttonLoadMap_map.setVisibility(View.GONE);
            linearLayoutMap_map.setVisibility(View.VISIBLE);
            SharedPreferences.Editor prefsEditorLoadMap = prefsLoadMap.edit();
            prefsEditorLoadMap.putBoolean("prefsLoadMap", true);
            prefsEditorLoadMap.apply();
            MapsInitializer.initialize(this);
            loadMapToActivity();
        });




        //true
        if (prefsLoadMap.getBoolean("prefsLoadMap", false)) {
            loadMapToActivity();
        } else {
            buttonLoadMap_map.setVisibility(View.VISIBLE);
            linearLayoutMap_map.setVisibility(View.GONE);
        }



    }

    private void loadMapToActivity () {

        Thread threadPrepare = new Thread(() -> runOnUiThread(() -> new Handler().postDelayed(() -> {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().penaltyDeath().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Thread thread = new Thread(() -> {

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.vetMap);

                runOnUiThread(() -> {
                    assert mapFragment != null;
                    mapFragment.getMapAsync(googleMap -> {
                        map = googleMap;
                        map.setOnMapClickListener(position -> linearLayoutData_map.setVisibility(View.GONE));

                        Thread thread1 = new Thread(() -> {
                            getLocationPermission();
                            getDeviceLocation();

                            runOnUiThread(() -> {
                                updateLocationUI();
                                loadMarkers();
                            });
                        });

                        thread1.start();
                        assert mapFragment != null;
                        mapFragment.getMapAsync(GoogleMaps.this);
                    });
                });

            });

            thread.start();

        }, 500)));
        threadPrepare.start();

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(GoogleMaps.this);
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
        }
        super.onSaveInstanceState(outState);
    }


    public void loadMarkers() {


        Markers markers = new Markers();
        markers.createMarkers(map);
        map.setOnMarkerClickListener(this);


        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(@NonNull Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(@NonNull Marker marker) {

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
    public void onMapReady (@NonNull GoogleMap googleMap){

    }






    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, FAR_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    } else {

                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        map.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
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
}