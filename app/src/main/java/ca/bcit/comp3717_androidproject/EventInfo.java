package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ScrollView;
import android.widget.TextView;

public class EventInfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Navigation menu
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent i = getIntent();
        final CulturalEvent ce = (CulturalEvent) i.getSerializableExtra("message_key");

        TextView title = (TextView) findViewById(R.id.eventTitle);
        TextView date = (TextView) findViewById(R.id.eventDate);
        TextView time = (TextView) findViewById(R.id.eventTime);
        TextView address = (TextView) findViewById(R.id.eventAddress);
        TextView details = (TextView) findViewById(R.id.eventDetails);

        title.setText(ce.getName());

        if (ce.getDate() != "")
            date.setText(ce.getDate());
        else
            date.setText("N/A");

        if (ce.getTime() != "")
            time.setText(ce.getTime());
        else
            time.setText("N/A");

        if (ce.getAddress() != "")
            address.setText(ce.getAddress());
        else
            address.setText("N/A");

        if (ce.getDescriptn() != "")
            details.setText(ce.getDescriptn());
        else
            details.setText("N/A");

        details.setMovementMethod(new ScrollingMovementMethod());

        Button mapBtn = (Button) findViewById(R.id.mapBtn);

        if (ce.getAddress() == "")
            mapBtn.setVisibility(View.GONE);


        mapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventInfo.this, MapLocation.class);
                String name = ce.getName();
                String address = ce.getAddress();
                String date = ce.getDate();
                String time = ce.getTime();
                intent.putExtra("message_key1", name + ", ");
                intent.putExtra("message_key2", address);
                intent.putExtra("message_key3", date + ", ");
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
                Intent intent = new Intent(EventInfo.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_map :
                Intent intent2 = new Intent(EventInfo.this, LoadedMap.class);
                //intent2.putExtra("message_key1", culturalVenueList);
                startActivity(intent2);
                break;

            case R.id.nav_venue :
                // do nothing
                drawer.closeDrawers();
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
