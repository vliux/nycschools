package org.vliux.nycschools

import android.content.Context
import android.widget.Toast
import org.vliux.nycschools.data.HighSchool
import org.vliux.nycschools.sat.SATScoreActivity

/** A service to manage the navigation logics inside/across the app. */
object Navigation {

  fun onSchoolClicked(context: Context, highSchool: HighSchool) {
    Toast.makeText(context, "School: ${highSchool.name}", Toast.LENGTH_SHORT).show()
    context.startActivity(SATScoreActivity.intent(context, highSchool))
  }
}
