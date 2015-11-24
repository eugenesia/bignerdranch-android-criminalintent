package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {

  // Keys for Fragment's Bundle arguments.
  private static final String ARG_CRIME_ID = "crime_id";

  // Key for DialogFragment in FragmentManager.
  private static final String DIALOG_DATE = "DialogDate";

  // Request code for date data returned from DatePickerFragment.
  private static final int REQUEST_DATE = 0;

  // Key to store contact (suspect) passed back here.
  private static final int REQUEST_CONTACT = 1;

  // Data for crime being shown.
  private Crime mCrime;
  // Editable title of the crime.
  private EditText mTitleField;
  // Button showing date and allowing edit via date picker.
  private Button mDateButton;
  // Whether the crime has been solved.
  private CheckBox mSolvedCheckBox;
  // Button to select suspect.
  private Button mSuspectButton;
  // Button to send crime report.
  private Button mReportButton;
  // Button to take photo.
  private ImageButton mPhotoButton;
  // Button to show photo.
  private ImageView mPhotoView;


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


  // Write out updated crime when this Fragment is done.
  @Override
  public void onPause() {
    super.onPause();

    CrimeLab.get(getActivity())
      .updateCrime(mCrime);
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

    // Button to send a crime report.
    mReportButton = (Button) v.findViewById(R.id.crime_report);

    mReportButton.setOnClickListener(new View.OnClickListener() {

      public void onClick(View v) {

        // Create implicit intent.
        Intent i = new Intent(Intent.ACTION_SEND);
        // There is no constructor that accepts a type, so set explicitly.
        i.setType("text/plain");

        // You include the text of the report and the string for the subject of
        // the report as extras. Note that these extras use constants defined in
        // the Intent class. Any activity responding to this intent will know
        // these constants and what to do with the associated values.
        i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
        i.putExtra(Intent.EXTRA_SUBJECT,
          getString(R.string.crime_report_subject));

        // Always show a chooser to choose an app.
        // Create a chooser to display the activities that respond to the
        // implicit intent. Then pass the intent into startActivity().
        i = Intent.createChooser(i, getString(R.string.send_report));

        startActivity(i);
      }
    });

    // Intent to select a contact as the "suspect".
    final Intent pickContact = new Intent(Intent.ACTION_PICK,
      ContactsContract.Contacts.CONTENT_URI);

    // Add dummy category so that no apps will match the category, to simulate
    // having no contacts app.
    // pickContact.addCategory(Intent.CATEGORY_HOME);

    mSuspectButton = (Button) v.findViewById(R.id.crime_suspect);
    mSuspectButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // Return the details of the contact selected as the result.
        startActivityForResult(pickContact, REQUEST_CONTACT);
      }
    });

    if (mCrime.getSuspect() != null) {
      mSuspectButton.setText(mCrime.getSuspect());
    }

    // PackageManager knows about all the components on the device.
    PackageManager packageManager = getActivity().getPackageManager();

    // Check with PackageManager if a contact app is installed.
    if (packageManager.resolveActivity(pickContact,
      PackageManager.MATCH_DEFAULT_ONLY) == null) {
      mSuspectButton.setEnabled(false);
    }

    // Camera button and photo image.
    mPhotoButton = (ImageButton) v.findViewById(R.id.crime_camera);
    mPhotoView = (ImageView) v.findViewById(R.id.crime_photo);

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
    else if (requestCode == REQUEST_CONTACT && data != null) {

      Uri contactUri = data.getData();

      // Specify which fields you want your query to return values for.
      String[] queryFields = new String[] {
        ContactsContract.Contacts.DISPLAY_NAME
      };

      // Now you need to get a result back from the contacts application.
      // Contacts information is shared by many applications, so Android
      // provides an in-depth API for working with contacts information through
      // a ContentProvider. Instances of this class wrap databases and make it
      // available to other applications. You can access a ContentProvider
      // through a ContentResolver.

      // Perform your query - the contactUri is like a "where" clause here.
      Cursor c = getActivity().getContentResolver()
        .query(contactUri, queryFields, null, null, null);

      try {
        // Double-check that you actually got results.
        if (c.getCount() == 0) {
          return;
        }

        // Pull out the first column of the first row of data - that is your
        // suspect's name.
        c.moveToFirst();
        String suspect = c.getString(0);

        mCrime.setSuspect(suspect);
        mSuspectButton.setText(suspect);
      }
      finally {
        c.close();
      }
    }
  }


  // Update mDateButton to show Crime date.
  private void updateDate() {
    mDateButton.setText(mCrime.getDate().toString());
  }


  // Get the text for a crime report.
  private String getCrimeReport() {

    // Text describing whether the crime was solved.
    String solvedString = null;
    if (mCrime.isSolved()) {
      solvedString = getString(R.string.crime_report_solved);
    }
    else {
      solvedString = getString(R.string.crime_report_unsolved);
    }

    String dateFormat = "EEE, MMM dd";
    String dateString =
      DateFormat.format(dateFormat, mCrime.getDate()).toString();

    // Get suspect name.
    String suspect = mCrime.getSuspect();
    if (suspect == null) {
      suspect = getString(R.string.crime_report_no_suspect);
    }
    else {
      suspect = getString(R.string.crime_report_suspect, suspect);
    }

    // Generate the report from individual pieces of text.
    String report = getString(R.string.crime_report,
        mCrime.getTitle(), dateString, solvedString, suspect);

    return report;
  }
}
