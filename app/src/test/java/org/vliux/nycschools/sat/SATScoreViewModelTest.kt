package org.vliux.nycschools.sat

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.vliux.nycschools.data.HighSchool
import org.vliux.nycschools.data.HighSchoolDataException
import org.vliux.nycschools.data.HighSchoolRepository
import org.vliux.nycschools.data.HighSchoolSAT
import org.vliux.nycschools.viewmodel.Status

@RunWith(MockitoJUnitRunner::class)
class SATScoreViewModelTest {

  companion object {
    private val HIGH_SCHOOL_NAME = "Test High School Name"
    private val HIGH_SCHOOL = HighSchool(HIGH_SCHOOL_NAME, "Test DBN")
    private val HIGH_SCHOOL_SAT = HighSchoolSAT(HIGH_SCHOOL.dbn, 123, 456, 789)
  }

  @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
  private val activity: Activity = mock {}
  private val highSchoolRepository: HighSchoolRepository = mock {}
  private val viewModel: SATScoreViewModel = SATScoreViewModel(highSchoolRepository)

  @Test
  fun `test happy path`() = runTest {
    `when`(highSchoolRepository.loadSATScore(eq(activity), eq(HIGH_SCHOOL)))
        .thenReturn(HIGH_SCHOOL_SAT)
    val viewModelData = viewModel.doLoadSATScore(activity, HIGH_SCHOOL)
    assertEquals(Status.SUCCESS, viewModelData.status)
    assertEquals(HIGH_SCHOOL_SAT, viewModelData.data)
  }

  @Test
  fun `test no SAT score`() = runTest {
    `when`(highSchoolRepository.loadSATScore(eq(activity), eq(HIGH_SCHOOL))).thenReturn(null)
    val viewModelData = viewModel.doLoadSATScore(activity, HIGH_SCHOOL)
    assertEquals(Status.SUCCESS, viewModelData.status)
    assertEquals(null, viewModelData.data)
  }

  @Test
  fun `test exception in loading SAT score`() = runTest {
    `when`(highSchoolRepository.loadSATScore(eq(activity), eq(HIGH_SCHOOL)))
        .thenThrow(HighSchoolDataException("Test exception", null))
    val viewModelData = viewModel.doLoadSATScore(activity, HIGH_SCHOOL)
    assertEquals(Status.ERROR, viewModelData.status)
  }
}
