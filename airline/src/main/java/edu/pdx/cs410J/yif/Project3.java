package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * The main class for the CS410J airline Project 3
 */
public class Project3 {

  /**
   * The main method
   * @param args the name of the airline and the data for the new flight added
   * @throws IOException may have IOException during the parsing or dumping process
   */
  public static void main(String[] args) throws IOException {

    //if no argument is provided, prompt the usage
    if(args.length == 0){
      //System.out.println("No command line arguments are passed in.");
      System.err.println("Missing command line arguments");
      System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n" +
              "args are (in this order):\n" +
              "airline \t The name of the airline\n" +
              "flightNumber \t The flight number\n" +
              "src \t Three-letter code of departure airport\n" +
              "depart \t Departure date and time (am/pm)\n" +
              "dest \t Three-letter code of arrival airport\n" +
              "arrive \t Arrival date and time (am/pm)\n" +
              "options are (options may appear in any order):\n" +
              "-pretty file Pretty print the airline’s flights to\n" +
                      "a text file or standard out (file -)\n" +
              "-textFile file  Where to read/write the airline info\n" +
              "-print \t Prints a description of the new flight\n" +
              "-README \t Prints a README for this project and exits\n" +
              "Date and time should be in the format: mm/dd/yyyy hh:mm");
      System.exit(1);
    }

    int printFlag = 0;
    int fileFlag = 0;
    int newFileFlag = 0;
    int prettyStd = 0;
    int prettyFlag = 0;
    File file = null;
    File prettyFile = null;
    int i;
    for(i = 0; i < args.length && args[i].startsWith("-"); i++){
      //For option -print, print the information for the flight later
      switch (args[i]) {
        case "-print":
          printFlag = 1;
          break;

        //For option -README, print the README and end the program
        case "-README":
          InputStream readme = Project3.class.getResourceAsStream("README.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String line;
          while ((line = reader.readLine()) != null) {
            System.out.println(line);
          }
          System.exit(0);

          //For option -textFile, load from and write back to the file specified
        case "-textFile":
          fileFlag = 1;
          Path temp = Paths.get(args[++i]);
          if(temp.isAbsolute()){
            file = new File(String.valueOf(temp));
          }
          else {
            File curDir = new File(System.getProperty("user.dir"));
            if(String.valueOf(temp).startsWith("/")){
              file = new File(curDir.getAbsolutePath() + temp);
            } else {
              file = new File(curDir.getAbsolutePath() + "/" + temp);
            }
          }
          if(file.createNewFile()){
            newFileFlag = 1;
          }
          break;

          //For option -pretty, pretty print the content
        case "-pretty":
          String prettyOpt = args[++i];
          prettyFlag = 1;
          if(prettyOpt.equals("-")){
            prettyStd = 1;
          }
          else{
            Path prettyPath = Paths.get(prettyOpt.trim());
            if(prettyPath.isAbsolute()){
              prettyFile = new File(String.valueOf(prettyPath));
            }
            else {
              File curDir = new File(System.getProperty("user.dir"));
              if(String.valueOf(prettyPath).startsWith("/")){
                prettyFile = new File(curDir.getAbsolutePath() + prettyPath);
              } else {
                prettyFile = new File(curDir.getAbsolutePath() + "/" + prettyPath);
              }
            }
            prettyFile.createNewFile();
          }

          break;

        default:
          System.err.println("An unknown option was present.");
          System.exit(1);
      }

    }

    //Check if there are missing arguments or extraneous arguments
    if(args.length - i < 10){
      System.err.println("Something is missing from the command line.");
      System.exit(1);
    }

    if(args.length - i > 10){
      System.err.println("There are extraneous command line arguments.");
      System.exit(1);
    }

    String name;
    int flightNumber = 0;
    String src;
    String dest;

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
    src = src.toUpperCase();
    if(!AirportNames.getNamesMap().containsKey(src)){
      System.err.println("The source airport code does not correspond to a known airport.");
      System.exit(1);
    }

    Date departDate = null;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
    dateFormat.setLenient(false);
    StringBuilder sb = new StringBuilder();
    for(int j = 0; j < 3; j++){
      sb.append(args[i++] + " ");
    }
    try {
      departDate = dateFormat.parse(sb.toString().trim());
    } catch (ParseException e) {
      //e.printStackTrace();
      System.err.println("The format of the departure date is incorrect.");
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
    dest = dest.toUpperCase();
    if(!AirportNames.getNamesMap().containsKey(dest)){
      System.err.println("The destination airport code does not correspond to a known airport.");
      System.exit(1);
    }



    Date arriveDate = null;
    sb = new StringBuilder();
    for(int j = 0; j < 3; j++){
      sb.append(args[i++] + " ");
    }
    try {
      arriveDate = dateFormat.parse(sb.toString().trim());
    } catch (ParseException e) {
      System.err.println("The format of the arrival date is incorrect.");
      System.exit(1);
    }

    if(arriveDate.before(departDate)){
      System.err.println("This flight's arrival time is before its departure time.");
      System.exit(1);
    }


    //Create a new flight and airline, and initialize them with the data provided
    Flight flight = new Flight(flightNumber, src, departDate, dest, arriveDate);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    Airline airline = null;

    //print the data for -print option
    if(printFlag == 1){
      System.out.println(flight);
    }

    //With -textFile option
    if(fileFlag == 1){
      TextParser parser = new TextParser(new FileReader(file));
      //If it's not a new file
      if(newFileFlag != 1) {
        try {
          airline = parser.parse();
        } catch (ParserException e) {
          System.err.println("Have an error during parsing the file. The text file cannot be parsed.");
          System.exit(1);
        }
        if (airline != null) {
          if (!Objects.equals(airline.getName(), name)) {
            System.err.println("The airline name loaded from the file specified is not the same as the name passed through command line.");
            System.exit(1);
          }
        }
      }
      //If it's a new file
      else{
        airline = new Airline(name);
      }
      TextDumper dumper = new TextDumper(new FileWriter(file));
      airline.addFlight(flight);
      airline.sortFlights();
      dumper.dump(airline);
    }

    //Without -textFile option
    else {
      airline = new Airline(name);
      airline.addFlight(flight);
    }

    if(prettyFlag == 1){
      if(prettyStd == 1){
        System.out.println("Airline Name: " + airline.getName());
        ArrayList<Flight> flightList = airline.getFlights();
        for (Flight toPrint : flightList) {
          System.out.println(toPrint.prettyPrint());
        }
      }
      else {
        PrettyPrinter dumper = new PrettyPrinter(new FileWriter(prettyFile, false));
        dumper.dump(airline);
      }
    }

    System.exit(0);
  }

}