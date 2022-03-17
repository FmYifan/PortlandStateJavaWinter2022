package edu.pdx.cs410j.yif;

import edu.pdx.cs410J.AirlineDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

public class PrettyPrinter implements AirlineDumper<Airline> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(Airline airline) {
        try (
                PrintWriter pw = new PrintWriter(this.writer, false)
        ) {
            pw.println("Airline Name: " + airline.getName());
            ArrayList<Flight> flightList = airline.getFlights();
            int size = flightList.size();
            for(int i = 0; i < size; i++){
                Flight flight = flightList.get(i);
                pw.println(flight.prettyPrint());
            }

            pw.flush();
        }
    }
}