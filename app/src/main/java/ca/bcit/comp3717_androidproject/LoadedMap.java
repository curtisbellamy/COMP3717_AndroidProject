package ca.bcit.comp3717_androidproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoadedMap extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{

    private String TAG = LoadedMap.class.getSimpleName();

    private ProgressDialog pDialog;

    private GoogleMap mMap;

    private static String SERVICE_URL_VENUE;
    private static String SERVICE_URL_EVENTS;

    private ArrayList<CulturalVenue> venueList;
    private ArrayList<CulturalEvent> eventList;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaded_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SERVICE_URL_VENUE = "https://api.myjson.com/bins/vwf0y";
        SERVICE_URL_EVENTS = "https://api.myjson.com/bins/15knzi";
        new GetContacts().execute();

        //----------------------- Begin navigation menu -------------------------//

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_event :
                Intent intent = new Intent(LoadedMap.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_map :
                // do nothing
                drawer.closeDrawers();
                break;

            case R.id.nav_venue :
                Intent intent2 = new Intent(LoadedMap.this, CulturalVenueInfo.class);
                startActivity(intent2);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //--------------------------- End navigation menu ---------------------------//


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


        } catch(IOException e) {
            e.printStackTrace();
        }

        try {

            for(CulturalVenue venue : venueList) {

                String myLocation = venue.getAddress() + "New Westminster, BC, Canada";
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocationName(myLocation, 1);
                Address address = addresses.get(0);
                double longitude1 = address.getLongitude();
                double latitude1 = address.getLatitude();

                LatLng latlng1 = new LatLng(latitude1, longitude1);

                mMap.addMarker(new MarkerOptions().position(latlng1).title(venue.getName())).setVisible(true);

            }

            for(CulturalEvent event : eventList) {

                String myLocation = event.getAddress() + "New Westminster, BC, Canada";
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocationName(myLocation, 1);
                Address address = addresses.get(0);
                double longitude1 = address.getLongitude();
                double latitude1 = address.getLatitude();

                LatLng latlng1 = new LatLng(latitude1, longitude1);

                mMap.addMarker(new MarkerOptions().position(latlng1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title(event.getName())).setVisible(true);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LoadedMap.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            venueList = new ArrayList<>();
            eventList = new ArrayList<>();

            // Making a request to url and getting response
            String jsonStrVenue = sh.makeServiceCall(SERVICE_URL_VENUE);
            String jsonStrEvent = sh.makeServiceCall(SERVICE_URL_EVENTS);

            Log.e(TAG, "Response from url: " + jsonStrVenue);

            if (jsonStrVenue != null && jsonStrEvent != null) {
                try {
                    JSONObject jsonObjVenue = new JSONObject(jsonStrVenue);
                    JSONArray venueJsonArray = jsonObjVenue.getJSONArray("features");

                    // looping through All Contacts
                    for (int i = 0; i < venueJsonArray.length(); i++) {

                        JSONObject c = venueJsonArray.getJSONObject(i);
                        String prop = c.getString("properties");
                        JSONObject jo = new JSONObject(prop);

                        String firstName = jo.getString("Name");
                        String details = jo.getString("Descriptn");
                        String address = jo.getString("Address");

                        // tmp hash map for single contact
                        CulturalVenue cv = new CulturalVenue();

                        cv.setName(firstName);
                        cv.setAddress(address);

                        // adding contact to contact list
                        venueList.add(cv);


                    }

                    JSONObject jsonObjEvent = new JSONObject(jsonStrEvent);
                    JSONArray eventJsonArray = jsonObjEvent.getJSONArray("features");


                    for (int i = 0; i < eventJsonArray.length(); i++) {

                        JSONObject c = eventJsonArray.getJSONObject(i);
                        String prop = c.getString("properties");
                        JSONObject jo = new JSONObject(prop);

                        String firstName = jo.getString("Name");
                        String details = jo.getString("Descriptn");
                        String address = jo.getString("Address");

                        // tmp hash map for single contact
                        CulturalEvent ce = new CulturalEvent();

                        ce.setName(firstName);
                        ce.setAddress(address);

                        // adding contact to contact list
                        eventList.add(ce);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

        }
    }

}
