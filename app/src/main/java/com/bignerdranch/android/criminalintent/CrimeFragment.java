package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {

  // Keys for Fragment's Bundle arguments.
  private static final String ARG_CRIME_ID = "crime_id";

  // Key for DialogFragment in FragmentManager.
  private static final String DIALOG_DATE = "DialogDate";

  // Request code for date data returned from DatePickerFragment.
  private static final int REQUEST_DATE = 0;

  // Data for crime being shown.
  private Crime mCrime;
  // Editable title of the crime.
  private EditText mTitleField;
  // Button showing date and allowing edit via date picker.
  private Button mDateButton;
  // Button showing time and allowing edit via time picker.
  private Button mTimeButton;
  // Whether the crime has been solved.
  private CheckBox mSolvedCheckBox;

  // To attach the arguments bundle to a fragment, you call
  // Fragment.setArguments(Bundle). Attaching arguments to a fragment must be
  // done after the fragment is created but before it is added to an activity.
  //
  // To hit this window, Android programmers follow a convention of adding a
  // static method named newInstance() to the Fragment class. This method
  // creates the fragment instance and bundles up and sets its arguments.
  //
  // When the hosting activity needs an instance of that fragment, you have it
  // call the newInstance() method rather than calling the constructor directly.
  // The activity can pass in any required parameters to newInstance() that the
  // fragment needs to create its arguments.
  public static CrimeFragment newInstance(UUID crimeId) {
    Bundle args = new Bundle();
    args.putSerializable(ARG_CRIME_ID, crimeId);

    CrimeFragment fragment = new CrimeFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Get crime ID from this Fragment's arguments Bundle, stashed here by the
    // Activity when creating a new Fragment instance.
    UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);

    mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                           Bundle savedInstanceState) {

    // Set attachToRoot = false as we will add the inflated view to its
    // parent in code.
    View v = inflater.inflate(R.layout.fragment_crime, parent, false);

    // View v contains an EditText, defined inside R.layout.fragment_crime.
    mTitleField = (EditText) v.findViewById(R.id.crime_title);
    mTitleField.setText(mCrime.getTitle());

    // Define a class here to watch for various changes of text.
    mTitleField.addTextChangedListener(new TextWatcher() {

      public void onTextChanged(
        CharSequence c, int start, int before, int count) {
        // Store the edited title string.
        mCrime.setTitle(c.toString());
      }

      public void beforeTextChanged(
        CharSequence c, int start, int count, int after) {
        // This space intentionally left blank.
      }

      public void afterTextChanged(Editable c) {
        // This space intentionally left blank.
      }
    });

    // Show crime date on button.
    mDateButton = (Button) v.findViewById(R.id.crime_date);
    updateDate();

    // Pop out a date picker dialog on click.
    mDateButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        FragmentManager manager = getActivity()
          .getSupportFragmentManager();

        // Create a new DatePickerFragment with the crime's date bundled in.
        DatePickerFragment dialog = DatePickerFragment
          .newInstance(mCrime.getDate());

        // Receive data back from the DialogFragment.
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

        // Get the dialog added to the FragmentManager with the key, and put it
        // on screen.
        dialog.show(manager, DIALOG_DATE);
      }
    });


    // Show time of crime.
    mTimeButton = (Button) v.findViewById(R.id.crime_time);
    mTimeButton.setText(new SimpleDateFormat("HH:mm").format(mCrime.getDate()));


    mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
    mSolvedCheckBox.setChecked(mCrime.isSolved());

    // Allow changing crime's solved property via checkbox.
    mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // Set the crime's solved property.
        mCrime.setSolved(isChecked);
      }
    });

    // Return inflated fragment view with its listeners back to the
    // Activity.
    return v;
  }


  // Get date from DatePickerFragment when that closes.
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) {
      return;
    }

    if (requestCode == REQUEST_DATE) {
      Date date = (Date) data
        .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
      mCrime.setDate(date);
      updateDate();
    }
  }


  // Update mDateButton to show Crime date.
  private void updateDate() {
    mDateButton.setText(mCrime.getDate().toString());
  }
}
