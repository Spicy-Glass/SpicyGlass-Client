package spicyglass.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import spicyglass.client.integration.external.PubSubSubscriber;
import spicyglass.client.integration.system.CalendarHandler;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "LoginPrefs";
    Button login;

    @Override
    protected void onCreate(Bundle SavedInstanceSTate){
        super.onCreate(SavedInstanceSTate);
        setContentView(R.layout.main_login);

        login = findViewById(R.id.LogBut);
        //EditText username = (EditText) findViewById(R.id.Email);
        //EditText password = (EditText) findViewById(R.id.Password);

        SharedPreferences login_info = getSharedPreferences("logged", 0);


        if (login_info.getString("logged","").toString().equals("logged")){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        login.setOnClickListener(v -> {
            //within the on click if login info is correct then you apply these if not give error msg
            //if(username.getText().toString().equals("spicy@ttu.edu") && password.getText().toString().equals("glass")){
                SharedPreferences login_info1 = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = login_info1.edit();
                editor.putString("logged","logged");
                editor.apply();
                //Start the Subscriber on a new thread, DO NOT do it on the main thread
                new Thread(()-> PubSubSubscriber.init(LoginActivity.this)).start();
                LoginActivity.this.SwitchMainActivity();
            //}
            //else{
                //Toast.makeText(getApplicationContext(), "Stop Trying To Hack Someone!", Toast.LENGTH_SHORT).show();
            Calendar beginTime =  Calendar.getInstance();
            beginTime.setTime(new Date(2020, 3, 1));
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(new Date(2020, 4, 1));
            CalendarHandler.requestEvent(this, beginTime, endTime);
            //}
        });

    }

    public void SwitchMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
