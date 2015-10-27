package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Fragment to show a list of crimes.
 */
public class CrimeListFragment extends Fragment {

  // RecyclerView shows a list of ViewHolders, which shows Crimes in a list.
  private RecyclerView mCrimeRecyclerView;
  // Adapter manages data and binding.
  private CrimeAdapter mAdapter;

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

  // Connect the Adapter to the RecyclerView.
  private void updateUI() {
    // Get the singleton instance.
    CrimeLab crimeLab = CrimeLab.get(getActivity());

    List<Crime> crimes = crimeLab.getCrimes();

    mAdapter = new CrimeAdapter(crimes);
    mCrimeRecyclerView.setAdapter(mAdapter);
  }


  // The ViewHolder class holding Views within the RecyclerView.
  private class CrimeHolder extends RecyclerView.ViewHolder {

    // Child View of the ViewHolder.
    public TextView mTitleTextView;

    // Child View is passed in on creation.
    public CrimeHolder(View itemView) {
      super(itemView);

      mTitleTextView = (TextView) itemView;
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
      holder.mTitleTextView.setText(crime.getTitle());
    }

    @Override
    public int getItemCount() {
      return mCrimes.size();
    }
  }
}
