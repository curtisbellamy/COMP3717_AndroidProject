package ca.bcit.comp3717_androidproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    private ArrayList<CulturalEvent> toonList;

    // URL to get contacts JSON
    private static String SERVICE_URL = "http://opendata.newwestcity.ca/downloads/cultural-events/EVENTS.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(SERVICE_URL);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                //JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray toonJsonArray = new JSONArray(jsonStr);

                // looping through All Contacts
                for (int i = 0; i < toonJsonArray.length(); i++) {
                    JSONObject c = toonJsonArray.getJSONObject(i);

                    String personId = c.getString("PersonId");
                    String firstName = c.getString("FirstName");
                    String lastName = c.getString("LastName");
                    String occupation = c.getString("Occupation");
                    String gender = c.getString("Gender");
                    String created = c.getString("Created");
                    String picture = c.getString("Picture");

                    // tmp hash map for single contact
                    Toon toon = new Toon();

                    // adding each child node to HashMap key => value
                    toon.setPersonId(Integer.parseInt(personId));
                    toon.setFirstName(firstName);
                    toon.setLastName(lastName);
                    toon.setOccupation(occupation);
                    toon.setGender(gender);
                    toon.setCreated(created);
                    toon.setPicture(picture);

                    // adding contact to contact list
                    toonList.add(toon);
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
}
