package spicyglass.client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import spicyglass.client.ui.screens.AutoStartActivity;
import spicyglass.client.ui.screens.DefrostActivity;
import spicyglass.client.ui.screens.DiagnosisActivity;
import spicyglass.client.ui.screens.LockerActivity;
import spicyglass.client.ui.screens.SeatActivity;
import spicyglass.client.ui.screens.SettingActivity;

//used for the weather API integration


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    class WeatherAPI extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... address) { //First String means URL is in String form. Void means nothing. Third String means Return type will be in String
            //String... means multiple addresses can be sent. It acts as an array.

            String APIKey = RequestWeather(address);
            return APIKey;

        }
    }


    private Button button1, button2, button3, button4, button5, button6, button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        GetAPIKey(); //function that requests the API Key


        button1 = findViewById(R.id.setting);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.diagnosis);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.defrost);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.seat);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.locker);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.Auto);
        button6.setOnClickListener(this);
        }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {

            case R.id.setting:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.diagnosis:
                intent = new Intent(this, DiagnosisActivity.class);
                startActivity(intent);
                break;

            case R.id.defrost:
                intent = new Intent(this, DefrostActivity.class);
                startActivity(intent);
                break;

            case R.id.seat:
                intent = new Intent(this, SeatActivity.class);
                startActivity(intent);
                break;

            case R.id.locker:
                intent = new Intent(this, LockerActivity.class);
                startActivity(intent);
                break;

            case R.id.Auto:
                intent = new Intent(this, AutoStartActivity.class);
                startActivity(intent);
                break;
                
        }
            //HelloWorld.helloWorld();
    }

    public JSONArray GetAPIKey(){
        String APIKeyVar;
        WeatherAPI weather = new WeatherAPI();
        try {
            APIKeyVar = weather.execute("https://openweathermap.org/data/2.5/weather?q=Lubbock&appid=b6907d289e10d714a6e88b30761fae22").get(); //gets the information for the location
            //first we will check data is retrieve successfully or not
            Log.i("APIData", APIKeyVar); //displays data from the whole API

            //JSON
            JSONObject jsonObject = new JSONObject(APIKeyVar);
            String weatherData = jsonObject.getString("weather");
            Log.i("weatherData",weatherData); //displays data from the weather
            //weather data is in Array
            JSONArray array = new JSONArray(weatherData);
            return array;

        } catch (Exception e) { //used to check if variable content doesn't go through
            e.printStackTrace();
        }
        return null;
    }

    public String RequestWeather(String[] address) {
        try {
            URL url = new URL(address[0]); //checks URL verification
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Establishes connection with address
            connection.connect();

            //retrieve data from url
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            //Retrieve data and return it as a String
            int data = isr.read();
            String APIKeyVar = "";
            char ch;
            while(data!=-1){
                ch = (char) data;
                APIKeyVar = APIKeyVar + ch;
                data = isr.read();
            } //will loop through all the data in the API link
            return APIKeyVar;

        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        //Checks if data fails to be retrieved
        return null;

    }






}

