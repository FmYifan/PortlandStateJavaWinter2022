This is a README file!
Yifan Zhao
CS410J
Project 6: A Small Device Application for an Airline

For this project, a small Android device application has implemented.
There is 3 main functionality for this application.

The first functionality is Creating an Airline and a Flight. Users need to click on
the button and enter the information of the airline and
the flight.
The data format are shown as below:
    airline     The name of the airline
    flightNumber    The flight number
    src     Three-letter code of departure airport
    depart  Departure date and time (12-hour time)
    dest    Three-letter code of arrival airport
    arrive   Arrival date and time (12-hour time)
    *Date and time should be in the format: mm/dd/yyyy hh:mm
After submitting the data, a confirmation page will show up. The information of the airline and
flight added will be pretty-printed. After confirming the information is correct, click on the
"Create" button to create this airline and flight in the internal storage. If the airline added
already existed, then the new flight will append to that airline.

The second functionality is Searching Airline. Users need to click on the button and enter the
name of the airline. After submitting, the information of this airline and all of its flights
will be pretty-printed in the below.

The third functionality is Searching Flights by Source and Destination. Users need to click on the
button and enter the name of the airline, the source airport code that flights depart, and the
destination airport code that flights arrive. After submitting, the information of this airline
and all of the flights which meet the requirement will be pretty-printed in the below.

On the main page, clicking on the Help button can show this page, which is the usage of this
application.
