package spicyglass.client.ui.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;
import spicyglass.client.integration.external.WeatherHandler;


public class DefrostActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result;
    Button Start, Stop, Calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.defrost_page);

        Start = (Button) findViewById(R.id.DefStartBut);
        Start.setOnClickListener(this);
        Stop = (Button) findViewById(R.id.DefStopBut);
        Stop.setOnClickListener(this);

        //Work done to display weather data
        result = findViewById(R.id.result);
        String display;
        WeatherHandler weather = new WeatherHandler(); //creates new object
        display = weather.displayWeather();
        result.setText(display);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.DefStartBut:
                Start.setVisibility(View.INVISIBLE);
                Stop.setVisibility(View.VISIBLE);
                Start.setEnabled(false);
                Stop.setEnabled(true);
                break;

            case R.id.DefStopBut:
                Start.setVisibility(View.VISIBLE);
                Stop.setVisibility(View.INVISIBLE);
                Start.setEnabled(true);
                Stop.setEnabled(false);
                break;
        }

    }

}
