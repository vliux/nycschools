package org.vliux.nycschools.data.xml

import android.util.Xml
import androidx.annotation.WorkerThread
import org.vliux.nycschools.data.HighSchool
import org.vliux.nycschools.util.Logger
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.Reader

@WorkerThread
object HighSchoolListXmlParser : HighSchoolXmlParser<HighSchool>() {

  @Throws(XmlPullParserException::class, IOException::class)
  fun parse(reader: Reader): List<HighSchool> {
    reader.use { r ->
      val parser = Xml.newPullParser()
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
      parser.setInput(r)
      parser.nextTag()
      return parseResponseNode(parser)
    }
  }

  @Throws(XmlPullParserException::class, IOException::class)
  override fun parseRowNode(parser: XmlPullParser): HighSchool? {
    parser.require(XmlPullParser.START_TAG, null, NODE_ROW)
    var schoolName: String? = null
    var dbn: String? = null
    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.eventType != XmlPullParser.START_TAG) {
        continue
      }
      when (parser.name) {
        NODE_SCHOOL_NAME -> schoolName = parseNodeText(parser, NODE_SCHOOL_NAME)
        NODE_DBN -> dbn = parseNodeText(parser, NODE_DBN)
        else -> skip(parser)
      }
    }

    return if (!schoolName.isNullOrEmpty() && !dbn.isNullOrEmpty()) {
      Logger.d("Found schoolName=${schoolName}, dbn=${dbn}")
      HighSchool(schoolName, dbn)
    } else {
      Logger.w("Either schoolName or dbn is empty: schoolName=${schoolName}, dbn=${dbn}")
      null
    }
  }
}
