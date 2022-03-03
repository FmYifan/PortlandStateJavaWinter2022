package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String[] args) throws IOException {

        if(args.length == 0){
            System.err.println(MISSING_ARGS);
            System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project5 [options] <args>\n" +
                    "args are (in this order):\n" +
                    "airline \t The name of the airline\n" +
                    "flightNumber \t The flight number\n" +
                    "src \t Three-letter code of departure airport\n" +
                    "depart \t Departure date and time (am/pm)\n" +
                    "dest \t Three-letter code of arrival airport\n" +
                    "arrive \t Arrival date and time (am/pm)\n" +
                    "options are (options may appear in any order):\n" +
                    "-host hostname \t Host computer on which the server runs\n" +
                    "-port port \t Pretty print the airline’s flights to\n" +
                    "-search \t Search for flights\n" +
                    "-print \t Prints a description of the new flight\n" +
                    "-README \t Prints a README for this project and exits\n" +
                    "Date and time should be in the format: mm/dd/yyyy hh:mm");
            System.exit(1);
        }

        String hostName = null;
        String portString = null;


        int i;
        int printFlag = 0;
        int searchFlag = 0;

        String airlineName = null;
        String flightNumber = null;
        String src = null;
        String depart = null;
        String dest = null;
        String arrive = null;

        for(i = 0; i < args.length && args[i].startsWith("-"); i++){
            //For option -print, print the information for the flight later
            switch (args[i]) {
                case "-print":
                    printFlag = 1;
                    break;

                //For option -README, print the README and end the program
                case "-README":
                    InputStream readme = Project5.class.getResourceAsStream("README.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.exit(0);

                case "-host":
                    hostName = args[++i];
                    break;

                case "-port":
                    portString = args[++i];
                    break;

                case "-search":
                    searchFlag = 1;

                    break;

                default:
                    error("An unknown option was present.");
                    System.exit(1);
            }
        }

        if(searchFlag == 1) {
            for(; i < args.length; ++i) {
                if(airlineName == null) {
                    airlineName = args[i];
                }

                else if(src == null) {
                    src = args[i];
                    int countLetters = 0;
                    for (int j = 0; j < src.length(); j++) {
                        if (Character.isLetter(src.charAt(j))) {
                            countLetters++;
                        }
                    }
                    if (src.chars().count() != 3 || countLetters != 3) {
                        error("The source airport code does not contain three letters.");
                        System.exit(1);
                    }
                    src = src.toUpperCase();
                    if (!AirportNames.getNamesMap().containsKey(src)) {
                        error("The source airport code does not correspond to a known airport.");
                        System.exit(1);
                    }
                }

                else if(dest == null) {
                    dest = args[i];
                    int countLetters = 0;
                    for (int j = 0; j < dest.length(); j++) {
                        if (Character.isLetter(dest.charAt(j))) {
                            countLetters++;
                        }
                    }
                    if (dest.chars().count() != 3 || countLetters != 3) {
                        error("The destination airport code does not contain three letters.");
                        System.exit(1);
                    }
                    dest = dest.toUpperCase();
                    if (!AirportNames.getNamesMap().containsKey(dest)) {
                        error("The destination airport code does not correspond to a known airport.");
                        System.exit(1);
                    }
                }
            }

            if(airlineName == null || src == null || dest == null){
                error("There should be an airline name, a source airport, and a destination airport for the option \"-search\"");
                System.exit(1);
            }

        }



        if(searchFlag == 0) {
            StringBuilder sb;
            for (; i < args.length; i++) {
                if (airlineName == null) {
                    airlineName = args[i];
                } else if (flightNumber == null) {
                    flightNumber = args[i];
                } else if (src == null) {
                    src = args[i];
                } else if (depart == null) {
                    sb = new StringBuilder();
                    if(i + 2 < args.length) {
                        for (int j = 0; j < 3; j++) {
                            sb.append(args[i++] + " ");
                        }
                        i--;
                        depart = sb.toString();
                    } else {
                        error("No enough arguments for departure date");
                    }
                } else if (dest == null) {
                    dest = args[i];
                } else if (arrive == null) {
                    sb = new StringBuilder();
                    if(i + 2 < args.length) {
                        for (int j = 0; j < 3; j++) {
                            sb.append(args[i++] + " ");
                        }
                        i--;
                        arrive = sb.toString();
                    } else {
                        error("No enough arguments for arrival date");
                    }
                }
            }
        }

        if (hostName == null) {
            error("Missing host name");
            return;
        }
        if (portString == null) {
            error("Missing port");
            return;
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            error("Port \"" + portString + "\" must be an integer");
            return;
        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        Airline airline;
        Flight flight;
        try {
            if (searchFlag == 1) {
                // Print all flights that originate at the src airport and terminate at the dest airport
                try {
                    airline = client.getSearchedSrcDestAirline(airlineName, src, dest);
                    if(!airline.prettyPrintAllFlights()){
                        System.out.println("There is no direct flight between the specified airports for this airline.");
                    }
                } catch (HttpRequestHelper.RestException e){
                    error("While contacting server: " + e.getHttpStatusCode());
                } catch (IOException e){
                    error("While contacting server: " + e.getMessage());
                }

            } else if (airlineName != null && flightNumber != null && src != null && depart != null && dest != null && arrive != null) {
                //add one flight to the servlet
                flight = new Flight(flightNumber, src, depart, dest, arrive);
                client.addFlightToServlet(airlineName, flight);
                if(printFlag == 1){
                    System.out.println(flight);
                }

            } else if (airlineName != null && flightNumber == null && src == null && depart == null && dest == null && arrive == null){
                //pretty print all flights in an airline
                try {
                    airline = client.getAirline(airlineName);
                    if (!airline.prettyPrintAllFlights()) {
                        System.out.println("There is no flight in this airline.");
                    }
                } catch (HttpRequestHelper.RestException ex){
                    error("While contacting server, the airline cannot be found");
                    return;
                }

            } else if(airlineName == null){
                error("An airline name needs to be specified.");
            } else{
                error("Something is missing from the command line.");
            }

        } catch (IOException | ParserException | HttpRequestHelper.RestException ex ) {
            error("While contacting server: An error happened");
            return;
        }

        System.exit(0);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

}