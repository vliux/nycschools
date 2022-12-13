package org.vliux.nycschools.util

import android.util.Log
import org.vliux.nycschools.BuildConfig

/**
 * Customized logger which could be used for performance & reliability purposes in the future. For
 * example, writing the logs async and uploading to our backend service. For now it just delegates
 * works to the Android logger.
 */
object Logger {

  private const val TAG = "NYCSchools"

  fun d(message: String) {
    if (BuildConfig.DEBUG && !org.vliux.nycschools.infra.Runtime.isTestRun) {
      Log.d(TAG, message)
    }
  }

  fun w(message: String, tr: Throwable? = null) {
    if (!org.vliux.nycschools.infra.Runtime.isTestRun) {
      Log.w(TAG, message, tr)
    }
  }

  fun e(message: String, tr: Throwable? = null) {
    if (!org.vliux.nycschools.infra.Runtime.isTestRun) {
      Log.e(TAG, message, tr)
    }
  }
}
