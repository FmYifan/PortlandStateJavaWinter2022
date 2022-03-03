package edu.pdx.cs410J.yif;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AirlineServlet extends HttpServlet {
    static final String AIRLINE_NAME_PARAMETER = "airline";
    static final String FLIGHT_NUMBER_PARAMETER = "flightNumber";
    static final String SOURCE_PARAMETER = "src";
    static final String DEPARTURE_PARAMETER = "depart";
    static final String DESTINATION_PARAMETER = "dest";
    static final String ARRIVAL_PARAMETER = "arrive";

    private final Map<String, Airline> airlines = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/xml; charset=UTF-8" );

        String airlineName = getParameter( AIRLINE_NAME_PARAMETER, request );
        String source = getParameter( SOURCE_PARAMETER, request );
        String destination = getParameter( DESTINATION_PARAMETER, request );
        String flightNumber = getParameter( FLIGHT_NUMBER_PARAMETER, request );
        String departure = getParameter( DEPARTURE_PARAMETER, request );
        String arrival = getParameter( ARRIVAL_PARAMETER, request );

        if (airlineName == null) {
            missingRequiredParameter(response,AIRLINE_NAME_PARAMETER);
        } else if(source != null && destination != null && flightNumber == null && departure == null && arrival == null) {

            int countLetters = 0;
            for (int j = 0; j < source.length(); j++) {
                if (Character.isLetter(source.charAt(j))) {
                    countLetters++;
                }
            }
            if (source.chars().count() != 3 || countLetters != 3) {
                throw new IOException("The source airport code does not contain three letters.");
            }
            source = source.toUpperCase();
            if (!AirportNames.getNamesMap().containsKey(source)) {
                throw new IOException("The source airport code does not correspond to a known airport.");
            }

            countLetters = 0;
            for (int j = 0; j < destination.length(); j++) {
                if (Character.isLetter(destination.charAt(j))) {
                    countLetters++;
                }
            }
            if (destination.chars().count() != 3 || countLetters != 3) {
                throw new IOException("The destination airport code does not contain three letters.");
            }
            destination = destination.toUpperCase();
            if (!AirportNames.getNamesMap().containsKey(destination)) {
                throw new IOException("The destination airport code does not correspond to a known airport.");
            }

            dumpSearchAirline(airlineName, source, destination, response);

        } else{
            dumpAirline(airlineName, response);
        }

    }

    /**
     * dump the airline which contain all of the flights depart at the source airport and arrive at the destination airport to the http response
     * @param airlineName the name of the airline which is searched
     * @param source the source airport
     * @param destination the destination airport
     * @param response the http response
     */
    protected void dumpSearchAirline(String airlineName, String source, String destination, HttpServletResponse response) throws IOException
    {
        source = source.toUpperCase();
        destination = destination.toUpperCase();
        Airline to_search = this.airlines.get(airlineName);
        if (to_search == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Airline to_dump = new Airline(airlineName);
        for(Flight flight : to_search.getFlights()){
            if(flight.getSource().equals(source) && flight.getDestination().equals(destination)){
                to_dump.addFlight(flight);
            }
        }

        PrintWriter pw = response.getWriter();
        XmlDumper dumper= new XmlDumper(pw);
        dumper.dump(to_dump);

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes all flights of the given airline to the HTTP response.
     *
     * The text of the message is formatted with {@link XmlDumper}
     */
    private void dumpAirline(String airlineName, HttpServletResponse response) throws IOException {
        Airline airline = this.airlines.get(airlineName);

        if (airline == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter pw = response.getWriter();
            XmlDumper dumper = new XmlDumper(pw);
            dumper.dump(airline);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String airlineName = getParameter(AIRLINE_NAME_PARAMETER, request );
        if (airlineName == null) {
            missingRequiredParameter(response, AIRLINE_NAME_PARAMETER);
            return;
        }

        String flightNumberString = getParameter(FLIGHT_NUMBER_PARAMETER, request );
        if ( flightNumberString == null) {
            missingRequiredParameter(response, FLIGHT_NUMBER_PARAMETER);
            return;
        }

        String sourceString = getParameter( SOURCE_PARAMETER, request );
        if ( sourceString == null) {
            missingRequiredParameter(response, SOURCE_PARAMETER);
            return;
        }
        String departureString = getParameter( DEPARTURE_PARAMETER, request );
        if ( departureString == null) {
            missingRequiredParameter(response, DEPARTURE_PARAMETER);
            return;
        }
        String destinationString = getParameter( DESTINATION_PARAMETER, request );
        if ( destinationString == null) {
            missingRequiredParameter(response, DESTINATION_PARAMETER);
            return;
        }
        String arrivalString = getParameter( ARRIVAL_PARAMETER, request );
        if ( arrivalString == null) {
            missingRequiredParameter(response, ARRIVAL_PARAMETER);
            return;
        }

        Flight flight = new Flight(flightNumberString, sourceString, departureString, destinationString, arrivalString);
        Airline airline = this.airlines.get(airlineName);
        int newFlag = 0;
        if (airline == null) {
            airline = new Airline(airlineName);
            newFlag = 1;
        }

        airline.addFlight(flight);

        if(newFlag == 1){
            airlines.put(airlineName, airline);
        }

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.airlines.clear();

        PrintWriter pw = response.getWriter();
        if(pw != null) {
            pw.println("All airlines are deleted.");
            pw.flush();
        }

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    Airline getAirline(String airlineName) {
        return this.airlines.get(airlineName);
    }
}
