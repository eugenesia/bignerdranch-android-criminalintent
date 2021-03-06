package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Activity to host a Fragment containing a ViewPager, so user can swipe through
 * consecutive crime details.
 */
public class CrimePagerActivity extends AppCompatActivity {

  // Key for Crime ID in Intent.
  private static final String EXTRA_CRIME_ID =
    "com.bignerdranch.android.criminalintent.crime_id";

  // View that allows swiping from crime to crime.
  private ViewPager mViewPager;
  private List<Crime> mCrimes;


  // Create new Intent to start this Activity, storing the Crime ID.
  public static Intent newIntent(Context packageContext, UUID crimeId) {
    Intent intent = new Intent(packageContext, CrimePagerActivity.class);
    intent.putExtra(EXTRA_CRIME_ID, crimeId);
    return intent;
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crime_pager);

    // Get ID of Crime to show from intent.
    UUID crimeId = (UUID) getIntent()
      .getSerializableExtra(EXTRA_CRIME_ID);

    mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

    mCrimes = CrimeLab.get(this).getCrimes();
    FragmentManager fragmentManager = getSupportFragmentManager();

    // Then you set the adapter to be an unnamed instance of
    // FragmentStatePagerAdapter. Creating the FragmentStatePagerAdapter
    // requires the FragmentManager. Remember that FragmentStatePagerAdapter is
    // your agent managing the conversation with ViewPager. For your agent to do
    // its job with the fragments that getItem(int) returns, it needs to be able
    // to add them to your activity. That is why it needs your FragmentManager.
    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

      @Override
      public Fragment getItem(int position) {
        Crime crime = mCrimes.get(position);
        return CrimeFragment.newInstance(crime.getId());
      }

      @Override
      public int getCount() {
        return mCrimes.size();
      }
    });

    // Show the Crime clicked instead of the the Crime with index 0.
    for (int i = 0; i < mCrimes.size(); i++) {

      // Find the Crime with the same UUID as the one passed in this Activity's
      // Intent.
      if (mCrimes.get(i).getId().equals(crimeId)) {
        mViewPager.setCurrentItem(i);
        break;
      }
    }
  }
}
