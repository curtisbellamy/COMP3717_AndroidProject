package ca.bcit.comp3717_androidproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class CulturalOrganizationAdapter extends ArrayAdapter<CulturalOrganization>{
    Context _context;
    public CulturalOrganizationAdapter(Context context, ArrayList<CulturalOrganization> culturalOrgs) {
        super(context, 0, culturalOrgs);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        final CulturalOrganization co = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvFirstName = (TextView) convertView.findViewById(R.id.name);
        TextView tvLastName = (TextView) convertView.findViewById(R.id.address);
        // Populate the data into the template view using the data object
        tvFirstName.setText(co.getName());
//        tvLastName.setText(ce.getCity());
        tvLastName.setText(co.getAddress());

        ImageView imgOnePhoto = (ImageView) convertView.findViewById(R.id.thumbImage);


        if (co.getImage() != null) {
            new ImageDownloaderTask(imgOnePhoto).execute(co.getImage());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}