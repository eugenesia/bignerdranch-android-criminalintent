package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

/**
 * Activity to host a Fragment containing a ViewPager, so user can swipe through
 * consecutive crime details.
 */
public class CrimePagerActivity extends FragmentActivity {

  // View that allows swiping from crime to crime.
  private ViewPager mViewPager;
  private List<Crime> mCrimes;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_crime_pager);

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
  }
}
