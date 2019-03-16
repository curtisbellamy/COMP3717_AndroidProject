package ca.bcit.comp3717_androidproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.util.ArrayList;

public class EventInfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    ArrayList<CulturalEvent> favouritesList;

    //Navigation menu
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent i = getIntent();
        final CulturalEvent ce = (CulturalEvent) i.getSerializableExtra("message_key");

        favouritesList = new ArrayList<>();

        TextView title = (TextView) findViewById(R.id.eventTitle);
        TextView date = (TextView) findViewById(R.id.eventDate);
        TextView time = (TextView) findViewById(R.id.eventTime);
        TextView address = (TextView) findViewById(R.id.eventAddress);
        TextView details = (TextView) findViewById(R.id.eventDetails);

        Button mapBtn = (Button) findViewById(R.id.mapBtn);
        Button webBtn = (Button) findViewById(R.id.webClick);
        final String url = ce.getWebsite();

        webBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //view.loadUrl(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }
        });

        title.setText(ce.getName());

        if (ce.getDate() != "")
            date.setText(ce.getDate());
        else
            date.setText("N/A");

        if (ce.getTime() != "")
            time.setText(ce.getTime());
        else
            time.setText("N/A");

        if (ce.getAddress() == ""){
            address.setText("N/A");
            mapBtn.setEnabled(false);
        }else {
            address.setText(ce.getAddress());
        }

        if (ce.getDescriptn() != "")
            details.setText(ce.getDescriptn());
        else
            details.setText("N/A");


        if(ce.getWebsite() == "") {
            webBtn.setEnabled(false);
        }



        details.setMovementMethod(new ScrollingMovementMethod());

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
                startActivity(intent2);
                break;

            case R.id.nav_venue :
                Intent intent3 = new Intent(EventInfo.this, CulturalVenueInfo.class);
                startActivity(intent3);
                break;

            case R.id.nav_org :
                Intent intent4 = new Intent(EventInfo.this, OrganizationList.class);
                startActivity(intent4);
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
