package com.bignerdranch.android.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {

  // Unique ID of the crime.
  private UUID mId;
  // Title of the crime.
  private String mTitle;
  // Date the crime was committed.
  private Date mDate;
  // Date the crime was solved.
  private boolean mSolved;

  public Crime() {
    // Generate unique identifier.
    mId = UUID.randomUUID();
    // Today's date.
    mDate = new Date();
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public UUID getId() {
    return mId;
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }

  public boolean isSolved() {
    return mSolved;
  }

  public void setSolved(boolean solved) {
    mSolved = solved;
  }
}
