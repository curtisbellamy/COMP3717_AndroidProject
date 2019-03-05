package ca.bcit.comp3717_androidproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    private static String SERVICE_URL;
    private ArrayList<CulturalEvent> culturalEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        culturalEventList = new ArrayList<CulturalEvent>();
        lv = (ListView) findViewById(R.id.listView);
        new GetContacts().execute();

        String message = getIntent().getStringExtra("message_key");
        SERVICE_URL = "https://api.myjson.com/bins/mamna";

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, EventInfo.class);
                        CulturalEvent selectedFromList = (CulturalEvent) lv.getItemAtPosition(position);
                        //String selectedString = selectedFromList.getName();
                        intent.putExtra("message_key", selectedFromList);
                        startActivity(intent);

                    }
                }
        );

    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
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
                    JSONArray eventJsonArray = jsonObj.getJSONArray("features");

                    // looping through All Contacts
                    for (int i = 0; i < eventJsonArray.length(); i++) {

                        JSONObject c = eventJsonArray.getJSONObject(i);
                        String prop = c.getString("properties");
                        JSONObject jo = new JSONObject(prop);

                        String firstName = jo.getString("Name");
                        String lastName = jo.getString("city");
                        String date = jo.getString("date");

                        // Placeholder image to be changed at a later time
                        String image = "http://www.stleos.uq.edu.au/wp-content/uploads/2016/08/image-placeholder-350x350.png";



                        // tmp hash map for single contact
                        CulturalEvent ce = new CulturalEvent();

                        ce.setName(firstName);
                        ce.setCity(lastName);
                        ce.setPicture(image);
                        ce.setDate(date);

                        // adding contact to contact list
                        culturalEventList.add(ce);

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

            CulturalEventAdapter adapter = new CulturalEventAdapter(MainActivity.this, culturalEventList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }
    }

}

