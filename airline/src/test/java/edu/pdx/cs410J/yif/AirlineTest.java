package edu.pdx.cs410J.yif;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the Airline class
 */
public class AirlineTest {

    /**
     * This tests if the initial name of the airline is null
     */
    @Test
    void initiallyAirlineNameIsNull(){
        Airline airline = new Airline();
        assertThat(airline.getName(), is(nullValue()));
    }

    /**
     * This tests if the name of the airline can be initialized correctly
     * through the constructor
     */
    @Test
    void airlineNameCanBeInitialized(){
        Airline airline = new Airline("Test");
        assertThat(airline.getName(), equalTo("Test"));
    }

    /**
     * This tests if the Flight object can be added correctly to the flightList
     */
    @Test
    void addFlightWorks(){
        Airline airline = new Airline();
        Flight flight = new Flight();
        airline.addFlight(flight);
        assertThat(airline.getFlights().get(0), equalTo(flight));
    }

    /**
     * This tests if the getFlights return a empty list when no flight is inside
     */
    @Test
    void getFlightsCanBeNull(){
        Airline airline = new Airline();
        assertThat(airline.getFlights().isEmpty(), is(true));
    }

}
