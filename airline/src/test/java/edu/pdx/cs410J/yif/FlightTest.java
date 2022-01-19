package edu.pdx.cs410J.yif;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
   * This tests if the getDeparture can return null
   */
  @Test
  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
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
   * This tests if the getArrival can return null
   */
  @Test
  void forProject1ItIsOkayIfGetArrivalTimeReturnsNull() {
    Flight flight = new Flight();
    assertThat(flight.getArrival(), is(nullValue()));
  }
}
