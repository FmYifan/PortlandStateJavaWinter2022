package edu.pdx.cs410j.yif;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * The class that extends AbstractAirline. An airline contains a name, and a flightList which consists of multiple flights.
 */
public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private ArrayList<Flight> flightList;

  /**
   * The default constructor with no arguments
   */
  public Airline(){
    name = null;
    flightList = new ArrayList<>();
  }

  /**
   * The constructor with an argument name
   * @param name  the name of the airline
   */
  public Airline(String name) {
    this.name = name;
    flightList = new ArrayList<>();
  }

  /**
   *  get the name of the airline
   * @return the name of the airline
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * add a flight object to the flightList of the airline
   * @param flight the flight object added to the list
   */
  @Override
  public void addFlight(Flight flight) {
    flightList.add(flight);
    sortFlights();
  }

  /**
   * get the flightList of the airline
   * @return the flightList of this airline
   */
  @Override
  public ArrayList<Flight> getFlights() {
    return flightList;
  }

  /**
   * Sort the flightLiat
   */
  public void sortFlights(){
    Collections.sort(flightList);
  }
}
