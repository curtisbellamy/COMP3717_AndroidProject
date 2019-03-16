package ca.bcit.comp3717_androidproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.io.Serializable;
import java.util.ArrayList;

public class OrganizationDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_details);

        Intent i = getIntent();
        final CulturalOrganization co = (CulturalOrganization) i.getSerializableExtra("message_key");


        TextView title = (TextView) findViewById(R.id.orgTitle);
        TextView address = (TextView) findViewById(R.id.orgAddress);
        TextView details = (TextView) findViewById(R.id.orgDetails);

        title.setText(co.getName());

        if (co.getAddress() != "")
            address.setText(co.getAddress());
        else
            address.setText("N/A");

        if (co.getDescription() != "")
            details.setText(co.getDescription());
        else
            details.setText("N/A");

        details.setMovementMethod(new ScrollingMovementMethod());

        Button mapBtn = (Button) findViewById(R.id.mapBtn);

        if (co.getAddress() == "")
            mapBtn.setEnabled(false);


        mapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(OrganizationDetails.this, MapLocation.class);
                String name = co.getName();
                String address = co.getAddress();
                String date = "";
                String time = "";
                intent.putExtra("message_key1", name);
                intent.putExtra("message_key2", address);
                intent.putExtra("message_key3", date);
                intent.putExtra("message_key4", time);
                startActivity(intent);
            }
        });

        Button webBtn = (Button) findViewById(R.id.webClick);

        final String url = co.getWebsite();

        if(co.getWebsite() == "")
            webBtn.setEnabled(false);

        webBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

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
                Intent intent = new Intent(OrganizationDetails.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_map :
                Intent intent2 = new Intent(OrganizationDetails.this, LoadedMap.class);
                startActivity(intent2);
                break;

            case R.id.nav_venue :
                Intent intent3 = new Intent(OrganizationDetails.this, CulturalVenueInfo.class);
                startActivity(intent3);
                break;

            case R.id.nav_org :
                Intent intent4 = new Intent(OrganizationDetails.this, OrganizationList.class);
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
