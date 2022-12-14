package org.vliux.nycschools.util

import android.util.Log
import org.vliux.nycschools.BuildConfig

/**
 * Customized logger which could be used for performance & reliability purposes in the future. For
 * example, writing the logs async and uploading to a backend service. For now it just delegates
 * works to the Android logger.
 */
object Logger {

  private const val TAG = "NYCSchools"

  fun d(message: String) {
    if (BuildConfig.DEBUG) {
      Log.d(TAG, message)
    }
  }

  fun w(message: String, tr: Throwable? = null) {
    Log.w(TAG, message, tr)
  }

  fun e(message: String, tr: Throwable? = null) {
    Log.e(TAG, message, tr)
  }
}
