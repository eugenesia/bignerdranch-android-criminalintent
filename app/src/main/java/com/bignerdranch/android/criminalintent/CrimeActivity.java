package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


public class CrimeActivity extends SingleFragmentActivity {

  // Create a Fragment of the appropriate type for this subclass.
  @Override
  protected Fragment createFragment() {
    return new CrimeFragment();
  }
}
