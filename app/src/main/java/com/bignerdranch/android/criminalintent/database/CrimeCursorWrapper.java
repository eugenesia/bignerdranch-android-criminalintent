package com.bignerdranch.android.criminalintent.database;

import android.database.Cursor;

/**
 * A Cursor leaves a lot to be desired as a way to look at a table. All it does
 * is give you raw column values. This class abstracts that and returns a usable
 * object.
 *
 * A CursorWrapper lets you wrap a Cursor you received from another place and
 * add new methods on top of it.
 */
public class CrimeCursorWrapper {

  public CrimeCursorWrapper(Cursor cursor) {
    super(cursor);
  }
}
