package org.vliux.nycschools.listing

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
import org.vliux.nycschools.viewmodel.ViewModelData
import org.vliux.nycschools.viewmodel.ViewModelData.Companion.error
import org.vliux.nycschools.viewmodel.ViewModelData.Companion.loading
import org.vliux.nycschools.viewmodel.ViewModelData.Companion.success
import javax.inject.Inject

@HiltViewModel
class HighSchoolListViewModel
@Inject
constructor(private val highSchoolRepository: HighSchoolRepository) : ViewModel() {

  private val highSchoolsMutable = MutableLiveData<ViewModelData<List<HighSchool>>>()
  val highSchools: LiveData<ViewModelData<List<HighSchool>>> = highSchoolsMutable

  fun loadHighSchools(context: Context) {
    highSchoolsMutable.value = loading()
    viewModelScope.launch { highSchoolsMutable.value = doLoadHighSchools(context) }
  }

  @VisibleForTesting
  suspend fun doLoadHighSchools(context: Context): ViewModelData<List<HighSchool>> {
    return withContext(Dispatchers.IO) {
      try {
        success(highSchoolRepository.loadHighSchools(context))
      } catch (e: HighSchoolDataException) {
        error(e)
      }
    }
  }
}
