package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

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

  // Convert a crime into a ContentValues object suitable for storing in SQLite.
  private static ContentValues getContentValues(Crime crime) {

    // Writes and updates to databases are done with the assistance of a class
    // called ContentValues. ContentValues is a key-value store class, like
    // Java's HashMap or the Bundles you have been using so far. However, unlike
    // HashMap or Bundle it is specifically designed to store the kinds of data
    // SQLite can hold.

    ContentValues values = new ContentValues();

    // Keys must be column names. Every column is specified here except for _id,
    // which is automatically created for you as a unique row ID.
    values.put(CrimeTable.Cols.UUID, crime.getId().toString());
    values.put(CrimeTable.Cols.TITLE, crime.getTitle());
    values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
    values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);

    return values;
  }

  // Insert a row into database.
  public void addCrime(Crime c) {
    ContentValues values = getContentValues(c);

    // 2nd arg is nullColumnHack. Well, say that you decided to call insert()
    // with an empty ContentValues. SQLite does not allow this, so your insert()
    // call would fail.
    //
    // If you passed in a value of uuid for nullColumnHack, though, it would
    // ignore that empty ContentValues. Instead, it would pass in a
    // ContentValues with uuid set to null. This would allow your insert() to
    // succeed and create a new row.
    mDatabase.insert(CrimeTable.NAME, null, values);
  }

  public List<Crime> getCrimes() {
    return new ArrayList<>();
  }

  // Get crime with a particular id.
  public Crime getCrime(UUID id) {
    // Crime not found.
    return null;
  }

  // Update a crime in the database.
  public void updateCrime(Crime crime) {

    String uuidString = crime.getId().toString();
    ContentValues values = getContentValues(crime);

    // Use placeholder "?" which will be substituted with uuidString, prevent
    // SQL injection.
    mDatabase.update(CrimeTable.NAME, values,
      CrimeTable.Cols.UUID + " = ?",
      new String[] { uuidString });
  }
}
