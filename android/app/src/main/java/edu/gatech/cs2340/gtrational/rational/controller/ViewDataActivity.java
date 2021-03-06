package edu.gatech.cs2340.gtrational.rational.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.gatech.cs2340.gtrational.rational.R;
import edu.gatech.cs2340.gtrational.rational.model.Model;
import edu.gatech.cs2340.gtrational.rational.model.web.WebAPI;

/**
 * Class for view data activity
 */
public class ViewDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        Intent prev = getIntent();
        Bundle bundle = prev.getExtras();

        if (bundle != null) {
            Log.d("tag", bundle.getString("text"));

            setTitle("Rat Sighting #" + bundle.getString("text"));

            WebAPI.RatData data = Model.getInstance().getRatDataByKey(
                    Integer.parseInt(bundle.getString("text"))
            );

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aa", Locale.US);

            TextView createdDate = findViewById(R.id.created_date);
            createdDate.setText(sdf.format(data.createdTime));
            TextView locationType = findViewById(R.id.location_type);
            locationType.setText(data.locationType);
            TextView zip = findViewById(R.id.zip);
            zip.setText(String.valueOf(data.incidentZip));
            TextView address = findViewById(R.id.address);
            address.setText(data.incidentAddress);
            TextView city = findViewById(R.id.city);
            city.setText(data.city);
            TextView borough = findViewById(R.id.borough);
            borough.setText(data.borough);
            TextView latitude = findViewById(R.id.latitude);
            latitude.setText(String.valueOf(data.latitude));
            TextView longitude = findViewById(R.id.longitude);
            longitude.setText(String.valueOf(data.longitude));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
