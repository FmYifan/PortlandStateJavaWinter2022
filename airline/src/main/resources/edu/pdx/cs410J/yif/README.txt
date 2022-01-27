This is a README file!
Yifan Zhao
CS410J
Project 2: Storing An Airline in a Text File
For this project, the TextParser and TextDumper has implemented. The TextParser implements AirlineParser, and the TextDumper implements AirlineDumper.
Parsing the command line has been implemented in class project2.
For TextParser, parse() has been overridden to read the contents of a text file, and from it create an airline with its associated flights.
For TextDumper, dump() has been overridden to dump the contents of an airline and its flights into a text file.

The format for the data in the file is shown below:
airline 		The name of the airline
flightNumber 		The flight number
src 		Three-letter code of departure airport
departDate 		Departure date (24-hour time)
departTime 		Departure time (24-hour time)
dest 		Three-letter code of arrival airport
arriveDate 		Arrival date (24-hour time)
arriveDate 		Arrival time (24-hour time)

Through the options specified by the command line arguments "-print" and "-README", program can print a description of the Flight or print the content of README.
In addition, the option "-textFile file" can make user load from and write back to a file specified in the command line.

The usage of this program is shown as below:
    java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
        airline     The name of the airline
        flightNumber    The flight number
        src     Three-letter code of departure airport
        depart  Departure date and time (24-hour time)
        dest    Three-letter code of arrival airport
        arrive   Arrival date and time (24-hour time)
    options are (options may appear in any order):
        -textFile file  Where to read/write the airline info
        -print  Prints a description of the new flight
        -README     Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm