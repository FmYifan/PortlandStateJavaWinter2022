package edu.pdx.cs410j.yif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);

        TextView textView = (TextView) findViewById(R.id.ReadMe);
        //Create inputStream object
        InputStream inputStream = this.getResources().openRawResource(R.raw.readme);
        //Create bufferedReader object
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        //Create stringBuffer object
        StringBuffer stringBuffer = new StringBuffer();

        if(inputStream != null){
            try{
                while(br.ready()){
                    stringBuffer.append(br.readLine() + "\n");
                }
                textView.setText(stringBuffer);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Back to Main Activity
     * @param view
     */
    public void backToMain(View view) {
        finish();
    }
}