package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    /**
     * Invokes the main method of {@link Project5} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project5.class, args );
    }

    /**
     * remove all airlines
     * @throws IOException
     */
    @Test
    void test0RemoveAllMappings() throws IOException {
      AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllAirlineEntries();
    }

    /**
     * No arguments provided issues an error
     */
    @Test
    void test1NoArgumentsProvided() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project5.MISSING_ARGS));
    }

    /**
     * No airline name provided issues an error
     */
    @Test
    void test2NoAirlineNameArguments() {
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("** An airline name needs to be specified."));
    }

    /**
     * Missing arguments will issue an error
      */
    @Test
    void test3MissingArguments() {
        String airlineName = "TEST Airlines";
        String flightNumber = "123";
        String src = "PDX";
        String departure = "05/04/2022 09:08 am";
        String dest = "LAX";
        String arrival = "05/05/2022 09:09 pm";
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, airlineName, flightNumber, src);
        assertThat(result.getExitCode(), equalTo(1));
        String err = result.getTextWrittenToStandardError();
        assertThat(err, containsString("** Something is missing from the command line."));
    }

    /**
     * Not enough departure arguments will issue an error
     */
    @Test
    void test4NotEnoughDepart() {
        String airlineName = "TEST Airlines";
        String flightNumber = "123";
        String src = "PDX";
        String departure = "05/04/2022 09:08 am";
        String dest = "LAX";
        String arrival = "05/05/2022 09:09 pm";
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, airlineName, flightNumber, src, departure);
        assertThat(result.getExitCode(), equalTo(1));
        String err = result.getTextWrittenToStandardError();
        assertThat(err, containsString("No enough arguments for departure date"));
    }

    /**
     * Test not enough arrival arguments will issue an error
     */
    @Test
    void test5AddFlight() {
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10");
        assertThat(result.getExitCode(), equalTo(1));
        String err = result.getTextWrittenToStandardError();
        assertThat(err, containsString("No enough arguments for arrival date"));
    }

    /**
     * Test if adding a flight works well
     */
    @Test
    void test6AddFlight() {
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests that entering a non-numeric flight number issues an error
     */
    @Test
    public void testNonNumericFlightNumber(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "Test", "pdx", "1/1/2022", "12:0", "am", "pdx", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The flight number is non-numeric."));
    }

    /**
     * Tests that entering a non-three-letters source airport code issues an error
     */
    @Test
    public void testNonThreeLettersSrc(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "410", "sdfk", "1/1/2022", "12:00", "am", "des", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The source airport code does not contain three letters."));
    }

    /**
     * Tests that entering an unknown source airport code issues an error
     */
    @Test
    public void testUnknownSrc(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "410", "abc", "1/1/2022", "12:00", "am", "des", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The source airport code does not correspond to a known airport."));
    }

    /**
     * Tests that entering an incorrect departure date issues an error
     */
    @Test
    public void testIncorrectDepartDate(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "-print", "test airline", "410", "pdx", "100/1/2022", "12:00", "pm", "des", "1/2/2022", "12:00", "pm");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The format of the departure date is incorrect."));
    }

    /**
     * Tests that entering a non-three-letters destination airport code issues an error
     */
    @Test
    public void testNonThreeLettersDest(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "D2L", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The destination airport code does not contain three letters."));
    }

    /**
     * Tests that entering an unknown destination airport code issues an error
     */
    @Test
    public void testUnknownDest(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "410", "pdx", "1/1/2022", "12:00", "am", "abc", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The destination airport code does not correspond to a known airport."));
    }

    /**
     * Tests that entering an incorrect arrival date issues an error
     */
    @Test
    public void testIncorrectArrivalDate(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "410", "pdx", "10/19/2022", "12:0", "am", "pdx", "1/200/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The format of the arrival date is incorrect."));
    }

    /**
     * Tests that entering an earlier arrival date issues an error
     */
    @Test
    public void testEarlierArrivalDate(){
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "test airline", "410", "pdx", "10/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:00", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("This flight's arrival time is before its departure time."));
    }

    /**
     * Tests that missing the host name issues an error
     */
    @Test
    public void testMissingHost(){
        MainMethodResult result = invokeMain( "-port", PORT);
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Missing host name"));
    }

    /**
     * Tests that missing the port issues an error
     */
    @Test
    public void testMissingPort(){
        MainMethodResult result = invokeMain( "-host", HOSTNAME);
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Missing port"));
    }

    /**
     * Tests that non-numeric port issues an error
     */
    @Test
    public void testNonNumericPort(){
        MainMethodResult result = invokeMain( "-host", HOSTNAME, "-port", "nonNumeric", "TestAirline");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Port \"nonNumeric\" must be an integer"));
    }

    /**
     * Test if -print option works well
     */
    @Test
    void testPrintOption() {
        MainMethodResult result = invokeMain("-print", "-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }


    /**
     * Test if -search option works well
     */
    @Test
    void testSearchOption() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "LAx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "510", "pdx", "1/1/2022", "12:0", "am", "LAx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search", "test airline", "pdx", "lax",
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Test when -search option can't find the flights, a message should be prompted
     */
    @Test
    void testSearchOption2() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search", "test airline", "pdx", "lax",
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("There is no direct flight between the specified airports for this airline."));
    }

    /**
     * Test source airport is unknown for -search option
     */
    @Test
    void testSearchOptionSrc1() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search", "test airline", "aaa", "lax",
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The source airport code does not correspond to a known airport."));
    }

    /**
     * Test source airport for -search option has more than three letters
     */
    @Test
    void testSearchOptionSrc2() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search", "test airline", "adsf", "lax",
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The source airport code does not contain three letters."));
    }

    /**
     * Test destination airport is unknown for -search option
     */
    @Test
    void testSearchOptionDest1() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search", "test airline", "pdx", "aaa",
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The destination airport code does not correspond to a known airport."));
    }

    /**
     * Test destination airport for -search option has more than three letters
     */
    @Test
    void testSearchOptionDest2() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search", "test airline", "pdx", "9999",
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("The destination airport code does not contain three letters."));
    }

    /**
     * Test if getting an airline works correctly
     */
    @Test
    void testGetAirline() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline");

        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Test if getting an airline that does not exist issues an error
     */
    @Test
    void testGetAirlineDoesntExist() {
        try {
            test0RemoveAllMappings();
        } catch (IOException e) {
            System.exit(1);
        }
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "610", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am");
        result = invokeMain("-host", HOSTNAME, "-port", PORT,
                "test airline2");

        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * Test if unknown option will issue an error
     */
    @Test
    void testUnknownOption() {
        MainMethodResult result = invokeMain("-unknown", "-host", HOSTNAME, "-port", PORT,
                "test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10");
        assertThat(result.getExitCode(), equalTo(1));
        String err = result.getTextWrittenToStandardError();
        assertThat(err, containsString("An unknown option was present."));
    }

    /**
     * Test not enough arguments for search
     */
    @Test
    void testNotEnoughSearchArg() {
        MainMethodResult result = invokeMain("-host", HOSTNAME, "-port", PORT, "-search");
        assertThat(result.getExitCode(), equalTo(1));
        String err = result.getTextWrittenToStandardError();
        assertThat(err, containsString("There should be an airline name, a source airport, and a destination airport for the option \"-search\""));
    }
}

