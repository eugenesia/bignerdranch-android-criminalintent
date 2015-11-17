package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Fragment to show a list of crimes.
 */
public class CrimeListFragment extends Fragment {

  // RecyclerView shows a list of ViewHolders, which shows Crimes in a list.
  private RecyclerView mCrimeRecyclerView;

  // Adapter manages data and binding.
  private CrimeAdapter mAdapter;

  // Whether subtitle is visible. Used to recreate visibility state when toolbar
  // is recreated e.g. when rotating.
  private boolean mSubtitleVisible;


  // Report that this fragment would like to participate in populating the
  // options menu by receiving a call to onCreateOptionsMenu(Menu, MenuInflater)
  // and related methods.
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {

    // Inflate the layout into a View.
    View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

    // Get the RecyclerView from the layout.
    mCrimeRecyclerView = (RecyclerView) view
      .findViewById(R.id.crime_recycler_view);

    // Note that as soon as you create your RecyclerView, you give it another
    // object called a LayoutManager. RecyclerView requires a LayoutManager to
    // work. If you forget to give it one, it will crash.
    mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    updateUI();

    return view;
  }


  // Reload list Views when returning from displaying a crime in
  // CrimePagerActivity, as user may have modified the crime data.
  @Override
  public void onResume() {
    super.onResume();
    updateUI();
  }


  // Inflate the menu resource when Activity creates the options menu.
  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.fragment_crime_list, menu);

    MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
    if (mSubtitleVisible) {
      subtitleItem.setTitle(R.string.hide_subtitle);
    }
    else {
      subtitleItem.setTitle(R.string.show_subtitle);
    }
  }


  // Handle "New crime" action item selected.
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {

      // Check that the "New crime" action item was clicked, via its ID.
      case R.id.menu_item_new_crime:

        // Just create an empty crime for now.
        Crime crime = new Crime();

        // Get the singleton instance of CrimeLab and add a new Crime.
        CrimeLab.get(getActivity()).addCrime(crime);

        // Create a new Intent to start the CrimePagerActivity, storing the ID
        // of the crime to show.
        Intent intent = CrimePagerActivity
          .newIntent(getActivity(), crime.getId());

        startActivity(intent);

        // Return true to indicate that no further processing is necessary, like
        // Javascript preventDefault().
        return true;

      // Show the subtitle.
      case R.id.menu_item_show_subtitle:
        mSubtitleVisible = ! mSubtitleVisible;
        getActivity().invalidateOptionsMenu();
        updateSubtitle();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }


  // Set toolbar's subtitle.
  private void updateSubtitle() {
    CrimeLab crimeLab = CrimeLab.get(getActivity());
    int crimeCount = crimeLab.getCrimes().size();

    // Format the subtitle according to the pre-set format, showing the number
    // of crimes.
    String subtitle = getString(R.string.subtitle_format, crimeCount);

    if (! mSubtitleVisible) {
      subtitle = null;
    }

    // The activity that is hosting the CrimeListFragment is cast to an
    // AppCompatActivity. CriminalIntent uses the AppCompat library, so all
    // activities will be a subclass of AppCompatActivity, which allows you to
    // access the toolbar. For legacy reasons, the toolbar is still referred to
    // as “action bar” in many places within the AppCompat library.
    AppCompatActivity activity = (AppCompatActivity) getActivity();

    activity.getSupportActionBar().setSubtitle(subtitle);
  }


  // Connect the Adapter to the RecyclerView.
  private void updateUI() {
    // Get the singleton instance.
    CrimeLab crimeLab = CrimeLab.get(getActivity());

    List<Crime> crimes = crimeLab.getCrimes();

    if (mAdapter == null) {
      mAdapter = new CrimeAdapter(crimes);
      mCrimeRecyclerView.setAdapter(mAdapter);
    }
    else {
      // Notify listeners that the dataset has changed, as user might have
      // edited the Crime details.
      mAdapter.notifyDataSetChanged();
    }
    // Show or hide subtitle when coming here from onResume() or onCreate().
    updateSubtitle();
  }


  // The ViewHolder class holding Views within the RecyclerView.
  // Implements OnClickListener so we can add a listener handler function as a
  // child method.
  private class CrimeHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {

    // Data about the crime showing in this ViewHolder.
    private Crime mCrime;

    // Child Views of the ViewHolder.

    // Title of the crime.
    private TextView mTitleTextView;
    // Date of crime.
    private TextView mDateTextView;
    // Whether crime was solved.
    private CheckBox mSolvedCheckBox;

    // Child View is passed in on creation.
    public CrimeHolder(View itemView) {
      super(itemView);

      // So we can use a class method to handle the click.
      itemView.setOnClickListener(this);

      // Get child Views from inflated layout View.
      //
      // Calls to findViewById(int) are often expensive. They go door to door
      // throughout your entire itemView looking for your View: "Hey, are you
      // list_item_crime_title_text_view? No? Oh, sorry for troubling you." This
      // takes time to do, and you have to walk all over your memory
      // neighborhood to do it.
      //
      // ViewHolder can relieve a lot of this pain. By stashing the results of
      // these findViewById(int) calls, you only have to spend that time in
      // createViewHolder(). When onBindViewHolder() is called, the work is
      // already done. Which is nice, because onBindViewHolder() is called much
      // more often than onCreateViewHolder().
      mTitleTextView = (TextView)
        itemView.findViewById(R.id.list_item_crime_title_text_view);
      mDateTextView = (TextView)
        itemView.findViewById(R.id.list_item_crime_date_text_view);
      mSolvedCheckBox = (CheckBox)
        itemView.findViewById(R.id.list_item_crime_solved_check_box);
    }

    // Handle click on ViewHolder.
    @Override
    public void onClick(View v) {

      // Store ID of crime to show in the Intent to pass to the newly-started
      // CrimePagerActivity.
      Intent intent = CrimePagerActivity.newIntent(getActivity(),
        mCrime.getId());

      // Start a new CrimePager Activity from this ViewHolder, to show details
      // of a single crime.
      startActivity(intent);
    }

    // Update child Views to show status of crime.
    public void bindCrime(Crime crime) {
      mCrime = crime;
      mTitleTextView.setText(mCrime.getTitle());
      mDateTextView.setText(mCrime.getDate().toString());
      mSolvedCheckBox.setChecked(mCrime.isSolved());
    }
  }


  // Adapter to manage child View data.
  private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

    // List of crimes to populate child ViewHolders with.
    private List<Crime> mCrimes;

    public CrimeAdapter(List<Crime> crimes) {
      mCrimes = crimes;
    }

    // Called by RecyclerView when it needs a new View to display an item.
    // RecyclerView does not expect this to be hooked up to any data yet.
    @Override
    public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

      // Inflate a layout from Android standard library, called
      // "simple_list_item_1". This layout contains a single TextView, styled to
      // look nice in a list.
      View view = layoutInflater
        .inflate(R.layout.list_item_crime, parent, false);

      return new CrimeHolder(view);
    }

    // Bind a ViewHolder's View to the model. Receives the ViewHolder and a
    // position in the data set.
    @Override
    public void onBindViewHolder(CrimeHolder holder, int position) {

      // Get data from model.
      Crime crime = mCrimes.get(position);

      // Update the View to reflect the model data.
      holder.bindCrime(crime);
    }

    @Override
    public int getItemCount() {
      return mCrimes.size();
    }
  }
}
