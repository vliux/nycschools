package org.vliux.nycschools.data

import android.content.Context
import androidx.annotation.WorkerThread
import org.vliux.nycschools.data.xml.HighSchoolListXmlParser
import org.vliux.nycschools.data.xml.HighSchoolSatXmlParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

/** The interface class to obtain the data regardless of the actual data source(s) used. */
@WorkerThread
@Singleton
class HighSchoolRepository @Inject constructor() {

  companion object {
    private const val ASSET_FILE_HIGH_SCHOOL_LIST = "doe_high_schools_2017.xml"
    private const val ASSET_FILE_HIGH_SCHOOL_SAT = "high_schools_sat_2012.xml"
  }

  private var satScoreCache: Map<String, HighSchoolSAT>? = null

  /**
   * Returns a list of high schools from the underlying data source. Current implementation returns
   * all the schools at once (as the data source is not super large). However given enough time we
   * could do optimization by segmenting the results into multiple pages based on client screen
   * sizes.
   */
  @Throws(HighSchoolDataException::class)
  fun loadHighSchools(context: Context): List<HighSchool> {
    try {
      return HighSchoolListXmlParser.parse(
          InputStreamReader(context.assets.open(ASSET_FILE_HIGH_SCHOOL_LIST)))
    } catch (e: XmlPullParserException) {
      throw HighSchoolDataException(
          "loadHighSchools(): XmlPullParserException when loading high schools", e)
    } catch (e: IOException) {
      throw HighSchoolDataException("loadHighSchools(): IOException when loading high schools", e)
    }
  }

  /**
   * Returns the SAT scores (if available) of the given high school. Current implementation loads
   * all the data into memory in the first read. However we could do optimization based on the
   * underlying data source. For example, having a REST API to query the scores from server and
   * cache in client locally.
   */
  @Throws(HighSchoolDataException::class)
  fun loadSATScore(context: Context, highSchool: HighSchool): HighSchoolSAT? {
    try {
      loadSATScoreCacheIfNeeded(context)
      return satScoreCache?.get(highSchool.dbn)
    } catch (e: XmlPullParserException) {
      throw HighSchoolDataException(
          "loadSATScore(): XmlPullParserException when loading high schools", e)
    } catch (e: IOException) {
      throw HighSchoolDataException("loadSATScore(): IOException when loading high schools", e)
    }
  }

  @Synchronized
  private fun loadSATScoreCacheIfNeeded(context: Context) {
    // check cache validity. Real prod code will need more logics.
    if (satScoreCache == null) {
      satScoreCache =
          HighSchoolSatXmlParser.parse(
              InputStreamReader(context.assets.open(ASSET_FILE_HIGH_SCHOOL_SAT)))
    }
  }
}
