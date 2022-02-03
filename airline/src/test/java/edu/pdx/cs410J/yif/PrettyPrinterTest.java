package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Unit test for PrettyPrinter
 */
public class PrettyPrinterTest {
    /**
     * Test if airline name is successfully dumped in text format
     */
    @Test
    void airlineNameIsDumpedInTextFormat() {
        String airlineName = "Test Airline";
        Airline airline = new Airline(airlineName);

        StringWriter sw = new StringWriter();
        PrettyPrinter dumper = new PrettyPrinter(sw);
        dumper.dump(airline);

        String text = sw.toString();
        assertThat(text, containsString(airlineName));
    }
}
