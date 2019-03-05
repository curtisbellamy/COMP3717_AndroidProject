package ca.bcit.comp3717_androidproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent i = getIntent();
        CulturalEvent ce = (CulturalEvent) i.getSerializableExtra("message_key");
    }
}
