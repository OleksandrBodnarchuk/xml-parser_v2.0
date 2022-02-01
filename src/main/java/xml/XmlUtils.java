package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XmlUtils {
    // Reads data from XML file with SAX
    public static Map<String, Double> readDataFromXml(String fileName) {
        Map<String, Double> currencies = new HashMap<>();
        try {

            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    /* checks if element has 2 attributes and if "currency"
                    length is in international code https://taxsummaries.pwc.com/glossary/currency-codes */
                    if (attributes.getLength() == 2 && attributes.getValue(0).length() == 3) {
                        currencies.put(attributes.getValue(0), Double.valueOf(attributes.getValue(1)));
                    }
                }
            };
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(fileName), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Error while reading from the file " + fileName);
        }
        return currencies;
    }

}
