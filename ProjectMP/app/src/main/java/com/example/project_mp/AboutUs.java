package com.example.project_mp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AboutUs extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "About Us";
    GoogleMap mapF;
    Button BTNBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Log.d(TAG, "onCreate: 1");
        BTNBack = findViewById(R.id.BTNbackABT);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.GmapRegis);
        mapFragment.getMapAsync(this);
        Log.d(TAG, "onCreate: 2");
        BTNBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.d(TAG, "onCreate: 3");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: 1");
        mapF = googleMap;
        LatLng ourPos = new LatLng(-6.2011113,106.7802167);
        mapF.addMarker(new MarkerOptions().position(ourPos).title("Binus University"));
        mapF.moveCamera(CameraUpdateFactory.newLatLng(ourPos));
        mapF.animateCamera(CameraUpdateFactory.newLatLngZoom(ourPos,16.5f));
        Log.d(TAG, "onMapReady: 2");
    }
}
