package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for XmlDumper
 */
public class XmlDumperTest {

    /**
     * test if airline and flight can be written into a file
     * @param tempDir the temp directory
     * @throws IOException may have IOException
     * @throws ParserException may have ParserException
     */
    @Test
    void canParseAirlineFlightWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
        String airlineName = "Test Airline";
        Airline airline = new Airline(airlineName);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
        String f1 = "05/04/2022 09:08 AM";
        String f2 = "05/4/2022 9:09 pm";
        Date depart = null;
        Date arrival = null;
        try {
            depart = df.parse(f1.trim());
            arrival = df.parse(f2.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Flight flight = new Flight(3, "PDX", depart, "LAX", arrival);
        airline.addFlight(flight);

        URL resource = getClass().getResource("dumperTest.xml");
        assertThat(resource, notNullValue());

        File file = new File(resource.getPath());

        XmlDumper dumper = new XmlDumper(file);
        dumper.dump(airline);

        XmlParser parser = new XmlParser(file);
        Airline read = parser.parse();
        assertThat(read.getName(), equalTo(airlineName));
        assertThat(read.getFlights().get(0).toString(), equalTo("Flight 3 departs PDX at 5/4/22, 9:08 AM arrives LAX at 5/4/22, 9:09 PM"));
    }

    /**
     * test if airline and multiple flights can be written into a file
     * @param tempDir the temp directory
     * @throws IOException may have IOException
     * @throws ParserException may have ParserException
     */
    @Test
    void canParseMultipleFlightsWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
        String airlineName = "Test Airline";
        Airline airline = new Airline(airlineName);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
        String f1 = "05/04/2022 09:08 am";
        String f2 = "05/05/2022 09:09 pm";
        Date depart = null;
        Date arrival = null;
        try {
            depart = df.parse(f1.trim());
            arrival = df.parse(f2.trim());
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
        Flight flight1 = new Flight(3, "PDX", depart, "LAX", arrival);
        Flight flight2 = new Flight(3, "ABE", depart, "LAX", arrival);
        airline.addFlight(flight1);
        airline.addFlight(flight2);


        URL resource = getClass().getResource("dumperTest2.xml");
        assertThat(resource, notNullValue());

        File file = new File(resource.getPath());

        XmlDumper dumper = new XmlDumper(file);
        dumper.dump(airline);

        XmlParser parser = new XmlParser(file);
        Airline read = parser.parse();
        assertThat(read.getName(), equalTo(airlineName));
        assertThat(read.getFlights().get(0).toString(), equalTo("Flight 3 departs ABE at 5/4/22, 9:08 AM arrives LAX at 5/5/22, 9:09 PM"));
        assertThat(read.getFlights().get(1).toString(), equalTo("Flight 3 departs PDX at 5/4/22, 9:08 AM arrives LAX at 5/5/22, 9:09 PM"));
    }
}
