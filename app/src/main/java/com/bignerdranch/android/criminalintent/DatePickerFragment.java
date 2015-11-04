package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Hosts the AlertDialog.
 */
public class DatePickerFragment extends DialogFragment {

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new AlertDialog.Builder(getActivity())

      // Set title of the dialog.
      .setTitle(R.string.date_picker_title)

      // Set OK button but return null listener for now.
      .setPositiveButton(android.R.string.ok, null)
      .create();
  }
}
