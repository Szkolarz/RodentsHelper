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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
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

public class Markers implements GoogleMap.OnMarkerClickListener {


    public void createMarkers(GoogleMap map) {

        map.addMarker(new MarkerOptions()
                .title("j i j")
                .position(new LatLng (51.16496939725749, 16.949910944054086))
                .snippet("57-220 Ziębice, ul Kolejowa 56\n555444333"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna REPTILIO")
                .position(new LatLng (50.302700104253006, 19.019787))
                .snippet("Olimpijska 6, 41-100 Siemianowice Śląskie\n327824082"));

        map.addMarker(new MarkerOptions()
                .title("Dudkowiak S., lek. wet. Lecznica dla zwierząt")
                .position(new LatLng (50.598249864718504, 17.03987761354501))
                .snippet("Przemysłowa 5, 57-220 Ziębice\n748191266"));





        /*LatLng wroclaw = new LatLng(51.172831, 16.946519);
        map.addMarker(new MarkerOptions().position(wroclaw).title("Wrocław ."));
        map.moveCamera(CameraUpdateFactory.newLatLng(wroclaw));*/





    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}

