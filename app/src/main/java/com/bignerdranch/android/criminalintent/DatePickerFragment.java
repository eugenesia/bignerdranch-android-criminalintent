package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Hosts the AlertDialog.
 */
public class DatePickerFragment extends DialogFragment {

  @Override
  @NonNull
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    // You may be wondering why you went to the trouble of defining and
    // inflating a layout when you could have created the DatePicker object in
    // code.
    //
    // Using a layout makes modifications easy if you change your mind about
    // what the dialog should present. For instance, what if you wanted a
    // TimePicker next to the DatePicker in this dialog? If you are already
    // inflating a layout, you can simply update the layout file, and the new
    // view will appear.
    //
    // Also, notice that the selected date in the DatePicker is automatically
    // preserved across rotation. (With the dialog open, select a date other
    // than the default and press Fn+Control+F12/Ctrl+F1 to see this in action.)
    //
    // How does this happen? Remember that Views can save state across
    // configuration changes, but only if they have an ID attribute. When you
    // created the DatePicker in dialog_date.xml you also asked the build tools
    // to generate a unique ID value for that DatePicker.
    //
    // If you created the DatePicker in code, you would have to programmatically
    // set an ID on the DatePicker for its state saving to work.

    // The DatePicker View.
    View v = LayoutInflater.from(getActivity())
      .inflate(R.layout.dialog_date, null);

    return new AlertDialog.Builder(getActivity())

      // Set the AlertDialog's View to the DatePicker View.
      .setView(v)

      // Set title of the dialog.
      .setTitle(R.string.date_picker_title)

      // Set OK button but return null listener for now.
      .setPositiveButton(android.R.string.ok, null)
      .create();
  }
}
