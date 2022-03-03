package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

  /**
   * Returns the specified Airline from the servlet
   */
  public Airline getAirline(String airlineName) throws IOException, ParserException {
    Response response = get(this.url, Map.of("airline", airlineName));
    throwExceptionIfNotOkayHttpStatus(response);

    XmlParser parser = new XmlParser(new StringReader(response.getContent()));
    return parser.parse();
  }

  /**
   * Returns the airline that contains all flights that originate at the src airport and terminate at the dest airport
   */
  public Airline getSearchedSrcDestAirline(String airlineName, String source, String destination) throws IOException, ParserException {
    Response response = get(this.url, Map.of("airline", airlineName, "src", source, "dest", destination));
    throwExceptionIfNotOkayHttpStatus(response);

    XmlParser parser = new XmlParser(new StringReader(response.getContent()));
    return parser.parse();
  }

  public void addFlightToServlet(String airlineName, Flight flight) throws IOException {
    Response response = post(this.url, Map.of("airline", airlineName, "flightNumber", Integer.toString(flight.getNumber()),
                                            "src", flight.getSource(), "depart", flight.getSimpleDepartureString(),
                                            "dest", flight.getDestination(), "arrive", flight.getSimpleArrivalString()));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  public void removeAllAirlineEntries() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }

}
