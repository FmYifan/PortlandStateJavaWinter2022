package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
      DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
      for(int i = 0; i < size; i++){
        Flight flight = flightList.get(i);
        pw.println(flight.getNumber());
        pw.println(flight.getSource());
        pw.println(dateFormat.format(flight.getDeparture()));
        pw.println(flight.getDestination());
        pw.println(dateFormat.format(flight.getArrival()));
      }

      pw.flush();
    }
  }
}
