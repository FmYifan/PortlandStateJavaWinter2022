package edu.pdx.cs410j.yif;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmActivity extends AppCompatActivity {

    Airline airline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        airline = (Airline) getIntent().getSerializableExtra("Airline");

        TextView info = findViewById(R.id.info);
        info.setText("Airline Name: " + airline.getName() + "\n"
        + airline.getFlights().get(0).prettyPrint());
    }

    public void ConfirmCreate(View view) {
        Toast.makeText(ConfirmActivity.this, "Create Successfully!", Toast.LENGTH_LONG).show();
        Intent data = new Intent();
        data.putExtra("Added Airline", this.airline);
        setResult(RESULT_OK, data);
        finish();
    }

    public void BackToPrevious(View view) {
        finish();
    }
}