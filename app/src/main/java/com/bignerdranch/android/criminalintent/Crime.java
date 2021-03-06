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
  // Name of suspect.
  private String mSuspect;


  public Crime() {

    // Generate random ID for this newly-created Crime.
    this(UUID.randomUUID());
  }

  public Crime(UUID id) {
    mId = id;
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

  public String getSuspect() {
    return mSuspect;
  }

  public void setSuspect(String suspect) {
    mSuspect = suspect;
  }

  // Generate unique filename from ID, to store photo of crime.
  public String getPhotoFilename() {
    return "IMG_" + getId().toString() + ".jpg";
  }
}
