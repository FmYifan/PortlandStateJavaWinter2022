package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AbstractFlight;

/**
 * The class that extends AbstractFlight. A Flight contains a flight number, a source airport, a departure date, a departure time,
 * a destination airport, an arrival date, an arrival time.
 */
public class Flight extends AbstractFlight {

  private int number;
  private String source;
  private String departureDate;
  private String departureTime;
  private String destination;
  private String arrivalDate;
  private String arrivalTime;

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
   * @param departureDate   the departure date
   * @param departureTime   the departure time
   * @param destination   the destination airport
   * @param arrivalDate   the arrival date
   * @param arrivalTime   the arrival time
   */
  public Flight(int number, String source, String departureDate, String departureTime, String destination, String arrivalDate, String arrivalTime){
    this.number = number;
    this.source = source;
    this.departureDate = departureDate;
    this.departureTime = departureTime;
    this.destination = destination;
    this.arrivalDate = arrivalDate;
    this.arrivalTime = arrivalTime;
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
   * get the departure date and time of the flight
   * @return  the departure date and time
   */
  @Override
  public String getDepartureString() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    if(departureDate == null || departureTime == null){
      return null;
    }
    return departureDate + " " + departureTime;
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
   * get the arrival date and time of the flight
   * @return  the arrival date and time
   */
  @Override
  public String getArrivalString() {
    if(arrivalDate == null || arrivalTime == null){
      return null;
    }
    return arrivalDate + " " + arrivalTime;
  }
}
