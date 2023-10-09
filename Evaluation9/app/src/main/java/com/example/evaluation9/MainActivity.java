package com.example.evaluation9;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO: goto https://console.cloud.google.com/ to setup the google maps sdk for this project
        //TODO: use the https://developers.google.com/maps/documentation/android-sdk/config to setup the google maps sdk in this project
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    DataServices dataServices = new DataServices();
    ArrayList<LatLng> path = dataServices.getPath();
    LatLngBounds.Builder bounds = new LatLngBounds.Builder();
    @Override
    public void onMapReady(GoogleMap googleMap) {
        PolylineOptions polylineOptions = new PolylineOptions().clickable(true);
               for(LatLng latLng : path){
                   polylineOptions.add(new LatLng(latLng.latitude,latLng.longitude));
                    bounds.include(latLng);
               }
               LatLngBounds latLngBounds = bounds.build();
               Polyline polyline1 = googleMap.addPolyline(polylineOptions);
               LatLng clt = path.get(0);
               LatLng cltEnd = path.get(path.size()-1);
               googleMap.addMarker(new MarkerOptions()
                       .position(clt)
                       .title("CLT"));
               googleMap.addMarker(new MarkerOptions()
                       .position(cltend)
                       .title("CLTEND"));

//googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 700,700, 1));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
    }

    @Override
    public void onPolylineClick(@NonNull Polyline polyline) {

    }

    @Override
    public void onPolygonClick(@NonNull Polygon polygon) {

    }

    //TODO: The retrieved trip points should be plotted on the Google Map
    // using “Polyline” shape https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial

    //TODO: The start and end points of the trip should be indicated with markers
    // https://developers.google.com/maps/documentation/android-sdk/map-with-marker

    //TODO: Also map should be auto zoomed to include all the trip points in the map’s bounding box.
    //Check CameraUpdateFactory class check: https://developers.google.com/android/reference/com/google/android/gms/maps/CameraUpdateFactory

}
