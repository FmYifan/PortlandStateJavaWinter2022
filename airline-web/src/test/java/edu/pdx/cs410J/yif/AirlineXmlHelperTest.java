package edu.pdx.cs410J.yif;

import com.google.common.base.Charsets;
import org.apache.tools.ant.util.ReaderInputStream;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AirlineXmlHelperTest {

  @Test
  void canParseValidXmlFile() throws ParserConfigurationException, IOException, SAXException {
    AirlineXmlHelper helper = new AirlineXmlHelper();


    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();
    factory.setValidating(true);

    DocumentBuilder builder =
      factory.newDocumentBuilder();
    builder.setErrorHandler(helper);
    builder.setEntityResolver(helper);

    File curDir = new File(System.getProperty("user.dir"));
    File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/valid-airline.xml");
    assertThat(file, notNullValue());

    InputStream targetStream = new ReaderInputStream(new FileReader(file), Charsets.UTF_8);
    builder.parse(targetStream);
  }

  @Test
  void cantParseInvalidXmlFile() throws ParserConfigurationException, FileNotFoundException {
    AirlineXmlHelper helper = new AirlineXmlHelper();


    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();
    factory.setValidating(true);

    DocumentBuilder builder =
      factory.newDocumentBuilder();
    builder.setErrorHandler(helper);
    builder.setEntityResolver(helper);


    File curDir = new File(System.getProperty("user.dir"));
    File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalid-airline.xml");
    assertThat(file, notNullValue());

    InputStream targetStream = new ReaderInputStream(new FileReader(file), Charsets.UTF_8);
    assertThrows(SAXParseException.class, () ->
      builder.parse(targetStream)
    );
  }

}
