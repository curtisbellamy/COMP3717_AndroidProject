package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class EventInfo extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent i = getIntent();
        CulturalEvent ce = (CulturalEvent) i.getSerializableExtra("message_key");

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

        mapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(EventInfo.this, MapLocation.class);
                //intent.putExtra("message_key", selectedFromList);
                startActivity(intent);
            }
        });

    }
}
