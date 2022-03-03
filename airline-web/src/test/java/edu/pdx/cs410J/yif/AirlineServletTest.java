package edu.pdx.cs410J.yif;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
class AirlineServletTest {

  /**
   * Missing Airline Name should set a failed status
   * @throws IOException
   */
  @Test
  void missingAirlineNameReturnsPreconditionFailedStatus() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter(AirlineServlet.AIRLINE_NAME_PARAMETER));
  }

  /**
   * Test dumping all flights in an airline
   * @throws IOException
   */
  @Test
  void testDumpAllFlights() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "TEST Airlines";
    int flightNumber = 123;
    String src = "PDX";
    String departure = "05/04/2022 09:08 am";
    String dest = "LAX";
    String arrival = "05/05/2022 09:09 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(String.valueOf(flightNumber));
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEPARTURE_PARAMETER)).thenReturn(departure);
    when(request.getParameter(AirlineServlet.DESTINATION_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.ARRIVAL_PARAMETER)).thenReturn(arrival);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);
  }

  /**
   * Test dumping all flights in an airline
   * @throws IOException
   */
  @Test
  void testWrongAirlineName() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "TEST Airlines";
    int flightNumber = 123;
    String src = "PDX";
    String departure = "05/04/2022 09:08 am";
    String dest = "LAX";
    String arrival = "05/05/2022 09:09 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(String.valueOf(flightNumber));
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEPARTURE_PARAMETER)).thenReturn(departure);
    when(request.getParameter(AirlineServlet.DESTINATION_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.ARRIVAL_PARAMETER)).thenReturn(arrival);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doPost(request, response);

    HttpServletRequest request2 = mock(HttpServletRequest.class);
    when(request2.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn("Wrong");

    HttpServletResponse response2 = mock(HttpServletResponse.class);
    servlet.doGet(request2, response2);
    verify(response2).setStatus(eq(HttpServletResponse.SC_NOT_FOUND));
  }


  /**
   * Test dumping flights that originate from source and arrive at destination
   * @throws IOException
   */
  @Test
  void testDumpSearchedFlights() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "TEST Airlines";
    String src = "PDX";
    String dest = "LAX";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DESTINATION_PARAMETER)).thenReturn(dest);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doGet(request, response);
  }


  /**
   * Test if a flight can be successfully added to the servlet
   * @throws IOException
   */
  @Test
  void addOneFlightToAnAirline() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    String airlineName = "TEST Airlines";
    int flightNumber = 123;
    String src = "PDX";
    String departure = "05/04/2022 09:08 am";
    String dest = "LAX";
    String arrival = "05/05/2022 09:09 pm";

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(AirlineServlet.AIRLINE_NAME_PARAMETER)).thenReturn(airlineName);
    when(request.getParameter(AirlineServlet.FLIGHT_NUMBER_PARAMETER)).thenReturn(String.valueOf(flightNumber));
    when(request.getParameter(AirlineServlet.SOURCE_PARAMETER)).thenReturn(src);
    when(request.getParameter(AirlineServlet.DEPARTURE_PARAMETER)).thenReturn(departure);
    when(request.getParameter(AirlineServlet.DESTINATION_PARAMETER)).thenReturn(dest);
    when(request.getParameter(AirlineServlet.ARRIVAL_PARAMETER)).thenReturn(arrival);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doPost(request, response);

    verify(response).setStatus(eq(HttpServletResponse.SC_OK));

    Airline airline = servlet.getAirline(airlineName);
    assertThat(airline, notNullValue());
    Collection<Flight> flights = airline.getFlights();
    assertThat(flights, hasSize(1));
    assertThat(flights.iterator().next().getNumber(), equalTo(flightNumber));

    servlet.doDelete(request, response);
  }

  /**
   * Test if HTTP DELETE Method can run successfully
   * @throws IOException
   */
  @Test
  void testDelete() throws IOException {
    AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doDelete(request, response);

    verify(response).setStatus(eq(HttpServletResponse.SC_OK));
  }
}
