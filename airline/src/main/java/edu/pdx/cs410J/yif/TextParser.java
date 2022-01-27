package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

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
      String src;
      String departDate;
      String departTime;
      String dest;
      String arriveDate;
      String arriveTime;

      while(br.ready()){
        try{
          flightNumber = Integer.parseInt(br.readLine());
        } catch(NumberFormatException nfe){
          throw new ParserException("While parsing the file, the flight number is non-numeric", nfe);
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
            throw new ParserException("While parsing the file, the source airport code does not contain three letters.");
          }
        } else {
          throw new IOException("The flight number is missing");
        }

        if(br.ready()) {
          departDate = br.readLine();
          if (!departDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new ParserException("While parsing the file, the format of the departure date is incorrect.");
          }
        } else{
          throw new IOException("The departure date is missing.");
        }
        if(br.ready()){
          departTime = br.readLine();
          if (!departTime.matches("\\d{1,2}:\\d{1,2}")) {
            throw new ParserException("While parsing the file, the format of the departure time is incorrect.");
          }
        } else{
          throw new IOException("The departure time is missing");
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
            throw new ParserException("While parsing the file, the destination airport code does not contain three letters.");
          }
        } else{
          throw new IOException("The destination is missing.");
        }

        if(br.ready()) {
          arriveDate = br.readLine();
          if (!arriveDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            throw new ParserException("While parsing the file, the format of the arrival date is incorrect.");
          }
        } else{
          throw new IOException("The arrival date is missing");
        }

        if(br.ready()) {
          arriveTime = br.readLine();
          if (!arriveTime.matches("\\d{1,2}:\\d{1,2}")) {
            throw new ParserException("While parsing the file, the format of the arrival time is incorrect.");
          }
        } else{
          throw new IOException("The arrival time is missing");
        }

        Flight flight = new Flight(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);  // Refer to one of Dave's classes so that we can be sure it is on the classpath

        airline.addFlight(flight);

      }

      return airline;

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
