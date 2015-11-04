package com.bignerdranch.android.criminalintent;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Hosts the AlertDialog.
 */
public class DatePickerFragment extends DialogFragment {

  // Key for Extra to pass date back to CrimeFragment.
  public static final String EXTRA_DATE =
    "com.bignerdranch.android.criminalintent.date";

  // Key to store date in bundle.
  private static final String ARG_DATE = "date";

  private DatePicker mDatePicker;


  // Create a new instance of the fragment, with the date stored in its Bundle.
  public static DatePickerFragment newInstance(Date date) {

    Bundle args = new Bundle();
    args.putSerializable(ARG_DATE, date);

    DatePickerFragment fragment = new DatePickerFragment();
    fragment.setArguments(args);

    return fragment;
  }


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

    // Date of the crime to be shown.
    Date date = (Date) getArguments().getSerializable(ARG_DATE);

    // DatePickerFragment needs to initialize the DatePicker using the
    // information held in the Date. However, initializing the DatePicker
    // requires integers for the month, day, and year. Date is more of a
    // timestamp and cannot provide integers like this directly.
    //
    // To get the integers you need, you must create a Calendar object and use
    // the Date to configure the Calendar. Then you can retrieve the required
    // information from the Calendar.

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    // The DatePicker View.
    View v = LayoutInflater.from(getActivity())
      .inflate(R.layout.dialog_date, null);

    mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
    mDatePicker.init(year, month, day, null);

    return new AlertDialog.Builder(getActivity())

      // Set the AlertDialog to show the inflated layout.
      .setView(v)

      // Set title of the dialog.
      .setTitle(R.string.date_picker_title)

      // Set OK button but return null listener for now.
      .setPositiveButton(android.R.string.ok, null)
      .create();
  }


  // Send selected date back to target Fragment (calling Fragment).
  private void sendResult(int resultCode, Date date) {

    if (getTargetFragment() == null) {
      return;
    }

    // Activity.onActivityResult() is the method that the ActivityManager calls
    // on the parent activity after the child activity dies. When dealing with
    // activities, you do not call Activity.onActivityResult() yourself; that is
    // the ActivityManager’s job. After the activity has received the call, the
    // activity’s FragmentManager then calls Fragment.onActivityResult() on the
    // appropriate fragment.
    //
    // When dealing with two fragments hosted by the same activity, you can
    // borrow Fragment.onActivityResult() and call it directly on the target
    // fragment to pass back data.

    Intent intent = new Intent();
    intent.putExtra(EXTRA_DATE, date);

    getTargetFragment()
      .onActivityResult(getTargetRequestCode(), resultCode, intent);
  }
}
