package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment to show a list of crimes.
 */
public class CrimeListFragment extends Fragment {

  private RecyclerView mCrimeRecyclerView;

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

    return view;
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
}
