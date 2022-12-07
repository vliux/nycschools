package org.vliux.nycschools.util

import android.app.Activity
import java.lang.ref.WeakReference

object ActivityUtils {

    fun isAlive(activityRef: WeakReference<Activity>): Boolean {
        return activityRef.get()?.let { it.isDestroyed || it.isFinishing } ?: false
    }
}