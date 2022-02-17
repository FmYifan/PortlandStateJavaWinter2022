package edu.pdx.cs410J.yif;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * Convert Text file to an Xml File
 */
public class Converter {
    /**
     * Take the text file and Xml file as args
     * @param args [0] textFile, [1] xmlFile
     */
    public static void main(String [] args){
        if(args.length == 0){
            System.err.println("Missing command line arguments");
            System.out.println("usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile");
            System.exit(1);
        }

        //Check if there are missing arguments or extraneous arguments
        if(args.length < 2){
            System.err.println("Something is missing from the command line.");
            System.exit(1);
        }

        if(args.length > 2){
            System.err.println("There are extraneous command line arguments.");
            System.exit(1);
        }

        File file = null;
        Path temp = Paths.get(args[0]);
        if(temp.isAbsolute()){
            file = new File(String.valueOf(temp));
        }
        else {
            File curDir = new File(System.getProperty("user.dir"));
            if(String.valueOf(temp).startsWith("/")){
                file = new File(curDir.getAbsolutePath() + temp);
            } else {
                file = new File(curDir.getAbsolutePath() + "/" + temp);
            }
        }

        File xmlFile = null;
        Path temp2 = Paths.get(args[1]);
        if(temp.isAbsolute()){
            xmlFile = new File(String.valueOf(temp2));
        }
        else {
            File curDir = new File(System.getProperty("user.dir"));
            if(String.valueOf(temp).startsWith("/")){
                xmlFile = new File(curDir.getAbsolutePath() + temp2);
            } else {
                xmlFile = new File(curDir.getAbsolutePath() + "/" + temp2);
            }
        }
        try {
            xmlFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Cannot create the xml file.");
            System.exit(1);
        }

        Airline airline = null;
        TextParser parser = null;
        try {
            parser = new TextParser(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the text file.");
            System.exit(1);
        }
        try {
            airline = parser.parse();
        } catch (ParserException e) {
            System.err.println("Have an error during parsing the file. The text file cannot be parsed.");
            System.exit(1);
        }

        airline.sortFlights();

        XmlDumper dumper = new XmlDumper(xmlFile);
        dumper.dump(airline);

        System.exit(0);
    }
}
