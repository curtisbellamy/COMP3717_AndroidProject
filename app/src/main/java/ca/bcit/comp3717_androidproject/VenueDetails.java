package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VenueDetails extends AppCompatActivity {

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
                intent.putExtra("message_key", cv);
                startActivity(intent);
            }
        });
    }
}
