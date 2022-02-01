package xml;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import xml.XmlUtils;

import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class XmlUtilsTest {


    @Test
    public void readDataFromXmlShouldReturnNotEmptyMap() {
        // given
        final String fileName = "eurofxref-daily.xml";
        // when
        Map<String, Double> fromXml = XmlUtils.readDataFromXml(fileName);
        // then
        Assertions.assertThat(fromXml.isEmpty()).isFalse();
    }

    @Test
    public void readDataFromXmlShouldThrowExceptionIfFileNameIsWrong() {
        // given
        final String wrongFileName = "wrong-name.xml";
       // when
        Exception exception = assertThrows(RuntimeException.class, () -> XmlUtils.readDataFromXml(wrongFileName));
        String expectedMessage = "Error while reading from the file " + wrongFileName;
        String actualMessage = exception.getMessage();
        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }
}