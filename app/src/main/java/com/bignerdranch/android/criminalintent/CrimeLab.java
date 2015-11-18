package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;

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

  // To make use of it later in Chapter 16.
  private Context mContext;

  // Database to store crimes.
  private SQLiteDatabase mDatabase;


  // Private constructor, only allow access to singleton instance.
  private CrimeLab(Context context) {

    // Open SQLiteDatabase for crimes.
    mContext = context.getApplicationContext();
    mDatabase = new CrimeBaseHelper(mContext)
      .getWritableDatabase();

    // The mCrimes instantiation line uses diamond notation, <>, which was introduced in Java 7.
    // This shorthand notation tells the compiler to infer the type of items the List will contain
    // based on the generic argument passed in the variable declaration. Here, the compiler will
    // infer that the ArrayList contains Crimes because the variable declaration,
    // private List<Crime> mCrimes;, specifies Crime for the generic argument. (The more verbose
    // equivalent, which developers were required to use prior to Java 7, is
    //   mCrimes = new ArrayList<Crime>();.)
    mCrimes = new ArrayList<>();
  }

  // Get the singleton instance.
  public static CrimeLab get(Context context) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(context);
    }
    return sCrimeLab;
  }

  public void addCrime(Crime c) {
    mCrimes.add(c);
  }

  public List<Crime> getCrimes() {
    return mCrimes;
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
