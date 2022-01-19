This is a README file!
Yifan Zhao
CS410J
Project 1: Designing an Airline Application
For this project, the fundamental Airline and Flight classes are created and extended. The fields and methods are implemented.
Airline extends AbstractAirline and Flight extends AbstractFlight. Each of them implement all of the abstract methods of its superclass.
An airline has a name and consists of multiple Flights.
A Flight has a flight number, a source airport, a departure airport, departure date and time, and arrival date and time.
In addition, the main method in the Project1 class has been implemented. In the main method, the command line arguments are parsed.
So that an Airline and a Flight can be created as specified by the command line. And the new Flight can be added to the Airline.
Through the options specified by the command line arguments "-print" and "-README", program can print a description of the Flight or print the content of README.
The usage of this program is shown as below:
    java edu.pdx.cs410J.<login-id>.Project1 [options] <args>
    args are (in this order):
        airline The name of the airline
        flightNumber The flight number
        src Three-letter code of departure airport
        depart Departure date and time (24-hour time)
        dest Three-letter code of arrival airport
        arrive Arrival date and time (24-hour time)
    options are (options may appear in any order):
        -print Prints a description of the new flight
        -README Prints a README for this project and exits
    Date and time should be in the format: mm/dd/yyyy hh:mm