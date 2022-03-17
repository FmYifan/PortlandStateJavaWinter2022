package edu.pdx.cs410j.yif;

import org.xml.sax.Parser;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;
  private Map<String, Airline> airlines = new HashMap<>();

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  //Parse one airline
  @Override
  public Airline parse() throws ParserException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {

      String airlineName = br.readLine();

      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }

      Airline airline = new Airline(airlineName);
      int flightNumber;
      Date departDate;
      Date arriveDate;
      String src;
      String dest;

      while(br.ready()){
        try{
          flightNumber = Integer.parseInt(br.readLine());
        } catch(NumberFormatException nfe){
          throw new IOException();
        }

        if(br.ready()) {
          src = br.readLine();
          int countLetters = 0;
          for (int j = 0; j < src.length(); j++) {
            if (Character.isLetter(src.charAt(j))) {
              countLetters++;
            }
          }
          if (src.chars().count() != 3 || countLetters != 3) {
            throw new IOException();
          }
        } else {
          throw new IOException();
        }
        src = src.toUpperCase();
        if(!AirportNames.getNamesMap().containsKey(src)){
          throw new IOException();
        }

        if(br.ready()) {
          String departTemp = br.readLine();
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
          dateFormat.setLenient(false);
          try {
            departDate = dateFormat.parse(departTemp.trim());
          } catch (ParseException e) {
            //e.printStackTrace();
            throw new IOException();
          }
        } else{
          throw new IOException();
        }

        if(br.ready()) {
          dest = br.readLine();
          int countLetters = 0;
          for (int j = 0; j < dest.length(); j++) {
            if (Character.isLetter(dest.charAt(j))) {
              countLetters++;
            }
          }
          if (dest.chars().count() != 3 || countLetters != 3) {
            throw new IOException();
          }
        } else{
          throw new IOException();
        }
        dest = dest.toUpperCase();
        if(!AirportNames.getNamesMap().containsKey(dest)){
          throw new IOException();
        }

        if(br.ready()) {
          String arriveTemp = br.readLine();
          DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
          dateFormat.setLenient(false);
          try {
            arriveDate = dateFormat.parse(arriveTemp.trim());
          } catch (ParseException e) {
            //e.printStackTrace();
            throw new IOException();
          }
        } else{
          throw new IOException();
        }

        if(arriveDate.before(departDate)){
          throw new IOException();
        }

        Flight flight = new Flight(flightNumber, src, departDate, dest, arriveDate);  // Refer to one of Dave's classes so that we can be sure it is on the classpath

        airline.addFlight(flight);

      }

      airline.sortFlights();

      return airline;

    } catch (IOException e) {
      System.err.print(e.getMessage());
      throw new ParserException("While parsing airline text", e);
    }
  }


  public Map<String, Airline> parseAll() throws ParserException {
    try (
            BufferedReader br = new BufferedReader(this.reader)
    ) {
      if(!br.ready()){
        return airlines;
      }

      while (br.ready()) {
        String airlineName = br.readLine();

        if (airlineName == null) {
          throw new ParserException("Missing airline name");
        }

        Airline airline = new Airline(airlineName);
        int flightNumber;
        Date departDate;
        Date arriveDate;
        String src;
        String dest;

        while (br.ready()) {
          //to see if it's delimiter
          String check = br.readLine();
          if(check.contains("/////")){
            break;
          }

          try {
            flightNumber = Integer.parseInt(check);
          } catch (NumberFormatException nfe) {
            throw new IOException();
          }

          if (br.ready()) {
            src = br.readLine();
            int countLetters = 0;
            for (int j = 0; j < src.length(); j++) {
              if (Character.isLetter(src.charAt(j))) {
                countLetters++;
              }
            }
            if (src.chars().count() != 3 || countLetters != 3) {
              throw new IOException();
            }
          } else {
            throw new IOException();
          }
          src = src.toUpperCase();
          if (!AirportNames.getNamesMap().containsKey(src)) {
            throw new IOException();
          }

          if (br.ready()) {
            String departTemp = br.readLine();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
            dateFormat.setLenient(false);
            try {
              departDate = dateFormat.parse(departTemp.trim());
            } catch (ParseException e) {
              //e.printStackTrace();
              throw new IOException();
            }
          } else {
            throw new IOException();
          }

          if (br.ready()) {
            dest = br.readLine();
            int countLetters = 0;
            for (int j = 0; j < dest.length(); j++) {
              if (Character.isLetter(dest.charAt(j))) {
                countLetters++;
              }
            }
            if (dest.chars().count() != 3 || countLetters != 3) {
              throw new IOException();
            }
          } else {
            throw new IOException();
          }
          dest = dest.toUpperCase();
          if (!AirportNames.getNamesMap().containsKey(dest)) {
            throw new IOException();
          }

          if (br.ready()) {
            String arriveTemp = br.readLine();
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
            dateFormat.setLenient(false);
            try {
              arriveDate = dateFormat.parse(arriveTemp.trim());
            } catch (ParseException e) {
              //e.printStackTrace();
              throw new IOException();
            }
          } else {
            throw new IOException();
          }

          if (arriveDate.before(departDate)) {
            throw new IOException();
          }

          Flight flight = new Flight(flightNumber, src, departDate, dest, arriveDate);  // Refer to one of Dave's classes so that we can be sure it is on the classpath

          airline.addFlight(flight);

        }
        airline.sortFlights();
        //If airline already exists, add new flights to it
        if (airlines.containsKey(airlineName)) {
          Airline airlineExist = airlines.get(airlineName);
          for (Flight flight : airline.getFlights()) {
            airlineExist.addFlight(flight);
          }
        } else {
          airlines.put(airlineName, airline);
        }
      }

      return airlines;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
