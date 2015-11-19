package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Crime;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * A Cursor leaves a lot to be desired as a way to look at a table. All it does
 * is give you raw column values. This class abstracts that and returns a usable
 * object.
 *
 * A CursorWrapper lets you wrap a Cursor you received from another place and
 * add new methods on top of it.
 */
public class CrimeCursorWrapper extends CursorWrapper {

  public CrimeCursorWrapper(Cursor cursor) {
    super(cursor);
  }


  // Read crime from database and format it into a Crime object.
  public Crime getCrime() {

    // Get fields from database.
    String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
    String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
    long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
    int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

    // Create Crime object to contain the retrieved fields.
    Crime crime = new Crime(UUID.fromString(uuidString));
    crime.setTitle(title);
    crime.setDate(new Date(date));
    crime.setSolved(isSolved != 0);

    return crime;
  }
}
