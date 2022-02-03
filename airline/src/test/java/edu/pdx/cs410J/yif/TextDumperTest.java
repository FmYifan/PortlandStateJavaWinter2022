package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
    String f1 = "05/04/2022 09:08 AM";
    String f2 = "05/4/2022 9:09 pm";
    Date depart = null;
    Date arrival = null;
    try {
      depart = df.parse(f1.trim());
      arrival = df.parse(f2.trim());
    } catch (ParseException e) {
      e.printStackTrace();
      System.exit(1);
    }
    Flight flight = new Flight(3, "PDX", depart, "LAX", arrival);
    airline.addFlight(flight);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().get(0).toString(), equalTo("Flight 3 departs PDX at 5/4/22, 9:08 AM arrives LAX at 5/4/22, 9:09 PM"));
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
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
    String f1 = "05/04/2022 09:08 am";
    String f2 = "05/05/2022 09:09 pm";
    Date depart = null;
    Date arrival = null;
    try {
      depart = df.parse(f1.trim());
      arrival = df.parse(f2.trim());
    } catch (ParseException e) {
      e.printStackTrace();
      System.exit(1);
    }
    Flight flight1 = new Flight(3, "PDX", depart, "LAX", arrival);
    Flight flight2 = new Flight(3, "ABE", depart, "LAX", arrival);
    airline.addFlight(flight1);
    airline.addFlight(flight2);

    File textFile = new File(tempDir, "airline.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(airline);

    TextParser parser = new TextParser(new FileReader(textFile));
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    assertThat(read.getFlights().get(0).toString(), equalTo("Flight 3 departs ABE at 5/4/22, 9:08 AM arrives LAX at 5/5/22, 9:09 PM"));
    assertThat(read.getFlights().get(1).toString(), equalTo("Flight 3 departs PDX at 5/4/22, 9:08 AM arrives LAX at 5/5/22, 9:09 PM"));
  }

}
