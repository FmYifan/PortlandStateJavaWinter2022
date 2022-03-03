package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirportNames;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  /**
   * This tests if all the Flights have the same flight number 0 initially,
   * when no arguments are passed in to create the Flight object
   */
  @Test
  void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight();
    assertThat(flight.getNumber(), equalTo(0));
  }

  /**
   * This tests if the initial source is null
   */
  @Test
  void initiallySourceIsNull(){
    Flight flight = new Flight();
    assertThat(flight.getSource(), is(nullValue()));
  }

  /**
   * This tests if the departure date and time can be null and return null in the getDepartureString
   */
  @Test
  void getDepartureStringReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDepartureString(), is(nullValue()));
  }

  /**
   * This tests if the departure date and time can be null and return null in the getDeparture
   */
  @Test
  void getDepartureReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getDeparture(), is(nullValue()));
  }

  /**
   * This tests if the initial destination is null
   */
  @Test
  void initiallyDestinationIsNull(){
    Flight flight = new Flight();
    assertThat(flight.getDestination(), is(nullValue()));
  }

  /**
   * This tests if the arrival date and time can be null and return null in the getArrivalString
   */
  @Test
  void getArrivalStringReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getArrivalString(), is(nullValue()));
  }

  /**
   * This tests if the arrival date and time can be null and return null in the getDeparture
   */
  @Test
  void getArrivalReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getArrival(), is(nullValue()));
  }

  /**
   * This tests if the pretty print works correctly
   */
  @Test
  void prettyPrintWorks() {
    Airline airline = new Airline();
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
    String f1 = "05/04/2022 09:08 am";
    String f2 = "05/04/2022 4:12 pm";
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
    airline.sortFlights();
    assertThat(flight.prettyPrint(), equalTo("Flight 3 departs from " + AirportNames.getName("PDX") + " at " + flight.getDepartureString()
            + " arrives at " + AirportNames.getName("LAX") + " at " + flight.getArrivalString() + '\n'
            + "The duration of the flight 3 is 7 hours 4 minutes."));
  }

}
