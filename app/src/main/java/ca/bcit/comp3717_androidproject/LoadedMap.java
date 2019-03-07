package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoadedMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaded_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {

            String centralNewWest = "New Westminster, BC, Canada";
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(centralNewWest, 1);
            Address address = addresses.get(0);
            double longitude1 = address.getLongitude();
            double latitude1 = address.getLatitude();


            LatLng latlng1 = new LatLng(latitude1, longitude1);

            // Move the camera instantly to location with a zoom of 15.
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng1, 15));

            // Zoom in, animating the camera.
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent i = getIntent();
        if(i != null) {
            final ArrayList<CulturalEvent> list = (ArrayList<CulturalEvent>) i.getSerializableExtra("message_key1");

            if (list != null) {

                for (CulturalEvent event : list) {

                    String myLocation = event.getAddress() + ", New Westminster, BC, Canada";

                    try {

                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocationName(myLocation, 1);
                        Address address = addresses.get(0);
                        double longitude = address.getLongitude();
                        double latitude = address.getLatitude();

                        LatLng latlng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latlng).title(event.getName() + ", " + event.getDate() + ", " + event.getTime())).setVisible(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }
}
