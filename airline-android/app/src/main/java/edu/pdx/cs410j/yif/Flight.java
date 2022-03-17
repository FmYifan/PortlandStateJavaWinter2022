package edu.pdx.cs410j.yif;

import edu.pdx.cs410J.AbstractFlight;
import edu.pdx.cs410J.AirportNames;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The class that extends AbstractFlight. A Flight contains a flight number, a source airport, a departure date, a departure time,
 * a destination airport, an arrival date, an arrival time.
 */
public class Flight extends AbstractFlight implements Comparable<Flight>{

  private int number;
  private String source;
  private Date departDate;
  private String destination;
  private Date arrivalDate;

  /**
   * The default constructor
   */
  public Flight(){
    number = 0;
  }

  /**
   *  The constructor with arguments that initialize the fields of this class
   * @param number  the flight number
   * @param source  the source airport
   * @param departDate   the departure date
   * @param destination   the destination airport
   * @param arrivalDate   the arrival date
   */
  public Flight(int number, String source, /*String departureDate, String departureTime,*/Date departDate, String destination, /*String arrivalDate, String arrivalTime*/Date arrivalDate){
    this.number = number;
    this.source = source;
    this.departDate = departDate;
    this.destination = destination;
    this.arrivalDate = arrivalDate;
  }

  public int compareTo(Flight flight){
    int test = source.compareToIgnoreCase(flight.source);
    if(test > 0){
      return 1;
    }
    else if(test == 0){
      int test2 = departDate.compareTo(flight.departDate);
      if(test2 > 0){
        return 1;
      }
      else if(test2 == 0){
        return 0;
      }
      else{
        return -1;
      }
    }
    else{
      return -1;
    }
  }

  /**
   * get the number of the flight
   * @return  the flight number
   */
  @Override
  public int getNumber() {
    return number;
  }

  /**
   * get the source airport of the flight
   * @return  the source airport
   */
  @Override
  public String getSource() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return source;
  }

  /**
   * get the departure date and time of the flight using String
   * @return  the departure date and time using String
   */
  @Override
  public String getDepartureString() {
    if(departDate != null) {
      int f = DateFormat.SHORT;
      DateFormat df = DateFormat.getDateTimeInstance(f, f, Locale.US);
      return df.format(departDate);
    }
    return null;
  }

  /**
   * get the departure date of the flight
   * @return the departure date
   */
  @Override
  public Date getDeparture(){
    return departDate;
  }

  /**
   * get the destination airport of the flight
   * @return the destination airport
   */
  @Override
  public String getDestination() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return destination;
  }

  /**
   * get the arrival date and time of the flight using String
   * @return  the arrival date and time using String
   */
  @Override
  public String getArrivalString() {
    if(arrivalDate != null) {
      int f = DateFormat.SHORT;
      DateFormat df = DateFormat.getDateTimeInstance(f, f, Locale.US);
      return df.format(arrivalDate);
    }
    return null;
  }

  /**
   * get the arrival date of the flight
   * @return the arrival date
   */
  @Override
  public Date getArrival(){
    return arrivalDate;
  }

  /**
   * Pretty print the flight's information
   * @return the pretty text of the flight
   */
  public String prettyPrint(){
    long duration = arrivalDate.getTime() - departDate.getTime();
    long diffMin = (duration / (1000 * 60)) % 60;
    long diffHr = (duration / (1000 * 60 * 60)) % 24;
    long diffDay = (duration / (1000 * 60 * 60 * 24)) % 365;
    String length;
    if(diffDay == 0){
      length = "The duration of the flight " + this.getNumber() + " is " + diffHr + " hours " + diffMin + " minutes.";
    } else{
      length = "The duration of the flight " + this.getNumber() + " is " + diffDay + " day " + diffHr + " hours " + diffMin + " minutes.";
    }

    int f = DateFormat.SHORT;
    DateFormat df = DateFormat.getDateTimeInstance(f,f);
    return "Flight " + this.getNumber() + " departs from " + AirportNames.getName(source) + " at " + this.getDepartureString()
            + " arrives at " + AirportNames.getName(destination) + " at " + this.getArrivalString() + '\n'
            + length;
  }

}
