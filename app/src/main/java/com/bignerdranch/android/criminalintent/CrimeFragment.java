package com.bignerdranch.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCrime = new Crime();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
            Bundle savedInstanceState) {
        
        // Set attachToRoot = false as we will add the inflated view to its
        // parent in code.
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);
        
        // View v contains an EditText, defined inside R.layout.fragment_crime.
        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        
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
        
        // Return inflated fragment view with its listeners back to the
        // Activity.
        return v;
    }
}
