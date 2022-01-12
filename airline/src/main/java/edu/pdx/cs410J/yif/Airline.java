package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private final String name;
  private ArrayList<Flight> flightList;

  public Airline(String name) {
    this.name = name;
    flightList = new ArrayList<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight flight) {
    throw new UnsupportedOperationException("This method is not implemented yet");
    /*
    flightList.add(flight);
     */
  }

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
    /*

     */
  }
}
