package edu.pdx.cs410j.yif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.pdx.cs410J.AirportNames;

public class SearchingFlightsActivity extends AppCompatActivity {

    private static Map<String, Airline> airlines = MainActivity.airlines;
    private ArrayAdapter<String> result;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_flights);

        listView = (ListView) findViewById(R.id.searchResult);

    }

    public void BackToMain(View view) {
        finish();
    }

    public void GetSearching(View view) {
        EditText name = (EditText) findViewById(R.id.searchAirlineName);
        EditText src = (EditText) findViewById(R.id.searchSource);
        EditText dest = (EditText) findViewById(R.id.searchDestination);

        String airlineName = name.getText().toString().trim();
        String source = src.getText().toString().trim();
        String destination = dest.getText().toString().trim();
        Airline to_search = null;
        Airline to_print = null;
        int flag = 0;

        try {
            if (airlineName.matches("")) {
                Toast.makeText(SearchingFlightsActivity.this, "Airline Name cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            }

            if (source.matches("")) {
                Toast.makeText(SearchingFlightsActivity.this, "The source airport code cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            } else {
                int countLetters = 0;
                for (int j = 0; j < source.length(); j++) {
                    if (Character.isLetter(source.charAt(j))) {
                        countLetters++;
                    }
                }
                if (source.chars().count() != 3 || countLetters != 3) {
                    Toast.makeText(SearchingFlightsActivity.this, "The source airport code does not contain three letters.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
                source = source.toUpperCase();
                if (!AirportNames.getNamesMap().containsKey(source)) {
                    Toast.makeText(SearchingFlightsActivity.this, "The source airport code does not correspond to a known airport.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
            }

            if (destination.matches("")) {
                Toast.makeText(SearchingFlightsActivity.this, "The destination airport code cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            } else {
                int countLetters = 0;
                for (int j = 0; j < destination.length(); j++) {
                    if (Character.isLetter(destination.charAt(j))) {
                        countLetters++;
                    }
                }
                if (destination.chars().count() != 3 || countLetters != 3) {
                    Toast.makeText(SearchingFlightsActivity.this, "The destination airport code does not contain three letters.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
                destination = destination.toUpperCase();
                if (!AirportNames.getNamesMap().containsKey(destination)) {
                    Toast.makeText(SearchingFlightsActivity.this, "The destination airport code does not correspond to a known airport.", Toast.LENGTH_LONG).show();
                    throw new IOException();
                }
            }


            to_search = this.airlines.get(airlineName);
            if (to_search == null) {
                Toast.makeText(SearchingFlightsActivity.this, "Cannot find the airline.", Toast.LENGTH_LONG).show();
                throw new IOException();
            }

            to_print = new Airline(airlineName);
            for(Flight flight : to_search.getFlights()){
                if(flight.getSource().equals(source) && flight.getDestination().equals(destination)){
                    to_print.addFlight(flight);
                }
            }
            if(to_print.getFlights().size() == 0){
                Toast.makeText(SearchingFlightsActivity.this, "There is no flight meets the requirement.", Toast.LENGTH_LONG).show();
                throw new IOException();
            }
        } catch (IOException e){
            flag = 1;
        }

        if(flag == 0) {
            listView.setAdapter(null);
            this.result = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            listView.setAdapter(this.result);
            this.result.add("Airline Name: " + airlineName);
            for (Flight flight : to_print.getFlights()) {
                    this.result.add(flight.prettyPrint());
            }
            Toast.makeText(SearchingFlightsActivity.this, "Searching Successfully!", Toast.LENGTH_LONG).show();
        }
    }
}