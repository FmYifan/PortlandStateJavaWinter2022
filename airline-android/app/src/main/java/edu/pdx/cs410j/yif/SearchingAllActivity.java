package edu.pdx.cs410j.yif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

public class SearchingAllActivity extends AppCompatActivity {

    private static Map<String, Airline> airlines = MainActivity.airlines;
    private ArrayAdapter<String> result;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_all);

        listView = (ListView) findViewById(R.id.searchResult2);
    }

    public void BackToMain(View view) {
        finish();
    }

    /**
     * Search and display all flights in one airline
     * @param view
     */
    public void GetSearching(View view) {
        EditText name = (EditText) findViewById(R.id.searchAirlineName2);
        String airlineName = name.getText().toString().trim();
        Airline to_search = null;
        int flag = 0;
        try {
            if (airlineName.matches("")) {
                Toast.makeText(SearchingAllActivity.this, "Airline Name cannot be empty", Toast.LENGTH_LONG).show();
                throw new IOException();
            }

            to_search = this.airlines.get(airlineName);
            if (to_search == null) {
                Toast.makeText(SearchingAllActivity.this, "Cannot find the airline.", Toast.LENGTH_LONG).show();
                throw new IOException();
            }
        } catch(IOException e){
            flag = 1;
        }
        if(flag == 0) {
            listView.setAdapter(null);
            this.result = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            listView.setAdapter(this.result);
            this.result.add("Airline Name: " + airlineName);
            for (Flight flight : to_search.getFlights()) {
                this.result.add(flight.prettyPrint());
            }
            Toast.makeText(SearchingAllActivity.this, "Searching Successfully!", Toast.LENGTH_LONG).show();
        }
    }
}