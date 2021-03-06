package edu.pdx.cs410J.yif;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    /**
     * This tests if the flight list get sorted correctly
     */
    @Test
    void sortFlightsWorks(){
        Airline airline = new Airline();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
        String f1 = "05/04/2022 09:08 am";
        String f2 = "05/05/2022 11:34 pm";
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
        airline.sortFlights();
        assertThat(airline.getFlights().get(0), equalTo(flight2));
    }

}
