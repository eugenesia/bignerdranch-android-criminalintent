package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Image manipulation.
 */
public class PictureUtils {

  public static Bitmap getScaledBitmap(String path, Activity activity) {
    Point size = new Point();
    activity.getWindowManager().getDefaultDisplay()
      .getSize(size);

    return getScaledBitmap(path, size.x, size.y);
  }

  public static Bitmap getScaledBitmap(String path, int destWidth,
    int destHeight) {

    // Read in the dimensions of the image on disk.
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);

    float srcWidth = options.outWidth;
    float srcHeight = options.outHeight;

    // Figure out how much to scale down by.
    // The key parameter above is inSampleSize. This determines how big each
    // "sample" should be for each pixel - a sample size of 1 has one final
    // horizontal pixel for each horizontal pixel in the original file, and a
    // sample size of 2 has one horizontal pixel for every two horizontal pixels
    // in the original file. So when inSampleSize is 2, the image has a quarter
    // of the number of pixels of the original.
    int inSampleSize = 1;

    if (srcHeight > destHeight || srcWidth > destWidth) {

      // Image has portrait aspect ratio.
      if (srcWidth > srcHeight) {
        inSampleSize = Math.round(srcHeight / destHeight);
      }
      // Image has landscape aspect ratio.
      else {
        inSampleSize = Math.round(srcWidth / destWidth);
      }
    }

    options = new BitmapFactory.Options();
    options.inSampleSize = inSampleSize;

    // Read in and create final bitmap.
    return BitmapFactory.decodeFile(path, options);
  }
}
