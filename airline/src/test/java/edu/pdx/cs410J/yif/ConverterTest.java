package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * A Unit test for the Converter
 */
class ConverterTest extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Tests that invoking the main method with not enough arguments issues an error
     */
    @Test
    void testNotEnoughArguments(){
        MainMethodResult result = invokeMain("1");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Something is missing from the command line."));
    }

    /**
     * Tests that invoking the main method with not enough arguments issues an error
     */
    @Test
    void testExtraneousArguments(){
        MainMethodResult result = invokeMain("1", "2", "3");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("There are extraneous command line arguments."));
    }

    @Test
    void testIfWorkCorrectly(){
        MainMethodResult result = invokeMain("src/test/resources/edu/pdx/cs410J/yif/validFlights.txt", "src/test/resources/edu/pdx/cs410J/yif/converterTest.xml");
        assertThat(result.getExitCode(), equalTo(0));


        File file = new File("src/test/resources/edu/pdx/cs410J/yif/converterTest.xml");

        XmlParser parser = new XmlParser(file);
        Airline read = null;
        try {
            read = parser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        assertThat(read.getName(), equalTo("test airline"));
        assertThat(read.getFlights().get(0).toString(), equalTo("Flight 410 departs HRL at 1/20/22, 3:15 AM arrives HSV at 2/10/22, 4:00 PM"));
        assertThat(read.getFlights().get(1).toString(), equalTo("Flight 410 departs PDX at 1/1/22, 12:00 AM arrives PDX at 1/2/22, 12:10 AM"));
    }



}
