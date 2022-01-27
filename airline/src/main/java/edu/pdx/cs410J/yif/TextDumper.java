package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class TextDumper implements AirlineDumper<Airline> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  @Override
  public void dump(Airline airline) {
    try (
      PrintWriter pw = new PrintWriter(this.writer, false)
      ) {
      pw.println(airline.getName());
      ArrayList<Flight> flightList = airline.getFlights();
      int size = flightList.size();
      for(int i = 0; i < size; i++){
        Flight flight = flightList.get(i);
        pw.println(flight.getNumber());
        pw.println(flight.getSource());
        pw.println(flight.getDepartureDate());
        pw.println(flight.getDepartureTime());
        pw.println(flight.getDestination());
        pw.println(flight.getArrivalDate());
        pw.println(flight.getArrivalTime());
      }

      pw.flush();
    }
  }
}
