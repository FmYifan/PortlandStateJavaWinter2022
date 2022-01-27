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
    InputStream resource = getClass().getResourceAsStream("invalidSrc.txt");
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
    InputStream resource = getClass().getResourceAsStream("invalidDest.txt");
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
    assertThat(list.get(0).toString(), equalTo("Flight 410 departs src at 1/1/2022 0:0 arrives des at 1/2/2022 12:10"));
    assertThat(list.get(1).toString(), equalTo("Flight 410 departs pdx at 1/20/2022 3:15 arrives lax at 2/10/2022 4:00"));
  }

}
