package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;


/**
 * Allow user to select time of day using TimePicker widget.
 */
public class TimePickerFragment extends DialogFragment {

  private TimePicker mTimePicker;

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    // The DatePicker View.
    View v = LayoutInflater.from(getActivity())
      .inflate(R.layout.dialog_time, null);

    mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);

    return new AlertDialog.Builder(getActivity())

      // Set the AlertDialog to show the inflated layout.
      .setView(v)

        // Set title of the dialog.
      .setTitle(R.string.time_picker_title)

        // OK button.
      .setPositiveButton(android.R.string.ok, null)
      .create();
  }
}
