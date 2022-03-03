package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Test for XmlParser
 */
public class XmlParserTest {
    /**
     * test valid xml file can be parsed
     * @throws ParserException parse() may throw a ParserException
     */
    @Test
    void validTextFileCanBeParsed() throws ParserException, FileNotFoundException {

        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/valid-airline.xml");
        assertThat(file, notNullValue());
        XmlParser parser = new XmlParser(new FileReader(file));
        Airline airline = parser.parse();
        assertThat(airline.getName(), equalTo("Valid Airlines"));
    }

    /**
     * test invalid if invalid xml file can throw a ParserException correctly
     */
    @Test
    void invalidTextFileThrowsParserException() throws FileNotFoundException {
        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalid-airline.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that a non-numeric flight number issues an error
     */
    @Test
    void testNonNumericFlightNumber() throws FileNotFoundException {

        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalidFlightNumber.xml");
        assertThat(file, notNullValue());


        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that a non-three-letters destination airport code issues an error
     */
    @Test
    void testNonThreeLettersDest() throws FileNotFoundException {

        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalidDest1.xml");
        assertThat(file, notNullValue());


        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that a destination code does not present in the collection of known airports issues an error
     */
    @Test
    void testNonPresentDest() throws FileNotFoundException {

        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalidDest2.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that an incorrect arrival date issues an error
     */
    @Test
    void testIncorrectArrivalDate() throws FileNotFoundException {

        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalidArrivalDate.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that an incorrect arrival date issues an error
     */
    @Test
    void testIncorrectArrivalTime() throws FileNotFoundException {
        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/invalidArrivalTime.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that a missing data entry issues an error
     */
    @Test
    void testMissingData() throws FileNotFoundException {
        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/missingData.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Tests that an earlier arrival date issues an error
     */
    @Test
    void earlierArrival() throws FileNotFoundException {
        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/earlyArrival.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * test valid text file (including airline and flights) can be parsed
     * @throws ParserException parse() may throw a ParserException
     */
    @Test
    void validWholeFileCanBeParsed() throws ParserException, FileNotFoundException {
        File curDir = new File(System.getProperty("user.dir"));
        File file = new File(curDir.getAbsolutePath() + "/src/test/resources/edu/pdx/cs410J/yif/valid-airline.xml");
        assertThat(file, notNullValue());

        XmlParser parser = new XmlParser(new FileReader(file));
        Airline airline = parser.parse();
        ArrayList<Flight> list = airline.getFlights();
        assertThat(airline.getName(), equalTo("Valid Airlines"));
        assertThat(list.get(0).toString(), equalTo("Flight 1437 departs BJX at 10/25/20, 5:00 PM arrives CMN at 10/26/20, 3:56 AM"));
        assertThat(list.get(1).toString(), equalTo("Flight 7865 departs JNB at 6/15/20, 7:24 AM arrives XIY at 6/16/20, 9:07 AM"));
    }

}
