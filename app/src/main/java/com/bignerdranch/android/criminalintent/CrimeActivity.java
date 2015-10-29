package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;


public class CrimeActivity extends SingleFragmentActivity {

  // Unique key to store the crime ID as an Intent extra.
  public static final String EXTRA_CRIME_ID =
    "com.bignerdranch.android.criminalintent.crime_id";

  // Create an Intent with an extra to store the crime ID.
  public static Intent newIntent(Context packageContext, UUID crimeId) {
    Intent intent = new Intent(packageContext, CrimeActivity.class);
    intent.putExtra(EXTRA_CRIME_ID, crimeId);
    return intent;
  }

  // Create a Fragment of the appropriate type for this subclass.
  @Override
  protected Fragment createFragment() {

    // Notice that the need for independence does not go both ways.
    // CrimeActivity has to know plenty about CrimeFragment, including that it
    // has a newInstance(UUID) method. This is fine. Hosting activities should
    // know the specifics of how to host their fragments, but fragments should
    // not have to know specifics about their activities. At least, not if you
    // want to maintain the flexibility of independent fragments.

    // Get ID of crime passed in by ViewHolder to this Activity's Intent.
    UUID crimeId = (UUID) getIntent()
      .getSerializableExtra(EXTRA_CRIME_ID);

    // Use newInstance() to get a new Fragment instance, with the crime ID in
    // the Fragment's arguments Bundle.
    return CrimeFragment.newInstance(crimeId);
  }
}
