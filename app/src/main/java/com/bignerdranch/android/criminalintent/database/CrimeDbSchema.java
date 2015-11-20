package com.bignerdranch.android.criminalintent.database;

/**
 * Store SQLite database schema.
 */
public class CrimeDbSchema {

  // Describe the Crime table in the db.
  public static final class CrimeTable {

    // Name of the table.
    public static final String NAME = "crimes";

    // Columns in the table.
    //
    // With that, you will be able to refer to the column named "title" in a
    // Java-safe way: CrimeTable.Cols.TITLE. That makes it much safer to change
    // your program if you ever need to change the name of that column or add
    // additional data to the table.
    public static final class Cols {
      public static final String UUID = "uuid";
      public static final String TITLE = "title";
      public static final String DATE = "date";
      public static final String SOLVED = "solved";
      public static final String SUSPECT = "suspect";
    }
  }
}
