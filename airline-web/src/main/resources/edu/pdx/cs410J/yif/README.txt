This is a README file!
Yifan Zhao
CS410J
Project 5: A REST-ful Airline Web Service

For this project, the AirlineRestClient and AirlineServlet has implemented.

AirlineRestClient is responsible for sending the HttpRequest. For sending the Get
and Post Request to the servlet, client can get the needed airline from the servlet
or add a flight/airline to the servlet.

AirlineServlet is responsible for receiving the HttpRequest that client makes.
By implementing doGet() and doPost(), servlet can manage multiple airlines in the
server, and dump the airline using XmlDumper to client.

Through the options specified by the command line arguments "-print" and "-README",
program can print a description of the Flight or print the content of README.
The new option "-search Airline Source Destination" can pretty print all flights in one airline
that originate from the source airport and arrive at the destination airport which
have been specified through the command line argument.

Also, "-host" and "-port" specify the host name and the port respectively.

The usage of this program is shown as below:
    java edu.pdx.cs410J.<login-id>.Project5 [options] <args>
    args are (in this order):
        airline     The name of the airline
        flightNumber    The flight number
        src     Three-letter code of departure airport
        depart  Departure date and time (12-hour time)
        dest    Three-letter code of arrival airport
        arrive   Arrival date and time (12-hour time)
    options are (options may appear in any order):
        -host   hostname Host computer on which the server runs
        -port   port Port on which the server is listening
        -search Search for flights
        -print  Prints a description of the new flight
        -README Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm