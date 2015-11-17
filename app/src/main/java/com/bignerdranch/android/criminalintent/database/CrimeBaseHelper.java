package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper functions for handling database.
 *
 * With your schema defined, you are ready to create the database itself.
 * Android provides some low-level methods on Context to open a database file
 * into an instance of SQLiteDatabase: openOrCreateDatabase(...) and
 * databaseList().
 *
 * However, in practice you will always need to follow a few basic steps:
 * 1. Check to see if the database already exists.
 * 2. If it does not, create it and create the tables and initial data it needs.
 * 3. If it does, open it up and see what version of your CrimeDbSchema it has.
 *    (You may want to add or remove things in future versions of
 *    CriminalIntent.)
 * 4. If it is an old version, run code to upgrade it to a newer version.
 *
 * Android provides the SQLiteOpenHelper class to handle all of this for you.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper {

  private static final int VERSION = 1;
  private static final String DATABASE_NAME = "crimeBase.db";

  public CrimeBaseHelper(Context context) {
    super(context, DATABASE_NAME, null, VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  }
}
