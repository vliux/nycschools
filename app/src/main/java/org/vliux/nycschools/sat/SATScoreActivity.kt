package org.vliux.nycschools.sat

import android.content.Context
import android.content.Intent
import android.os.Bundle
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

  private var textView: TextView? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_satscore)
    textView = findViewById(R.id.tv)

    intent.getParcelableExtra<HighSchool>(EXTRA_HIGH_SCHOOL)?.let {
      viewModel.highSchoolSAT.observe(this) { viewModelData ->
        when (viewModelData.status) {
          Status.LOADING -> onSATScoreLoading()
          Status.SUCCESS -> onSATScoreLoaded(viewModelData.data)
          Status.ERROR -> onSATScoreLoadingFailed()
        }
      }
      viewModel.loadSATScore(this, it)
    }
        ?: onSATScoreLoadingFailed()
  }

  private fun onSATScoreLoading() {}

  private fun onSATScoreLoaded(highSchoolSAT: HighSchoolSAT?) {
    highSchoolSAT?.apply {
      textView?.setText("reading=${satReadingScore}, math=${satMathScore}, writing=${satMathScore}")
    }
  }

  private fun onSATScoreLoadingFailed() {}
}
