package edu.pdx.cs410j.yif;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AirportNames;

public class CreateAirlineActivity extends AppCompatActivity {

    static final int CONFIRM_RESULT = 40;
    Airline airline;
    Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_airline);
    }


    /**
     * Back to the MainActivity
     * @param view
     */
    public void backToMain(View view) {
        finish();
    }

    /**
     * Load info for airline and flight, and go to confirm activity
     * @param view
     */
    public void confirmInfo(View view) {
        EditText name = (EditText) findViewById(R.id.airlineName);
        EditText number = (EditText) findViewById(R.id.flightNumber);
        EditText source = (EditText) findViewById(R.id.source);
        EditText depDate = (EditText) findViewById(R.id.departDate);
        EditText depTime = (EditText) findViewById(R.id.departTime);
        EditText depAA = (EditText) findViewById(R.id.departAA);
        EditText destination = (EditText) findViewById(R.id.destination);
        EditText arrDate = (EditText) findViewById(R.id.arrivalDate);
        EditText arrTime = (EditText) findViewById(R.id.arrivalTime);
        EditText arrAA = (EditText) findViewById(R.id.arrivalAA);

        String airlineName;
        int flightNumber = 0;
        String src;
        Date departDate = null;
        String dest;
        Date arrivalDate = null;

        int parseFlag = 0;


        airlineName = name.getText().toString().trim();
        src = source.getText().toString().trim();
        dest = destination.getText().toString().trim();
        //Error handling
        try {
            if (airlineName.matches("")) {
                Toast.makeText(CreateAirlineActivity.this, "Airline Name cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            }

            //flight number
            try {
                String flightNumberString = number.getText().toString();
                if (flightNumberString.matches("")) {
                    Toast.makeText(CreateAirlineActivity.this, "Flight number cannot be empty", Toast.LENGTH_LONG).show();
                    throw new IOException();
                } else {
                    flightNumber = Integer.parseInt(flightNumberString);
                }
            } catch (NumberFormatException nfe) {
                Toast.makeText(CreateAirlineActivity.this, "Flight number should be numerical", Toast.LENGTH_LONG).show();
                throw new IOException();
            }

            //source code
            if (src.matches("")) {
                Toast.makeText(CreateAirlineActivity.this, "The source airport code cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            } else {
                int countLetters = 0;
                for (int i = 0; i < src.length(); i++) {
                    if (Character.isLetter(src.charAt(i))) {
                        countLetters++;
                    }
                }
                if (src.chars().count() != 3 || countLetters != 3) {
                    Toast.makeText(CreateAirlineActivity.this, "The source airport code does not contain three letters.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
                src = src.toUpperCase();
                if (!AirportNames.getNamesMap().containsKey(src)) {
                    Toast.makeText(CreateAirlineActivity.this, "The source airport code does not correspond to a known airport.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
            }

            //Departure date and time
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);
            dateFormat.setLenient(false);
            StringBuilder sb = new StringBuilder();
            String dateString = depDate.getText().toString().trim();
            String timeString = depTime.getText().toString().trim();
            String aaString = depAA.getText().toString().trim();
            if (dateString.matches("") || timeString.matches("") || aaString.matches("")) {
                Toast.makeText(CreateAirlineActivity.this, "The departure date and time cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            } else {

                sb.append(dateString + " ");
                sb.append(timeString + " ");
                sb.append(aaString);
                try {
                    departDate = dateFormat.parse(sb.toString().trim());
                } catch (ParseException e) {
                    Toast.makeText(CreateAirlineActivity.this, "The format of the departure date is incorrect.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
            }


            //destination code
            if (dest.matches("")) {
                Toast.makeText(CreateAirlineActivity.this, "The destination airport code cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            } else {
                int countLetters = 0;
                for (int i = 0; i < dest.length(); i++) {
                    if (Character.isLetter(dest.charAt(i))) {
                        countLetters++;
                    }
                }
                if (dest.chars().count() != 3 || countLetters != 3) {
                    Toast.makeText(CreateAirlineActivity.this, "The destination airport code does not contain three letters.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
                dest = dest.toUpperCase();
                if (!AirportNames.getNamesMap().containsKey(dest)) {
                    Toast.makeText(CreateAirlineActivity.this, "The dest airport code does not correspond to a known airport.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
            }

            //Arrival date and time
            sb = new StringBuilder();
            dateString = arrDate.getText().toString().trim();
            timeString = arrTime.getText().toString().trim();
            aaString = arrAA.getText().toString().trim();
            if (dateString.matches("") || timeString.matches("") || aaString.matches("")) {
                Toast.makeText(CreateAirlineActivity.this, "The arrival date and time cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            } else {
                sb.append(dateString + " ");
                sb.append(timeString + " ");
                sb.append(aaString);
                try {
                    arrivalDate = dateFormat.parse(sb.toString().trim());
                } catch (ParseException e) {
                    Toast.makeText(CreateAirlineActivity.this, "The format of the arrival date is incorrect.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
            }

            if (arrivalDate.before(departDate)) {
                Toast.makeText(CreateAirlineActivity.this, "This flight's arrival time is before its departure time.", Toast.LENGTH_LONG).show();
                throw new IOException();
            }
        } catch(IOException e){
            parseFlag = 1;
        }

        if(parseFlag == 0){
            airline = new Airline(airlineName);
            Flight flight = new Flight(flightNumber, src, departDate, dest, arrivalDate);
            airline.addFlight(flight);
            Intent intent = new Intent(CreateAirlineActivity.this, ConfirmActivity.class);
            intent.putExtra("Airline", airline);
            startActivityForResult(intent, CONFIRM_RESULT);
        }
    }

    /**
     * Get the result and airline from confirmation activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CONFIRM_RESULT && resultCode == RESULT_OK && data != null){
            Intent back = new Intent();
            back.putExtra("Added Airline", data.getSerializableExtra("Added Airline"));
            setResult(RESULT_OK, back);
            finish();
        }
    }
}