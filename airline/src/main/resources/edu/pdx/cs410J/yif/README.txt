This is a README file!
Yifan Zhao
CS410J
Project 4: Storing Airlines as XML

For this project, the XML Parser and dumper has implemented.
Also, a Converter class has been implemented to convert a text file to an XML file
The useage of the converter is shown as below:
usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile

The format for the parsable data has been modified.
The modified version is shown as below:
airline 		The name of the airline
flightNumber 		The flight number
src 		Three-letter code of departure airport
departDate 		MM/dd/yyyy hh:mm aa
dest 		Three-letter code of arrival airport
arriveDate 	MM/dd/yyyy hh:mm aa

Through the options specified by the command line arguments "-print" and "-README", program can print a description of the Flight or print the content of README.
The option "-textFile file" can make user load from and write back to a file specified in the command line.
The new option "-pretty file" can pretty print the airline and flights to the file specified.
If "-" presents for the "file" field, then the information will be printed to the screen.

The new option for this project is the option "-xmlFile". With this option, user can load from and write back to a file
specified in the command line.
One thing need to be noted is that specifying both options "-textFile" and "-xmlFile" will be seen as an error.

The usage of this program is shown as below:
    java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
        airline     The name of the airline
        flightNumber    The flight number
        src     Three-letter code of departure airport
        depart  Departure date and time (12-hour time)
        dest    Three-letter code of arrival airport
        arrive   Arrival date and time (12-hour time)
    options are (options may appear in any order):
        -xmlFile file   Where to read/write the airline info
        -pretty file    Pretty print the airline’s flights to
                        a text file or standard out (file -)
        -textFile file  Where to read/write the airline info
        -print  Prints a description of the new flight
        -README     Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm