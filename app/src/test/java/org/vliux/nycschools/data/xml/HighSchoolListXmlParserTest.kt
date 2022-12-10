package org.vliux.nycschools.data.xml

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.StringReader

@RunWith(RobolectricTestRunner::class)
class HighSchoolListXmlParserTest {

  @Test
  fun `test parse valid school list`() {
    val xml =
        "<response>\n" +
            "    <row>\n" +
            "        <row _id=\"row-1\">\n" +
            "            <dbn>02M260</dbn>\n" +
            "            <school_name>Clinton School Writers &amp; Artists, M.S. 260</school_name>\n" +
            "        </row>\n" +
            "        <row _id=\"row-2\">\n" +
            "            <dbn>02M261</dbn>\n" +
            "            <school_name>Liberation Diploma Plus High School</school_name>\n" +
            "        </row>\n" +
            "    </row>\n" +
            "</response>"
    val highSchools = HighSchoolListXmlParser.parse(StringReader(xml))
    assertEquals(highSchools.size, 2)
    assertEquals(highSchools[0].name, "Clinton School Writers & Artists, M.S. 260")
    assertEquals(highSchools[0].dbn, "02M260")
    assertEquals(highSchools[1].name, "Liberation Diploma Plus High School")
    assertEquals(highSchools[1].dbn, "02M261")
  }

  @Test
  fun `test parse school list without dbn`() {
    val xml =
        "<response>\n" +
            "    <row>\n" +
            "        <row _id=\"row-1\">\n" +
            "            <school_name>Clinton School Writers &amp; Artists, M.S. 260</school_name>\n" +
            "        </row>\n" +
            "    </row>\n" +
            "</response>"
    val highSchools = HighSchoolListXmlParser.parse(StringReader(xml))
    assertEquals(highSchools.size, 0)
  }

  @Test
  fun `test parse school list without name`() {
    val xml =
        "<response>\n" +
            "    <row>\n" +
            "        <row _id=\"row-1\">\n" +
            "            <dbn>02M260</dbn>\n" +
            "        </row>\n" +
            "    </row>\n" +
            "</response>"
    val highSchools = HighSchoolListXmlParser.parse(StringReader(xml))
    assertEquals(highSchools.size, 0)
  }
}
