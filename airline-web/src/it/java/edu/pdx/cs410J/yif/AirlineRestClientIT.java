package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  /**
   * Test if client can remove all airlines in the servlet
   * @throws IOException
   */
  @Test
  void test0RemoveAllAirlines() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    client.removeAllAirlineEntries();
  }

  /**
   * Create a flight in a new airline in the servlet
   * @throws IOException
   * @throws ParserException
   */
  @Test
  void test1CreateFlightInNewAirline() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();

    String airlineName = "TEST Airlines";
    String flightNumber = "123";
    String src = "PDX";
    String departure = "05/04/2022 09:08 am";
    String dest = "LAX";
    String arrival = "05/05/2022 09:09 pm";

    client.removeAllAirlineEntries();

    Flight flight = new Flight(flightNumber, src, departure, dest, arrival);
    client.addFlightToServlet(airlineName, flight);

    Airline toVerify = client.getAirline(airlineName);
    assertThat(toVerify.getFlights().get(0).getNumber(), equalTo(Integer.parseInt(flightNumber)));
  }

  /**
   * Get a specified airline from the servlet
   * @throws IOException
   * @throws ParserException
   */
  @Test
  void test2GetWholeAirline() throws IOException, ParserException{
    AirlineRestClient client = newAirlineRestClient();

    String airlineName = "TEST Airlines";
    String flightNumber = "123";
    String src = "PDX";
    String departure = "05/04/2022 09:08 am";
    String dest = "LAX";
    String arrival = "05/05/2022 09:09 pm";

    client.removeAllAirlineEntries();

    Flight flight = new Flight(flightNumber, src, departure, dest, arrival);
    client.addFlightToServlet(airlineName, flight);

    Airline toVerify = client.getAirline(airlineName);
    assertThat(toVerify.getName(), equalTo(airlineName));
  }


  /**
   * Get a specified airline from the servlet
   * @throws IOException
   * @throws ParserException
   */
  @Test
  void test3GetSrcDestFlights() throws IOException, ParserException{
    AirlineRestClient client = newAirlineRestClient();

    String airlineName = "TEST Airlines";
    String flightNumber1 = "123";
    String flightNumber2 = "234";
    String flightNumber3 = "345";
    String flightNumber4 = "459";
    String src = "PDX";
    String departure = "05/04/2022 09:08 am";
    String dest = "LAX";
    String arrival = "05/05/2022 09:09 pm";

    client.removeAllAirlineEntries();

    Flight flight1 = new Flight(flightNumber1, src, departure, dest, arrival);
    Flight flight2 = new Flight(flightNumber2, src, departure, dest, arrival);
    Flight flight3 = new Flight(flightNumber3, src, departure, dest, arrival);
    Flight flight4 = new Flight(flightNumber4, dest, departure, src, arrival);
    client.addFlightToServlet(airlineName, flight1);
    client.addFlightToServlet(airlineName, flight2);
    client.addFlightToServlet(airlineName, flight3);
    client.addFlightToServlet(airlineName, flight4);

    Airline toVerify = client.getSearchedSrcDestAirline(airlineName, src, dest);
    assertThat(toVerify.getFlights().get(0).getNumber(), equalTo(Integer.parseInt(flightNumber1)));
    assertThat(toVerify.getFlights().get(1).getNumber(), equalTo(Integer.parseInt(flightNumber2)));
    assertThat(toVerify.getFlights().get(2).getNumber(), equalTo(Integer.parseInt(flightNumber3)));
  }
}
