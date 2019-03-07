package ca.bcit.comp3717_androidproject;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CulturalVenueAdapter extends ArrayAdapter<CulturalVenue>{
    Context _context;
    public CulturalVenueAdapter(Context context, ArrayList<CulturalVenue> culturalVenues) {
        super(context, 0, culturalVenues);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        CulturalVenue cv = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvFirstName = (TextView) convertView.findViewById(R.id.name);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.address);
        // Populate the data into the template view using the data object
        tvFirstName.setText(cv.getName());
//        tvLastName.setText(ce.getCity());
        tvAddress.setText(cv.getAddress());

        ImageView imgOnePhoto = (ImageView) convertView.findViewById(R.id.thumbImage);


        if (cv.getImage() != null) {
            new ImageDownloaderTask(imgOnePhoto).execute(cv.getImage());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}

