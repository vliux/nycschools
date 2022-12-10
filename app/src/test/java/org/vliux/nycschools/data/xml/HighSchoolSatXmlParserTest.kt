package org.vliux.nycschools.data.xml

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.StringReader

@RunWith(RobolectricTestRunner::class)
class HighSchoolSatXmlParserTest {

  @Test
  fun `test parse SAT scores`() {
    val xml =
        "<response>\n" +
            "    <row>\n" +
            "        <row _id=\"row-1\">\n" +
            "            <dbn>01M292</dbn>\n" +
            "            <school_name>HENRY STREET SCHOOL FOR INTERNATIONAL STUDIES</school_name>\n" +
            "            <num_of_sat_test_takers>29</num_of_sat_test_takers>\n" +
            "            <sat_critical_reading_avg_score>355</sat_critical_reading_avg_score>\n" +
            "            <sat_math_avg_score>404</sat_math_avg_score>\n" +
            "            <sat_writing_avg_score>363</sat_writing_avg_score>\n" +
            "        </row>\n" +
            "        <row _id=\"row-2\">\n" +
            "            <dbn>01M293</dbn>\n" +
            "            <school_name>UNIVERSITY NEIGHBORHOOD HIGH SCHOOL</school_name>\n" +
            "            <num_of_sat_test_takers>91</num_of_sat_test_takers>\n" +
            "            <sat_critical_reading_avg_score>383</sat_critical_reading_avg_score>\n" +
            "            <sat_math_avg_score>423</sat_math_avg_score>\n" +
            "            <sat_writing_avg_score>366</sat_writing_avg_score>\n" +
            "        </row>\n" +
            "    </row>\n" +
            "</response>"
    val satMap = HighSchoolSatXmlParser.parse(StringReader(xml))
    assertEquals(satMap.size, 2)
    assertEquals("355", satMap["01M292"]?.satReadingScore)
    assertEquals("363", satMap["01M292"]?.satWritingScore)
    assertEquals("404", satMap["01M292"]?.satMathScore)

    assertEquals("383", satMap["01M293"]?.satReadingScore)
    assertEquals("366", satMap["01M293"]?.satWritingScore)
    assertEquals("423", satMap["01M293"]?.satMathScore)
  }
}
