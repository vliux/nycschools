package org.vliux.nycschools.listing

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
import org.vliux.nycschools.data.HighSchoolRepository
import org.vliux.nycschools.viewmodel.Status

@RunWith(MockitoJUnitRunner::class)
class HighSchoolListViewModelTest {

  companion object {
    private val HIGH_SCHOOL = HighSchool("Test High School Name", "Test DBN")
  }

  @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
  private val activity: Activity = mock {}
  private val highSchoolRepository: HighSchoolRepository = mock {}
  private val viewModel = HighSchoolListViewModel(highSchoolRepository)

  @Test
  fun `test happy path`() = runTest {
    `when`(highSchoolRepository.loadHighSchools(eq(activity))).thenReturn(listOf(HIGH_SCHOOL))
    val viewModelData = viewModel.doLoadHighSchools(activity)
    assertEquals(Status.SUCCESS, viewModelData.status)
    assertEquals(1, viewModelData.data?.size)
    assertEquals(HIGH_SCHOOL, viewModelData.data?.get(0))
  }

  @Test
  fun `test empty school list`() = runTest {
    `when`(highSchoolRepository.loadHighSchools(eq(activity))).thenReturn(listOf())
    val viewModelData = viewModel.doLoadHighSchools(activity)
    assertEquals(Status.SUCCESS, viewModelData.status)
    assertEquals(0, viewModelData.data?.size)
  }
}
