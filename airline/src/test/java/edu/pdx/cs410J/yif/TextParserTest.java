package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test for TextParser
 */
public class TextParserTest {

  /**
   * test valid text file can be parsed
   * @throws ParserException parse() may throw a ParserException
   */
  @Test
  void validTextFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  /**
   * test invalid if invalid text file can throw a ParserException correctly
   */
  @Test
  void invalidTextFileThrowsParserException() {
    InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that a non-numeric flight number issues an error
   */
  @Test
  void testNonNumericFlightNumber(){
    InputStream resource = getClass().getResourceAsStream("invalidFlightNumber.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that a non-three-letters source airport code issues an error
   */
  @Test
  void testNonThreeLettersSrc(){
    InputStream resource = getClass().getResourceAsStream("invalidSrc1.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that a source code does not present in the collection of known airports issues an error
   */
  @Test
  void testNonPresentSrc(){
    InputStream resource = getClass().getResourceAsStream("invalidSrc2.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that an incorrect departure date issues an error
   */
  @Test
  void testIncorrectDepartDate(){
    InputStream resource = getClass().getResourceAsStream("invalidDepartDate.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that an incorrect departure time issues an error
   */
  @Test
  void testIncorrectDepartTime(){
    InputStream resource = getClass().getResourceAsStream("invalidDepartTime.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that a non-three-letters destination airport code issues an error
   */
  @Test
  void testNonThreeLettersDest(){
    InputStream resource = getClass().getResourceAsStream("invalidDest1.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that a destination code does not present in the collection of known airports issues an error
   */
  @Test
  void testNonPresentDest(){
    InputStream resource = getClass().getResourceAsStream("invalidDest2.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that an incorrect arrival date issues an error
   */
  @Test
  void testIncorrectArrivalDate(){
    InputStream resource = getClass().getResourceAsStream("invalidArrivalDate.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that an incorrect arrival date issues an error
   */
  @Test
  void testIncorrectArrivalTime(){
    InputStream resource = getClass().getResourceAsStream("invalidArrivalTime.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that a missing data entry issues an error
   */
  @Test
  void testMissingData(){
    InputStream resource = getClass().getResourceAsStream("missingData.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * Tests that an earlier arrival date issues an error
   */
  @Test
  void earlierArrivalDate(){
    InputStream resource = getClass().getResourceAsStream("earlierArrivalDate.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }

  /**
   * test valid text file (including airline and flights) can be parsed
   * @throws ParserException parse() may throw a ParserException
   */
  @Test
  void validWholeFileCanBeParsed() throws ParserException {
    InputStream resource = getClass().getResourceAsStream("validFlights.txt");
    assertThat(resource, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(resource));
    Airline airline = parser.parse();
    ArrayList<Flight> list = airline.getFlights();
    assertThat(airline.getName(), equalTo("test airline"));
    assertThat(list.get(0).toString(), equalTo("Flight 410 departs HRL at 1/20/22, 3:15 AM arrives HSV at 2/10/22, 4:00 PM"));
    assertThat(list.get(1).toString(), equalTo("Flight 410 departs PDX at 1/1/22, 12:00 AM arrives PDX at 1/2/22, 12:10 AM"));
  }

}
