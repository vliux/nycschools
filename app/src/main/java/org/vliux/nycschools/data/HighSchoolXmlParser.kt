package org.vliux.nycschools.data

import androidx.annotation.WorkerThread
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

@WorkerThread
abstract class HighSchoolXmlParser<T> {

  companion object {
    const val NODE_RESPONSE = "response"
    const val NODE_ROW = "row"
    const val NODE_SCHOOL_NAME = "school_name"
    const val NODE_DBN = "dbn"
  }

  @Throws(XmlPullParserException::class, IOException::class)
  protected fun parseResponseNode(parser: XmlPullParser): List<T> {
    parser.require(XmlPullParser.START_TAG, null, NODE_RESPONSE)
    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.eventType != XmlPullParser.START_TAG) {
        continue
      }
      if (parser.name == NODE_ROW) {
        return parseParentRowNode(parser)
      } else {
        skip(parser)
      }
    }
    return listOf()
  }

  @Throws(XmlPullParserException::class, IOException::class)
  private fun parseParentRowNode(parser: XmlPullParser): List<T> {
    val results = mutableListOf<T>()
    parser.require(XmlPullParser.START_TAG, null, NODE_ROW)
    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.eventType != XmlPullParser.START_TAG) {
        continue
      }
      // individual child node
      if (parser.name == NODE_ROW) {
        parseRowNode(parser)?.let { results.add(it) }
      }
    }
    return results
  }

  @Throws(XmlPullParserException::class, IOException::class)
  abstract fun parseRowNode(parser: XmlPullParser): T?

  @Throws(XmlPullParserException::class, IOException::class)
  protected fun parseNodeText(parser: XmlPullParser, nodeName: String): String? {
    parser.require(XmlPullParser.START_TAG, null, nodeName)
    var result = ""
    if (parser.next() == XmlPullParser.TEXT) {
      result = parser.text
      parser.nextTag()
    }
    parser.require(XmlPullParser.END_TAG, null, nodeName)
    return result
  }

  @Throws(XmlPullParserException::class, IOException::class)
  protected fun skip(parser: XmlPullParser) {
    if (parser.eventType != XmlPullParser.START_TAG) {
      throw IllegalStateException()
    }
    var depth = 1
    while (depth != 0) {
      when (parser.next()) {
        XmlPullParser.END_TAG -> depth--
        XmlPullParser.START_TAG -> depth++
      }
    }
  }
}
