package org.vliux.nycschools.data

import android.util.Xml
import androidx.annotation.WorkerThread
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.Reader

@WorkerThread
internal object HighSchoolXmlParser {

    private const val NODE_RESPONSE = "response"
    private const val NODE_ROW = "row"
    private const val NODE_SCHOOL_NAME = "school_name"


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

    // TODO Add comment here
    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseResponseNode(parser: XmlPullParser): List<HighSchool> {
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
    private fun parseParentRowNode(parser: XmlPullParser): List<HighSchool> {
        val schools = mutableListOf<HighSchool>()
        parser.require(XmlPullParser.START_TAG, null, NODE_ROW)
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // individual child node
            if (parser.name == NODE_ROW) {
                parseRowNode(parser)?.let { schools.add(it) }
            }
        }
        return schools
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseRowNode(parser: XmlPullParser): HighSchool? {
        parser.require(XmlPullParser.START_TAG, null, NODE_ROW)
        var schoolName: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                NODE_SCHOOL_NAME -> schoolName = parseSchoolNameNode(parser)
                else -> skip(parser)
            }
        }
        return if (!schoolName.isNullOrEmpty()) {
            HighSchool(schoolName)
        } else {
            null
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseSchoolNameNode(parser: XmlPullParser): String? {
        parser.require(XmlPullParser.START_TAG, null, NODE_SCHOOL_NAME)
        val title = parseNodeText(parser)
        parser.require(XmlPullParser.END_TAG, null, NODE_SCHOOL_NAME)
        return title
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun parseNodeText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
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