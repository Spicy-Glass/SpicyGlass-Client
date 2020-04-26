package spicyglass.client.ui.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import kotlin.Unit;
import spicyglass.client.R;
import spicyglass.client.integration.external.APIResponse;
import spicyglass.client.integration.external.SpicyApiTalker;
import spicyglass.client.model.VehicleState;

public class AutoStartActivity extends AppCompatActivity {
    Button Start, Stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_page);

        Start = findViewById(R.id.AutoStartBut);
        Start.setOnClickListener(c -> SpicyApiTalker.updateCarOnState(VehicleState.INSTANCE.getVehicleId(),true, this::onStart));
        Stop = findViewById(R.id.AutoStopBut);
        Stop.setOnClickListener(c -> SpicyApiTalker.updateCarOnState(VehicleState.INSTANCE.getVehicleId(),false, this::onStop));

        //Set the function to be called when the state of the locks updates
        VehicleState.setCarOnUpdatedFunc(this::onStateChanged);
        //Set the states when it opens
        onStateChanged(VehicleState.INSTANCE.getCarOn());
    }

    @Override
    protected void onDestroy() {
        //Clear the updated function because this screen will no longer be open, so we won't need to monitor for updates to if the car is on.
        VehicleState.setCarOnUpdatedFunc(null);
        super.onDestroy();
    }

    public Unit onStateChanged(boolean carOn) {
        this.runOnUiThread(() -> {
            Start.setVisibility(!carOn ?View.VISIBLE :View.INVISIBLE);
            Stop.setVisibility(!carOn ?View.INVISIBLE :View.VISIBLE);
            Start.setEnabled(!carOn);
            Stop.setEnabled(carOn);
        });
        return null;
    }

    public Unit onStart(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateCarOn(true);
        }
        return null;
    }

    public Unit onStop(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateCarOn(false);
        }
        return null;
    }
}
