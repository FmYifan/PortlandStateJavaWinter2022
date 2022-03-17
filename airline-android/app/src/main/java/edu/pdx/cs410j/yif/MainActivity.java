package edu.pdx.cs410j.yif;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import edu.pdx.cs410J.ParserException;

public class MainActivity extends AppCompatActivity {

    private static final int AIRLINE_CREATED = 41;
    public static Map<String, Airline> airlines = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadStorage();
    }

    /**
     * Launch Readme Activity
     * @param view
     */
    public void launchReadMe(View view) {
        Intent intent = new Intent(MainActivity.this, ReadMeActivity.class);
        startActivity(intent);
    }

    /**
     * Launch Creating Airline Activity
     * @param view
     */
    public void launchCreateAirline(View view) {
        Intent intent = new Intent(MainActivity.this, CreateAirlineActivity.class);
        startActivityForResult(intent, AIRLINE_CREATED);
    }

    /**
     * Launch Searching Flights Activity
     * @param view
     */
    public void searchingFlights(View view) {
        Intent intent = new Intent(MainActivity.this, SearchingFlightsActivity.class);
        startActivity(intent);
    }

    /**
     * Launch Searching ALl Activity. Display all of the flights from one airline
     * @param view
     */
    public void searchingAll(View view) {
        Intent intent = new Intent(MainActivity.this, SearchingAllActivity.class);
        startActivity(intent);
    }

    /**
     * get result from activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AIRLINE_CREATED && resultCode == RESULT_OK && data != null){
            Airline airline = (Airline) data.getSerializableExtra("Added Airline");
            String airlineName = airline.getName();
            if(!airlines.containsKey(airlineName)) {
                airlines.put(airlineName, airline);
            } else {
                Flight flight = airline.getFlights().get(0);
                airlines.get(airlineName).addFlight(flight);
            }
            writeStorage(airline);
        }
    }

    /**
     * Loading data from internal storage
     */
    private void loadStorage(){
        try{
                File file = getBaseContext().getFileStreamPath("data.txt");
                if(file.exists()) {
                    FileInputStream fileInputStream = openFileInput("data.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    TextParser parser = new TextParser(inputStreamReader);
                    airlines = parser.parseAll();
                }
        } catch (ParserException | IOException e) {
            Toast.makeText(MainActivity.this, "Loading failure", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Write Data to internal Storage
     * @param airline
     */
    private void writeStorage(Airline airline){
        try{
            FileOutputStream fileOutputStream = openFileOutput("data.txt", MODE_APPEND);
            Writer osw = new OutputStreamWriter(fileOutputStream);
            TextDumper dumper = new TextDumper(osw);
            dumper.dump(airline);
        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "Cannot find the file to save data.", Toast.LENGTH_LONG).show();
        }
    }

}