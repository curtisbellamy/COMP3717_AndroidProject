package ca.bcit.comp3717_androidproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class OrganizationList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = OrganizationList.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    private static String SERVICE_URL;
    private ArrayList<CulturalOrganization> culturalOrgList;

    // Navigation menu
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        culturalOrgList = new ArrayList<CulturalOrganization>();
        lv = (ListView) findViewById(R.id.listView);
        new GetContacts().execute();

        String message = getIntent().getStringExtra("message_key");
        SERVICE_URL = "https://api.myjson.com/bins/vdar6";


        // Clicks on the list view, pass object to new activity
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        Intent intent = new Intent(OrganizationList.this, OrganizationDetails.class);
                        final CulturalOrganization selectedFromList = (CulturalOrganization) lv.getItemAtPosition(position);
                        intent.putExtra("message_key", selectedFromList);
                        startActivity(intent);

                    }
                }
        );


        //----------------------- Begin navigation menu -------------------------//

        Toolbar toolbar = findViewById(R.id.toolbar);
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
        switch (item.getItemId()) {
            case R.id.nav_event:
                Intent intent = new Intent(OrganizationList.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_map:
                Intent intent2 = new Intent(OrganizationList.this, LoadedMap.class);
                startActivity(intent2);
                break;

            case R.id.nav_venue:
                Intent intent3 = new Intent(OrganizationList.this, CulturalVenueInfo.class);
                startActivity(intent3);
                break;

            case R.id.nav_org :
                // do nothing
                drawer.closeDrawers();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //--------------------------- End navigation menu ---------------------------//

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(OrganizationList.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray orgJsonArray = jsonObj.getJSONArray("features");

                    // looping through All Contacts
                    for (int i = 0; i < orgJsonArray.length(); i++) {

                        JSONObject c = orgJsonArray.getJSONObject(i);
                        String prop = c.getString("properties");
                        JSONObject jo = new JSONObject(prop);

                        String firstName = jo.getString("Name");
                        String details = jo.getString("Descriptn");
                        String address = jo.getString("Address");
                        String website = jo.getString("website");

                        String image = "https://work.anpit.it/wp-content/uploads/2018/05/YMAP-ICONS-PEOPLE-01.png";


                        // tmp hash map for single contact
                        CulturalOrganization co = new CulturalOrganization(firstName, address, website, details);

                        co.setImage(image);

                        // adding contact to contact list
                        culturalOrgList.add(co);

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

            CulturalOrganizationAdapter adapter = new CulturalOrganizationAdapter(OrganizationList.this, culturalOrgList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }
    }
}
