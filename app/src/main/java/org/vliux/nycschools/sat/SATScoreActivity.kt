package org.vliux.nycschools.sat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import org.vliux.nycschools.R
import org.vliux.nycschools.data.HighSchool
import org.vliux.nycschools.data.HighSchoolSAT
import org.vliux.nycschools.viewmodel.Status

@AndroidEntryPoint
class SATScoreActivity : AppCompatActivity() {

  companion object {
    private const val EXTRA_HIGH_SCHOOL = "extra_high_school"

    fun intent(context: Context, highSchool: HighSchool): Intent {
      val intent = Intent(context, SATScoreActivity::class.java)
      intent.putExtra(EXTRA_HIGH_SCHOOL, highSchool)
      return intent
    }
  }

  private val viewModel: SATScoreViewModel by lazy {
    ViewModelProvider(this).get(SATScoreViewModel::class.java)
  }

  private lateinit var textViewSchool: TextView
  private lateinit var textViewReadingScore: TextView
  private lateinit var textViewWritingScore: TextView
  private lateinit var textViewMathScore: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_satscore)
    textViewSchool = findViewById(R.id.school_tv)
    textViewReadingScore = findViewById(R.id.sat_reading_tv)
    textViewWritingScore = findViewById(R.id.sat_writing_tv)
    textViewMathScore = findViewById(R.id.sat_math_tv)

    intent.getParcelableExtra<HighSchool>(EXTRA_HIGH_SCHOOL)?.let {
      viewModel.highSchoolSAT.observe(this) { viewModelData ->
        when (viewModelData.status) {
          Status.LOADING -> onSATScoreLoading()
          Status.SUCCESS -> onSATScoreLoaded(it, viewModelData.data)
          Status.ERROR -> onSATScoreLoadingFailed()
        }
      }
      viewModel.loadSATScore(this, it)
    }
        ?: onSATScoreLoadingFailed()
  }

  private fun onSATScoreLoading() {}

  private fun onSATScoreLoaded(highSchool: HighSchool, highSchoolSAT: HighSchoolSAT?) {
    textViewSchool.text = highSchool.name
    highSchoolSAT?.apply {
      textViewReadingScore.text = getString(R.string.sat_reading_score, satReadingScore)
      textViewWritingScore.text = getString(R.string.sat_writing_score, satWritingScore)
      textViewMathScore.text = getString(R.string.sat_math_score, satMathScore)
    }
        ?: run {
          textViewReadingScore.text = getString(R.string.no_sat_score_available)
          textViewWritingScore.visibility = View.GONE
          textViewMathScore.visibility = View.GONE
        }
  }

  private fun onSATScoreLoadingFailed() {}
}
