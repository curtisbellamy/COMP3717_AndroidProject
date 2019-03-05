package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        date.setText(ce.getDate());
        time.setText(ce.getTime());
        address.setText(ce.getAddress());
        details.setText(ce.getDescriptn());

    }
}
