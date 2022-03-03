package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirlineDumper;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;

public class XmlDumper implements AirlineDumper<Airline>{
    private final Writer writer;

    public XmlDumper(Writer writer) {
        this.writer = writer;
    }


    @Override
    public void dump(Airline airline){

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();

            AirlineXmlHelper helper = new AirlineXmlHelper();
            builder.setErrorHandler(helper);
            builder.setEntityResolver(helper);

            DocumentType docType = dom.createDocumentType("airline", "-//Portland State University//DTD CS410J Airline//EN","http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
            doc = dom.createDocument(null, "airline", docType);

        } catch (ParserConfigurationException | DOMException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }


        // Construct the DOM tree
        try {
            Element root = doc.getDocumentElement();
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(airline.getName()));
            root.appendChild(name);
            ArrayList<Flight> flightList = airline.getFlights();
            int size = flightList.size();
            for(int i = 0; i < size; i++){
                Flight flight = flightList.get(i);
                Element fli = doc.createElement("flight");
                root.appendChild(fli);
                Element number = doc.createElement("number");
                number.appendChild(doc.createTextNode(String.valueOf(flight.getNumber())));
                fli.appendChild(number);
                Element src = doc.createElement("src");
                src.appendChild(doc.createTextNode(flight.getSource()));
                fli.appendChild(src);
                Element depart = doc.createElement("depart");
                fli.appendChild(depart);
                Calendar calendarDepart = Calendar.getInstance();
                calendarDepart.setTime(flight.getDeparture());
                Element departDate = doc.createElement("date");
                departDate.setAttribute("day", String.valueOf(calendarDepart.get(Calendar.DATE)));
                departDate.setAttribute("month", String.valueOf(calendarDepart.get(Calendar.MONTH)));
                departDate.setAttribute("year", String.valueOf(calendarDepart.get(Calendar.YEAR)));
                depart.appendChild(departDate);
                Element departTime = doc.createElement("time");
                departTime.setAttribute("hour", String.valueOf(calendarDepart.get(Calendar.HOUR_OF_DAY)));
                departTime.setAttribute("minute", String.valueOf(calendarDepart.get(Calendar.MINUTE)));
                depart.appendChild(departTime);
                Element dest = doc.createElement("dest");
                dest.appendChild(doc.createTextNode(flight.getDestination()));
                fli.appendChild(dest);
                Element arrive = doc.createElement("arrive");
                fli.appendChild(arrive);
                Calendar calendarArrive = Calendar.getInstance();
                calendarArrive.setTime(flight.getArrival());
                Element arrivalDate = doc.createElement("date");
                arrivalDate.setAttribute("day", String.valueOf(calendarArrive.get(Calendar.DATE)));
                arrivalDate.setAttribute("month", String.valueOf(calendarArrive.get(Calendar.MONTH)));
                arrivalDate.setAttribute("year", String.valueOf(calendarArrive.get(Calendar.YEAR)));
                arrive.appendChild(arrivalDate);
                Element arrivalTime = doc.createElement("time");
                arrivalTime.setAttribute("hour", String.valueOf(calendarArrive.get(Calendar.HOUR_OF_DAY)));
                arrivalTime.setAttribute("minute", String.valueOf(calendarArrive.get(Calendar.MINUTE)));
                arrive.appendChild(arrivalTime);
            }
        } catch (DOMException ex) {
            System.err.println("** DOMException: " + ex);
            System.exit(1);
        }
        // Write the XML document to the xml file specified
        try {
            Source src = new DOMSource(doc);
            Result res = new StreamResult(writer);
            TransformerFactory xFactory = TransformerFactory.newInstance();
            Transformer xform = xFactory.newTransformer();
            xform.setOutputProperty(OutputKeys.INDENT, "yes");
            xform.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd");
            xform.setOutputProperty(OutputKeys.METHOD, "xml");
            xform.transform(src, res);
        } catch (TransformerException | DOMException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }
}

