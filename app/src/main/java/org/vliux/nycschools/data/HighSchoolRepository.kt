package org.vliux.nycschools.data

import android.content.Context
import androidx.annotation.WorkerThread
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStreamReader

/**
 * The interface class to obtain the data regardless of the actual data source(s) used.
 */
@WorkerThread
object HighSchoolRepository {

    private const val ASSET_FILE_HIGH_SCHOOL_LIST = "doe_high_schools_2017.xml"

    @Throws(HighSchoolDataException::class)
    fun loadHighSchools(context: Context): List<HighSchool> {
        try {
            return HighSchoolXmlParser.parse(
                InputStreamReader(
                    context.assets.open(ASSET_FILE_HIGH_SCHOOL_LIST)
                )
            )
        } catch (e: XmlPullParserException) {
            throw HighSchoolDataException("XmlPullParserException when loading high schools", e)
        } catch (e: IOException) {
            throw HighSchoolDataException("IOException when loading high schools", e)
        }
    }
}