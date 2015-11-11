package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Eugene on 08/10/15.
 * Abstract reusable Activity that hosts a single Fragment.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

  protected abstract Fragment createFragment();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);

    FragmentManager fm = getSupportFragmentManager();

    // If fragment already exists, find it by ID.
    // It is customary to use the layout's fragment container View ID as the ID
    // of the fragment.
    Fragment fragment = fm.findFragmentById(R.id.fragment_container);

    // Fragment has not been created yet.
    if (fragment == null) {
      fragment = createFragment();

      // Add the newly created fragment thru the fragment manager, using the
      // ID of the fragment container View ID, as the fragment's ID.
      fm.beginTransaction()
        .add(R.id.fragment_container, fragment)
        .commit();
    }
  }
}
