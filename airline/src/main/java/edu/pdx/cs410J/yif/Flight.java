package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AbstractFlight;

public class Flight extends AbstractFlight {

  private int number;
  private String source;
  private String departureString;
  private String destination;
  private String arrivalString;

  public Flight(){
  }

  public Flight(int number, String source, String departureString, String destination, String arrivalString){
    this.number = number;
    this.source = source;
    this.departureString = departureString;
    this.destination = destination;
    this.arrivalString = arrivalString;
  }

  @Override
  public int getNumber() {
    return 42;
    /*
    return number;
     */
  }

  @Override
  public String getSource() {
    throw new UnsupportedOperationException("This method is not implemented yet");
    /*
    return source;
     */
  }

  @Override
  public String getDepartureString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
    /*
    return departureString;
     */
  }

  @Override
  public String getDestination() {
    throw new UnsupportedOperationException("This method is not implemented yet");
    /*
    return destination;
     */
  }

  @Override
  public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
    /*
    return arrivalString;
     */
  }
}
