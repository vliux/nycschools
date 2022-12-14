package org.vliux.nycschools.sat

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.vliux.nycschools.data.HighSchool
import org.vliux.nycschools.data.HighSchoolDataException
import org.vliux.nycschools.data.HighSchoolRepository
import org.vliux.nycschools.data.HighSchoolSAT
import org.vliux.nycschools.viewmodel.ViewModelData
import javax.inject.Inject

@HiltViewModel
class SATScoreViewModel @Inject constructor(val highSchoolRepository: HighSchoolRepository) :
    ViewModel() {

  private val highSchoolSATMutable: MutableLiveData<ViewModelData<HighSchoolSAT>> =
      MutableLiveData()
  val highSchoolSAT: LiveData<ViewModelData<HighSchoolSAT>> = highSchoolSATMutable

  fun loadSATScore(context: Context, highSchool: HighSchool) {
    viewModelScope.launch { highSchoolSATMutable.value = doLoadSATScore(context, highSchool) }
  }

  @VisibleForTesting
  suspend fun doLoadSATScore(
      context: Context,
      highSchool: HighSchool
  ): ViewModelData<HighSchoolSAT> {
    return withContext(Dispatchers.IO) {
      try {
        highSchoolRepository.loadSATScore(context, highSchool)?.let { ViewModelData.success(it) }
            ?: ViewModelData.success(null)
      } catch (e: HighSchoolDataException) {
        ViewModelData.error<HighSchoolSAT>(null)
      }
    }
  }
}
