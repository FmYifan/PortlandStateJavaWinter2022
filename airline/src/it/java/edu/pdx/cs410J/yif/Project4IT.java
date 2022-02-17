package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project4} main class.
 */
class Project4IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project4.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    /**
     * Tests that invoking the main method with not enough arguments issues an error
     */
    @Test
    public void testNotEnoughArguments(){
        MainMethodResult result = invokeMain("Arg1", "2", "Arg");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Something is missing from the command line."));
    }

    /**
     * Tests that invoking the main method with not enough arguments issues an error
     */
    @Test
    public void testExtraneousArguments(){
        MainMethodResult result = invokeMain("test airline", "410", "pdx", "1/1/2022", "12:0", "am", "PDx", "1/2/2022", "12:10", "am", "Arg11");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("There are extraneous command line arguments."));
    }

    /**
     * Tests that entering a non-numeric flight number issues an error
     */
    @Test
    public void testNonNumericFlightNumber(){
        MainMethodResult result = invokeMain("test airline", "Test", "pdx", "1/1/2022", "12:0", "am", "pdx", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The flight number is non-numeric."));
    }

    /**
     * Tests that entering a non-three-letters source airport code issues an error
     */
    @Test
    public void testNonThreeLettersSrc(){
        MainMethodResult result = invokeMain("test airline", "410", "sdfk", "1/1/2022", "12:00", "am", "des", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The source airport code does not contain three letters."));
    }

    /**
     * Tests that entering an unknown source airport code issues an error
     */
    @Test
    public void testUnknownSrc(){
        MainMethodResult result = invokeMain("test airline", "410", "abc", "1/1/2022", "12:00", "am", "des", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The source airport code does not correspond to a known airport."));
    }

    /**
     * Tests that entering an incorrect departure date issues an error
     */
    @Test
    public void testIncorrectDepartDate(){
        MainMethodResult result = invokeMain("-print", "test airline", "410", "pdx", "100/1/2022", "12:00", "pm", "des", "1/2/2022", "12:00", "pm");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The format of the departure date is incorrect."));
    }

    /**
     * Tests that entering a non-three-letters destination airport code issues an error
     */
    @Test
    public void testNonThreeLettersDest(){
        MainMethodResult result = invokeMain("test airline", "410", "pdx", "1/1/2022", "12:0", "am", "D2L", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The destination airport code does not contain three letters."));
    }

    /**
     * Tests that entering an unknown destination airport code issues an error
     */
    @Test
    public void testUnknownDest(){
        MainMethodResult result = invokeMain("test airline", "410", "pdx", "1/1/2022", "12:00", "am", "abc", "1/2/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The destination airport code does not correspond to a known airport."));
    }

    /**
     * Tests that entering an incorrect arrival date issues an error
     */
    @Test
    public void testIncorrectArrivalDate(){
        MainMethodResult result = invokeMain("test airline", "410", "pdx", "10/19/2022", "12:0", "am", "pdx", "1/200/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The format of the arrival date is incorrect."));
    }

    /**
     * Tests that entering an earlier arrival date issues an error
     */
    @Test
    public void testEarlierArrivalDate(){
        MainMethodResult result = invokeMain("test airline", "410", "pdx", "10/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:00", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("This flight's arrival time is before its departure time."));
    }

    /**
     * Tests if the parsing of the command line arguments is correct
     */
    @Test
    public void testIfWorkingCorrectly(){
        MainMethodResult result = invokeMain("test airline", "410", "pdx", "1/19/2022", "12:0", "am", "lax", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if -print option works correctly
     */
    @Test
    public void testIfPrintCorrect(){
        MainMethodResult result = invokeMain("-print", "test airline", "410", "pdx", "1/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if -README option works correctly
     */
    @Test
    public void testIfReadmeCorrect(){
        MainMethodResult result = invokeMain("-print", "-README");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if unknown option issues an error
     */
    @Test
    public void testUnknownOption(){
        MainMethodResult result = invokeMain("-print", "-fake", "Name", "2", "src", "10/19/2022", "0:0", "des", "1/20/2022", "12:30");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("An unknown option was present."));
    }

    /**
     * Tests if different airline name with the one in the file issues an error
     */
    @Test
    public void testDiffAirline(){
        MainMethodResult result = invokeMain("-textFile", "src/test/resources/edu/pdx/cs410J/yif/diffNameTest.txt", "Test2", "2", "pdx", "1/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The airline name loaded from the file specified is not the same as the name passed through command line."));
    }


    /**
     * Tests if incomplete flight info in the file issues an error
     */
    @Test
    public void testIncompleteFlightParse(){
        MainMethodResult result = invokeMain("-textFile", "src/test/resources/edu/pdx/cs410J/yif/incompleteFlightTest.txt", "Test3", "410", "pdx", "1/19/2022", "12:0", "am", "des", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * Tests if the malformatted flight info in the file issues an error
     */
    @Test
    public void testMalformattedFlight(){
        MainMethodResult result = invokeMain("-textFile", "src/test/resources/edu/pdx/cs410J/yif/malformattedFlightTest.txt", "Test4", "2", "pdx", "1/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(1));
    }

    /**
     * Tests if the -textFile option works correctly
     */
    @Test
    public void testTextFileCorrect(){
        MainMethodResult result = invokeMain("-textFile", "src/test/resources/edu/pdx/cs410J/yif/textFileTest.txt", "Test5", "410", "pdx", "1/19/2022", "12:0", "am", "lax", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if the -pretty - option works correctly
     */
    @Test
    public void testPrettyStdOut(){
        MainMethodResult result = invokeMain("-pretty", "-", "Test", "410", "pdx", "1/19/2022", "12:0", "am", "lax", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if the -pretty file option works correctly
     */
    @Test
    public void testPretty(){
        MainMethodResult result = invokeMain("-pretty", "src/test/resources/edu/pdx/cs410J/yif/prettyTest.txt", "Test", "410", "pdx", "1/19/2022", "12:0", "am", "lax", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if specify both -textFile and -XmlFile issues an error
     */
    @Test
    public void testBothTextXml(){
        MainMethodResult result = invokeMain("-xmlFile", "src/test/resources/edu/pdx/cs410J/yif/textFileTest.txt", "-textFile", "src/test/resources/edu/pdx/cs410J/yif/textFileTest.txt", "Test5", "410", "pdx", "1/19/2022", "12:0", "am", "lax", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Error: -textFile and -xmlFile are both specified."));
    }

    /**
     * Tests if the -textFile option works correctly
     */
    @Test
    public void testXmlFileCorrect(){
        MainMethodResult result = invokeMain("-xmlFile", "src/test/resources/edu/pdx/cs410J/yif/xmlFileTest.xml", "Test5", "410", "pdx", "1/19/2022", "12:0", "am", "lax", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(0));
    }

    /**
     * Tests if different airline name with the one in the XML file issues an error
     */
    @Test
    public void testXmlDiffAirline(){
        MainMethodResult result = invokeMain("-xmlFile", "src/test/resources/edu/pdx/cs410J/yif/diffNameTest.xml", "Test2", "2", "pdx", "1/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("The airline name loaded from the file specified is not the same as the name passed through command line."));
    }

    /**
     * Tests if the malformatted flight info in the XML file issues an error
     */
    @Test
    public void testXmlMalformattedFlight(){
        MainMethodResult result = invokeMain("-xmlFile", "src/test/resources/edu/pdx/cs410J/yif/invalid-airline.xml", "Test4", "2", "pdx", "1/19/2022", "12:0", "am", "pdx", "1/20/2022", "12:30", "am");
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Have an error during parsing the file. The xml file cannot be parsed."));
    }

}