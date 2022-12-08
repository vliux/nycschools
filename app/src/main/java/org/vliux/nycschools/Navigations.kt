package org.vliux.nycschools

import android.content.Context
import android.widget.Toast
import org.vliux.nycschools.data.HighSchool

object Navigations {

  fun onSchoolClicked(context: Context, highSchool: HighSchool) {
    Toast.makeText(context, "School: ${highSchool.name}", Toast.LENGTH_SHORT).show()
  }
}
