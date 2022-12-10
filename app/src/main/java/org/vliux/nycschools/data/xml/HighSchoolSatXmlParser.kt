package org.vliux.nycschools.data.xml

import android.util.Xml
import java.io.IOException
import java.io.Reader
import org.vliux.nycschools.data.HighSchoolSAT
import org.vliux.nycschools.util.Logger
import org.vliux.nycschools.util.isNumber
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException

object HighSchoolSatXmlParser : HighSchoolXmlParser<HighSchoolSAT>() {

  private const val NODE_SAT_READING_SCORE = "sat_critical_reading_avg_score"
  private const val NODE_SAT_MATH_SCORE = "sat_math_avg_score"
  private const val NODE_SAT_WRITING_SCORE = "sat_writing_avg_score"

  /** Return a map of dbn -> HighSchoolSAT objects. */
  @Throws(XmlPullParserException::class, IOException::class)
  fun parse(reader: Reader): Map<String, HighSchoolSAT> {
    reader.use { r ->
      val parser = Xml.newPullParser()
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
      parser.setInput(r)
      parser.nextTag()
      val satList: List<HighSchoolSAT> = parseResponseNode(parser)
      return satList.associateBy { it.dbn }
    }
  }

  override fun parseRowNode(parser: XmlPullParser): HighSchoolSAT? {
    parser.require(XmlPullParser.START_TAG, null, NODE_ROW)
    var dbn: String? = null
    var satReadingScore: Int? = null
    var satMathScore: Int? = null
    var satWritingScore: Int? = null
    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.eventType != XmlPullParser.START_TAG) {
        continue
      }
      when (parser.name) {
        NODE_DBN -> dbn = parseNodeText(parser, NODE_DBN)
        NODE_SAT_READING_SCORE ->
            satReadingScore = parseNodeTextAsInt(parser, NODE_SAT_READING_SCORE)
        NODE_SAT_MATH_SCORE -> satMathScore = parseNodeTextAsInt(parser, NODE_SAT_MATH_SCORE)
        NODE_SAT_WRITING_SCORE ->
            satWritingScore = parseNodeTextAsInt(parser, NODE_SAT_WRITING_SCORE)
        else -> skip(parser)
      }
    }

    return if (!dbn.isNullOrEmpty()) {
      HighSchoolSAT(dbn, satReadingScore, satMathScore, satWritingScore)
    } else {
      Logger.w(
          "dbn is empty: satReading=${satReadingScore}, satMath=${satMathScore}, satWriting=${satWritingScore}")
      null
    }
  }

  private fun parseNodeTextAsInt(parser: XmlPullParser, nodeName: String): Int? {
    return parseNodeText(parser, nodeName)?.let {
      if (it.isNumber()) {
        it.toInt()
      } else {
        null
      }
    }
        ?: null
  }
}
