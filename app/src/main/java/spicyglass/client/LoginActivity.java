package spicyglass.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import kotlin.Unit;
import spicyglass.client.integration.external.APIResponse;
import spicyglass.client.integration.external.PubSubSubscriber;
import spicyglass.client.integration.external.SpicyApiTalker;
import spicyglass.client.integration.system.CalendarHandler;
import spicyglass.client.model.VehicleState;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "SpicyLoginPrefs";

    @Override
    protected void onCreate(Bundle SavedInstanceSTate){
        super.onCreate(SavedInstanceSTate);
        setContentView(R.layout.main_login);

        Button login = findViewById(R.id.LogBut);
        EditText username = findViewById(R.id.Email);
        EditText password = findViewById(R.id.Password);

        SharedPreferences login_info = getSharedPreferences(PREFS_NAME, 0);
        String token = login_info.getString("token","");

        if (!token.isEmpty()){
            //Go ahead and store the token here so we don't have to copy the sharedpreferences stuff into the validatedToken function
            //If it is invalid, it will just be overwritten when the user logs in.
            VehicleState.INSTANCE.setToken(token);
            SpicyApiTalker.checkToken(token, this::validatedToken);
        }

        login.setOnClickListener(v -> {
            //within the on click if login info is correct then you apply these if not give error msg
            SpicyApiTalker.attemptLogin(username.getText().toString(), password.getText().toString(), this::finishLogin);
            //Calendar integration testing stuff
            Calendar beginTime =  Calendar.getInstance();
            beginTime.setTime(new Date(2020, 3, 1));
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(new Date(2020, 4, 1));
            CalendarHandler.requestEvent(this, beginTime, endTime);
        });

    }

    public Unit validatedToken(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            switchToMainActivity();
        } else {
            //We probably don't need any error handling for this one, if the token isn't valid the user will just have to log in.
            //However, show it for now in case we get something unexpected
            Toast.makeText(getApplicationContext(), response.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public Unit finishLogin(APIResponse<String> response) {
        if(response.getSuccess()) {
            SharedPreferences loginPreferences = getSharedPreferences(PREFS_NAME, 0);
            loginPreferences.edit()
                    .putString("token", response.getResponse())
                    .apply();
            VehicleState.INSTANCE.setToken(response.getResponse());
            //Start the Subscriber on a new thread, DO NOT do it on the main thread
            new Thread(() -> PubSubSubscriber.init(LoginActivity.this)).start();
            switchToMainActivity();
        } else {
            Toast.makeText(getApplicationContext(), response.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public void switchToMainActivity(){
        //Start the state retrieval before we switch to the main screen. This doesn't necessarily mean it'll finish.
        VehicleState.getStates();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
