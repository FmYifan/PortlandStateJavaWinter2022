package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for TextDumper
 */
public class TextDumperTest {

  /**
   * Test if airline name is successfully dumped in text format
   */
  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(airline);

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  /**
   * test if text can be written into a file
   * @param tempDir the temp directory
   * @throws IOException may have IOException
   * @throws ParserException may have ParserException
   */
  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
  }

  /**
   * test if airline and flight can be written into a file
   * @param tempDir the temp directory
   * @throws IOException may have IOException
   * @throws ParserException may have ParserException
   */
  @Test
  void canParseAirlineFlightWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    Flight flight = new Flight(2, "src", "10/19/2022", "0:0", "des", "1/20/2022", "12:30");
    airline.addFlight(flight);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().get(0).toString(), equalTo("Flight 2 departs src at 10/19/2022 0:0 arrives des at 1/20/2022 12:30"));
  }

  /**
   * test if airline and multiple flights can be written into a file
   * @param tempDir the temp directory
   * @throws IOException may have IOException
   * @throws ParserException may have ParserException
   */
  @Test
  void canParseMultipleFlightsWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);
    Flight flight1 = new Flight(2, "src", "10/19/2022", "0:0", "des", "1/20/2022", "12:30");
    Flight flight2 = new Flight(4, "pdx", "10/19/2022", "0:0", "sea", "1/20/2022", "12:30");
    airline.addFlight(flight1);
    airline.addFlight(flight2);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().get(0).toString(), equalTo("Flight 2 departs src at 10/19/2022 0:0 arrives des at 1/20/2022 12:30"));
    assertThat(read.getFlights().get(1).toString(), equalTo("Flight 4 departs pdx at 10/19/2022 0:0 arrives sea at 1/20/2022 12:30"));
  }

}
