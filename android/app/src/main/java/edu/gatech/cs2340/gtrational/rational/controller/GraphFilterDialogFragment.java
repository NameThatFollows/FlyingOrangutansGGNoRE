package edu.gatech.cs2340.gtrational.rational.controller;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.gatech.cs2340.gtrational.rational.R;

import static edu.gatech.cs2340.gtrational.rational.controller.MapFilterDialogFragment.dateToSeconds;

/**
 * A fragment for the dashboard screen.
 */
public class GraphFilterDialogFragment extends DialogFragment {

    public GraphFilterDialogFragment() {
        // Required empty public constructor
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Filter")
                .setPositiveButton("Apply", (DialogInterface dialog, int id) -> {
                    //Parse date fields
                    EditText startDateField = getDialog().findViewById((R.id.startDate));
                    EditText endDateField = getDialog().findViewById((R.id.endDate));
                    String startDate = startDateField.getText().toString();
                    String endDate = endDateField.getText().toString();

                    try {
                        long startLong = MapFilterDialogFragment.dateToSeconds(startDate);
                        long endLong = MapFilterDialogFragment.dateToSeconds(endDate);
                        // ((MainDashboardActivity)getActivity()).setMapPins(startLong, endLong);
                        // TODO callback method here!
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }

                })
                .setNegativeButton("Cancel", (DialogInterface dialog, int id) -> {
                    // TODO Cancelled
                })
                .setView(R.layout.fragment_graph_filter);
        return builder.create();
    }

}