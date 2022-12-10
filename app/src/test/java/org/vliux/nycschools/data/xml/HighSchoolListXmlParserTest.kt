package org.vliux.nycschools.data.xml

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.StringReader

@RunWith(RobolectricTestRunner::class)
class HighSchoolListXmlParserTest {

  @Test
  fun `test parse school list`() {
    val xml =
        "<response>\n" +
            "    <row>\n" +
            "        <row _id=\"row-w9xs-n2d4-6w87\">\n" +
            "            <dbn>02M260</dbn>\n" +
            "            <school_name>Clinton School Writers &amp; Artists, M.S. 260</school_name>\n" +
            "        </row>\n" +
            "    </row>\n" +
            "</response>"
    val highSchools = HighSchoolListXmlParser.parse(StringReader(xml))
    assertEquals(highSchools.size, 1)
    assertEquals(highSchools.get(0).name, "Clinton School Writers & Artists, M.S. 260")
  }
}
