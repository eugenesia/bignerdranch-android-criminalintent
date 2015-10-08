package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eugene on 08/10/15.
 * Singleton class.
 */
public class CrimeLab {

  // Singleton instance of this class.
  private static CrimeLab sCrimeLab;

  // List of crimes.
  private List<Crime> mCrimes;


  // Get the singleton instance.
  public static CrimeLab get(Context context) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(context);
    }
    return sCrimeLab;
  }

  // Private constructor, only allow access to singleton instance.
  private CrimeLab(Context context) {

    // The mCrimes instantiation line uses diamond notation, <>, which was introduced in Java 7.
    // This shorthand notation tells the compiler to infer the type of items the List will contain
    // based on the generic argument passed in the variable declaration. Here, the compiler will
    // infer that the ArrayList contains Crimes because the variable declaration,
    // private List<Crime> mCrimes;, specifies Crime for the generic argument. (The more verbose
    // equivalent, which developers were required to use prior to Java 7, is
    //   mCrimes = new ArrayList<Crime>();.)
    mCrimes = new ArrayList<>();

    // Populate list with 100 random crimes.
    for (int i = 0; i < 100; i++) {
      Crime crime = new Crime();
      crime.setTitle("Crime #" + i);

      // Every other one is solved.
      crime.setSolved(i % 2);

      mCrimes.add(crime);
    }
  }

  // Get crime with a particular id.
  public Crime getCrime(UUID id) {
    for (Crime crime : mCrimes) {
      if (crime.getId().equals(id)) {
        return crime;
      }
    }
    // Crime not found.
    return null;
  }
}
