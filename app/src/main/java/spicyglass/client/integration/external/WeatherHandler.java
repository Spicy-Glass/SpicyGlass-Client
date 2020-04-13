package spicyglass.client.integration.external;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import spicyglass.client.R;

public class WeatherHandler {

    public static JSONArray GetAPIKey(){
        String APIKeyVar;
        WeatherAPI weather = new WeatherAPI();
        try {
            APIKeyVar = weather.execute("https://openweathermap.org/data/2.5/weather?q=lubbock&appid=439d4b804bc8187953eb36d2a8c26a02").get(); //gets the information for the location
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

    public static String displayWeather(){
        WeatherAPI weather = new WeatherAPI();
        try {
            String content;
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q=lubbock&appid=439d4b804bc8187953eb36d2a8c26a02").get(); //gets the information for the location

            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main");
            Log.i("weatherData",weatherData);

            String windData = jsonObject.getString("wind"); //TEST
            Log.i("windData", windData);

            String location = jsonObject.getString("name");

            //weather data is in Array
            JSONArray array = new JSONArray(weatherData);

            //wind data is in Array 1
            //JSONArray array1 = new JSONArray(windData);

            //String main = "";
            String description = "";
            String temperature = "";
            String windSpeed = "";



            for(int i = 0; i< array.length(); i++){
                JSONObject weatherPart = array.getJSONObject(i);
                //main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }
            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");

            JSONObject windPart = new JSONObject(windData);
            windSpeed = windPart.getString("speed");



            //Log.i("Temperature", temperature);
            //Log.i("main",main);
            //Log.i("description",description);
            //Log.i("windSpeed", windSpeed);
            //Log.i("location", location);


            String resultText = "Description: " + description
                    + "\nTemperature: " + temperature + "°C"
                    + "\nWind Speeds: " + windSpeed
                    + "\nLocation: " + location;

            return resultText;
            //result.setText(resultText); //Should display text


        } catch (Exception e) { //used to check if variable content doesn't go through
            e.printStackTrace();
        }
        return null;
    }

    public static String RequestWeather(String[] address) {

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

    static class WeatherAPI extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... address) { //First String means URL is in String form. Void means nothing. Third String means Return type will be in String
            //String... means multiple addresses can be sent. It acts as an array.

            String APIKey = RequestWeather(address);
            return APIKey;

        }
    }
}
