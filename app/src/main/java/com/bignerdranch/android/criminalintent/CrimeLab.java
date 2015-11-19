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
  }

  // Get the singleton instance.
  public static CrimeLab get(Context context) {
    if (sCrimeLab == null) {
      sCrimeLab = new CrimeLab(context);
    }
    return sCrimeLab;
  }

  public void addCrime(Crime c) {
  }

  public List<Crime> getCrimes() {
    return new ArrayList<>();
  }

  // Get crime with a particular id.
  public Crime getCrime(UUID id) {
    // Crime not found.
    return null;
  }
}
