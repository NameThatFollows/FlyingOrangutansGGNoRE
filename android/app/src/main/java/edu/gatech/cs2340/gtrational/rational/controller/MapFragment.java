package edu.gatech.cs2340.gtrational.rational.controller;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.cs2340.gtrational.rational.R;
import edu.gatech.cs2340.gtrational.rational.model.Model;
import edu.gatech.cs2340.gtrational.rational.model.web.WebAPI;

/**
 * A fragment for the "Map" screen
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final double DEFAULT_LAT = 40.7143;
    private static final double DEFAULT_LONG = -73.9376;
    private static final int DEFAULT_ZOOM = 11;

    private GoogleMap map;
    private MapView mapView;


    /**
     * The constructor for map fragment
     */
    public MapFragment() {
        // Required empty public constructor
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_filter:
                MapFilterDialogFragment filterDialog = new MapFilterDialogFragment();
                filterDialog.show(getFragmentManager(), "Filter");
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(DEFAULT_LAT, DEFAULT_LONG), DEFAULT_ZOOM
        ));
        Model.getInstance().getRatData(0, 10, this::setMapPins);
    }

    /**
     * Sets the map pins to the provided list
     *
     * @param ratData The list of map pins
     */
    public void setMapPins(List<WebAPI.RatData> ratData) {
        new PlacePinsTask(ratData).execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * Class to asynchronously clear the map, and then place pins based on rat data.
     */
    @SuppressWarnings("FieldMayBeFinal")
    private class PlacePinsTask extends AsyncTask<Void, Void, Void> {

        private final List<WebAPI.RatData> rat_data;

        public PlacePinsTask(List<WebAPI.RatData> rat_data) {
            this.rat_data = new ArrayList<>(rat_data);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            map.clear();
            for (int i = 0; i < rat_data.size(); i++) {
                WebAPI.RatData data = rat_data.get(i);
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(data.latitude, data.longitude))
                        .title(data.uniqueKey + ""));
            }
        }
    }
}
