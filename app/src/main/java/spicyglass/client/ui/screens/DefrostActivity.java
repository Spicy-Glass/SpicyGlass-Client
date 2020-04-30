package spicyglass.client.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import kotlin.Unit;
import spicyglass.client.R;
import spicyglass.client.integration.external.APIResponse;
import spicyglass.client.integration.external.SpicyApiTalker;
import spicyglass.client.integration.external.WeatherHandler;
import spicyglass.client.model.VehicleState;


public class DefrostActivity extends AppCompatActivity implements View.OnClickListener {
    TextView result;
    Button startFront, stopFront, startRear, stopRear, calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.defrost_page);

        startFront = findViewById(R.id.DefStartBut);
        startFront.setOnClickListener(c -> SpicyApiTalker.updateDefrostState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT, true, this::onStartFront));
        stopFront = findViewById(R.id.DefStopBut);
        stopFront.setOnClickListener(c -> SpicyApiTalker.updateDefrostState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT, false, this::onStopFront));

        startRear = findViewById(R.id.RearStartBut);
        startRear.setOnClickListener(c -> SpicyApiTalker.updateDefrostState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR, true, this::onStartRear));
        stopRear = findViewById(R.id.RearStopBut);
        stopRear.setOnClickListener(c -> SpicyApiTalker.updateDefrostState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR, false, this::onStopRear));

        calendar = findViewById(R.id.CalendarBut);
        calendar.setOnClickListener(this);

        //Work done to display weather data
        result = findViewById(R.id.result);
        String display = WeatherHandler.displayWeather();
        result.setText(display);

        //Set the function to be called when the state of the locks updates
        VehicleState.setDefrostUpdatedFunc(this::onStateChanged);
        //Set the states when it opens
        onStateChanged(VehicleState.INSTANCE.getFrontDefrost(), VehicleState.INSTANCE.getRearDefrost());
    }

    @Override
    protected void onDestroy() {
        //Clear the updated function because this screen will no longer be open, so we won't need to monitor for updates to the defrost.
        VehicleState.setDefrostUpdatedFunc(null);
        super.onDestroy();
    }

    public Unit onStateChanged(boolean frontDefrosting, boolean rearDefrosting) {
        this.runOnUiThread(() -> {
            startFront.setVisibility(!frontDefrosting ? View.VISIBLE : View.INVISIBLE);
            stopFront.setVisibility(!frontDefrosting ? View.INVISIBLE : View.VISIBLE);
            startFront.setEnabled(!frontDefrosting);
            stopFront.setEnabled(frontDefrosting);

            startRear.setVisibility(!rearDefrosting ? View.VISIBLE : View.INVISIBLE);
            stopRear.setVisibility(!rearDefrosting ? View.INVISIBLE : View.VISIBLE);
            startRear.setEnabled(!rearDefrosting);
            stopRear.setEnabled(rearDefrosting);
        });
        return null;
    }

    public Unit onStartFront(APIResponse<Boolean> response) {
        if (response.getSuccess() && response.getResponse()) {
            VehicleState.updateDefrost(true, VehicleState.INSTANCE.getRearDefrost());
        }
        return null;
    }

    public Unit onStopFront(APIResponse<Boolean> response) {
        if (response.getSuccess() && response.getResponse()) {
            VehicleState.updateDefrost(false, VehicleState.INSTANCE.getRearDefrost());
        }
        return null;
    }

    public Unit onStartRear(APIResponse<Boolean> response) {
        if (response.getSuccess() && response.getResponse()) {
            VehicleState.updateDefrost(VehicleState.INSTANCE.getFrontDefrost(), true);
        }
        return null;
    }

    public Unit onStopRear(APIResponse<Boolean> response) {
        if (response.getSuccess() && response.getResponse()) {
            VehicleState.updateDefrost(VehicleState.INSTANCE.getFrontDefrost(), false);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.CalendarBut:
                intent = new Intent(this, DefrostScheduler.class);
                startActivity(intent);
                break;
        }
    }
}