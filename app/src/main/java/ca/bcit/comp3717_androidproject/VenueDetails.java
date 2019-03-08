package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VenueDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_details);

        Intent i = getIntent();
        final CulturalVenue cv = (CulturalVenue) i.getSerializableExtra("message_key");

        TextView title = (TextView) findViewById(R.id.eventTitle);
        TextView address = (TextView) findViewById(R.id.eventAddress);
        TextView details = (TextView) findViewById(R.id.eventDetails);

        title.setText(cv.getName());

        if (cv.getAddress() != "")
            address.setText(cv.getAddress());
        else
            address.setText("N/A");

        if (cv.getDescription() != "")
            details.setText(cv.getDescription());
        else
            details.setText("N/A");

        details.setMovementMethod(new ScrollingMovementMethod());

        Button mapBtn = (Button) findViewById(R.id.mapBtn);

        if (cv.getAddress() == "")
            mapBtn.setVisibility(View.GONE);


        mapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(VenueDetails.this, MapLocation.class);
                String name = cv.getName();
                String address = cv.getAddress();
                String date = "";
                String time = "";
                intent.putExtra("message_key1", name);
                intent.putExtra("message_key2", address);
                intent.putExtra("message_key3", date);
                intent.putExtra("message_key4", time);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(VenueDetails.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_map :
                Intent intent2 = new Intent(VenueDetails.this, LoadedMap.class);
                startActivity(intent2);
                break;

            case R.id.nav_venue :
                Intent intent3 = new Intent(VenueDetails.this, CulturalVenueInfo.class);
                startActivity(intent3);
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

}
