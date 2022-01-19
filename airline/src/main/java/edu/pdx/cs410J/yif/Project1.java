package edu.pdx.cs410J.yif;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The main class for the CS410J airline Project
 */
public class Project1 {

  public static void main(String[] args) throws IOException {

    //if no argument is provided, prompt the usage
    if(args.length == 0){
      //System.out.println("No command line arguments are passed in.");
      System.err.println("Missing command line arguments");
      System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>\n" +
              "args are (in this order):\n" +
              "airline \t The name of the airline\n" +
              "flightNumber \t The flight number\n" +
              "src \t Three-letter code of departure airport\n" +
              "depart \t Departure date and time (24-hour time)\n" +
              "dest \t Three-letter code of arrival airport\n" +
              "arrive \t Arrival date and time (24-hour time)\n" +
              "options are (options may appear in any order):\n" +
              "-print \t Prints a description of the new flight\n" +
              "-README \t Prints a README for this project and exits\n" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm");
      System.exit(1);
    }

    int flag = 0;
    int i;
    for(i = 0; i < args.length && args[i].startsWith("-"); i++){
      //For option -print, print the information for the flight later
      if(args[i].equals("-print")){
        flag = 1;
      }
      //For option -README, print the README and end the program
      else if(args[i].equals("-README")){
        InputStream readme = Project1.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line;
        while((line = reader.readLine()) != null){
          System.out.println(line);
        }
        System.exit(0);
      }
    }

    //Check if there are missing arguments or extraneous arguments
    if(args.length - i < 8){
      System.err.println("Something is missing from the command line.");
      System.exit(1);
    }

    if(args.length - i > 8){
      System.err.println("There are extraneous command line arguments.");
      System.exit(1);
    }

    String name;
    int flightNumber = 0;
    String src;
    String departDate;
    String departTime;
    String dest;
    String arriveDate;
    String arriveTime;

    //Error handling
    name = args[i++];

    try{
      flightNumber = Integer.parseInt(args[i++]);
    } catch(NumberFormatException nfe){
      System.err.println("The flight number is non-numeric.");
      System.exit(1);
    }

    src = args[i++];
    int countLetters = 0;
    for(int j = 0; j < src.length(); j++){
      if(Character.isLetter(src.charAt(j))){
        countLetters++;
      }
    }
    if(src.chars().count() != 3 || countLetters != 3){
      System.err.println("The source airport code does not contain three letters.");
      System.exit(1);
    }

    departDate = args[i++];
    if(!departDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")){
      System.err.println("The format of the departure date is incorrect.");
      System.exit(1);
    }
    departTime = args[i++];
    if(!departTime.matches("\\d{1,2}:\\d{1,2}")){
      System.err.println("The format of the departure time is incorrect.");
      System.exit(1);
    }

    dest = args[i++];
    countLetters = 0;
    for(int j = 0; j < dest.length(); j++){
      if(Character.isLetter(dest.charAt(j))){
        countLetters++;
      }
    }
    if(dest.chars().count() != 3 || countLetters != 3){
      System.err.println("The destination airport code does not contain three letters.");
      System.exit(1);
    }

    arriveDate = args[i++];
    if(!arriveDate.matches("\\d{1,2}/\\d{1,2}/\\d{4}")){
      System.err.println("The format of the arrival date is incorrect.");
      System.exit(1);
    }
    arriveTime = args[i];
    if(!arriveTime.matches("\\d{1,2}:\\d{1,2}")){
      System.err.println("The format of the arrival time is incorrect.");
      System.exit(1);
    }

    //Create a new flight and airline, and initialize them with the data provided
    Flight flight = new Flight(flightNumber, src, departDate, departTime, dest, arriveDate, arriveTime);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    Airline airline = new Airline(name);
    airline.addFlight(flight);

    //print the data for -print option
    if(flag == 1){
      System.out.println(flight);
    }

    System.exit(0);
  }

}