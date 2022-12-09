package org.vliux.nycschools.sat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.vliux.nycschools.R
import org.vliux.nycschools.data.HighSchool

class SATScoreActivity : AppCompatActivity() {

  companion object {
    private const val EXTRA_HIGH_SCHOOL = "extra_high_school"

    fun intent(context: Context, highSchool: HighSchool): Intent {
      val intent = Intent(context, SATScoreActivity::class.java)
      intent.putExtra(EXTRA_HIGH_SCHOOL, highSchool)
      return intent
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_satscore)
  }
}
