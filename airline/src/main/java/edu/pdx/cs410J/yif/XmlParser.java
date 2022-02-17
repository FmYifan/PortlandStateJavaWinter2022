package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class XmlParser implements AirlineParser<Airline> {
    private File xmlFile;

    public XmlParser(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * parse the xml file to an airline
     * @return the airline parsed
     * @throws ParserException
     */
    public Airline parse() throws ParserException {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            AirlineXmlHelper helper = new AirlineXmlHelper();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            doc = builder.parse(xmlFile);
            NodeList airlineList = doc.getElementsByTagName("airline");
            Element airlineElement = (Element) airlineList.item(0);
            NodeList nameList = airlineElement.getElementsByTagName("name");
            Element airlineName = (Element) nameList.item(0);
            Airline airline = new Airline(airlineName.getTextContent());
            NodeList flightList = airlineElement.getElementsByTagName("flight");
            for (int i = 0; i < flightList.getLength(); i++) {
                Node flight = flightList.item(i);
                NodeList elements = flight.getChildNodes();
                int number = 0;
                String source = null;
                Date departDate = null;
                String destination = null;
                Date arrivalDate = null;

                for (int j = 0; j < elements.getLength(); j++) {
                    Node node = elements.item(j);
                    if (!(node instanceof Element)) {
                        continue;
                    }
                    Element element = (Element) node;
                    switch (element.getTagName()) {
                        case "number": {
                            try {
                                number = Integer.parseInt(element.getTextContent());
                            } catch(NumberFormatException nfe){
                                throw new IOException();
                            }
                            break;
                        }
                        case "src": {
                            source = element.getTextContent();
                            int countLetters = 0;
                            for (int k = 0; k < source.length(); k++) {
                                if (Character.isLetter(source.charAt(k))) {
                                    countLetters++;
                                }
                            }
                            if (source.chars().count() != 3 || countLetters != 3) {
                                throw new IOException();
                            }
                            source = source.toUpperCase();
                            if (!AirportNames.getNamesMap().containsKey(source)) {
                                throw new IOException();
                            }
                            break;
                        }
                        case "depart": {
                            Calendar calendar = Calendar.getInstance();
                            calendar.clear();
                            calendar.setLenient(false);

                            NodeList depDate = node.getChildNodes();
                            for (int k = 0; k < depDate.getLength(); k++) {
                                Node dateTime = depDate.item(k);
                                if (!(dateTime instanceof Element)) {
                                    continue;
                                }
                                Element dT = (Element) dateTime;
                                switch (dT.getTagName()) {
                                    case "date": {
                                        calendar.set(Calendar.DATE, Integer.parseInt(dT.getAttribute("day")));
                                        calendar.set(Calendar.MONTH, Integer.parseInt(dT.getAttribute("month")));
                                        calendar.set(Calendar.YEAR, Integer.parseInt(dT.getAttribute("year")));
                                        continue;
                                    }
                                    case "time": {
                                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dT.getAttribute("hour")));
                                        calendar.set(Calendar.MINUTE, Integer.parseInt(dT.getAttribute("minute")));
                                        continue;
                                    }
                                }
                            }

                            departDate = calendar.getTime();
                            break;
                        }

                        case "dest": {
                            destination = element.getTextContent();
                            int countLetters = 0;
                            for (int k = 0; k < destination.length(); k++) {
                                if (Character.isLetter(destination.charAt(k))) {
                                    countLetters++;
                                }
                            }
                            if (destination.chars().count() != 3 || countLetters != 3) {
                                throw new IOException();
                            }
                            destination = destination.toUpperCase();
                            if (!AirportNames.getNamesMap().containsKey(destination)) {
                                throw new IOException();
                            }
                            break;
                        }

                        case "arrive": {
                            Calendar calendar = Calendar.getInstance();
                            calendar.clear();
                            calendar.setLenient(false);

                            NodeList arrDate = node.getChildNodes();
                            for (int k = 0; k < arrDate.getLength(); k++) {
                                Node dateTime = arrDate.item(k);
                                if (!(dateTime instanceof Element)) {
                                    continue;
                                }
                                Element dT = (Element) dateTime;
                                switch (dT.getTagName()) {
                                    case "date": {
                                        calendar.set(Calendar.DATE, Integer.parseInt(dT.getAttribute("day")));
                                        calendar.set(Calendar.MONTH, Integer.parseInt(dT.getAttribute("month")));
                                        calendar.set(Calendar.YEAR, Integer.parseInt(dT.getAttribute("year")));
                                        continue;
                                    }
                                    case "time": {
                                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dT.getAttribute("hour")));
                                        calendar.set(Calendar.MINUTE, Integer.parseInt(dT.getAttribute("minute")));
                                        continue;
                                    }
                                }
                            }

                            arrivalDate = calendar.getTime();
                            break;
                        }
                        default: {
                            throw new ParserConfigurationException();
                        }
                    }
                }

                if(arrivalDate.before(departDate)){
                    throw new IOException();
                }

                Flight addedFlight = new Flight(number, source, departDate, destination, arrivalDate);
                airline.addFlight(addedFlight);
            }

            airline.sortFlights();

            return airline;

        } catch (ParserConfigurationException | SAXException | IOException | IllegalArgumentException ex) {
            throw new ParserException("While parsing XML file", ex);
        }
    }
}
