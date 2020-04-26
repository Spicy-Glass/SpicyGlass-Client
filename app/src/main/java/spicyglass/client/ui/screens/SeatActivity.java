package spicyglass.client.ui.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import kotlin.Unit;
import spicyglass.client.R;
import spicyglass.client.integration.external.APIResponse;
import spicyglass.client.integration.external.SpicyApiTalker;
import spicyglass.client.model.VehicleState;

public class SeatActivity extends AppCompatActivity {
    ImageButton FLeft, FRight, BLeft, BRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_page);

        FLeft = findViewById(R.id.FLeft);
        FLeft.setOnClickListener(c -> SpicyApiTalker.updateSeatHeaterState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT_LEFT, !VehicleState.INSTANCE.getFrontLeftSeatHeating(), this::onFrontLeftToggled));
        FRight = findViewById(R.id.FRight);
        FRight.setOnClickListener(c -> SpicyApiTalker.updateSeatHeaterState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT_RIGHT, !VehicleState.INSTANCE.getFrontRightSeatHeating(), this::onFrontRightToggled));
        BLeft = findViewById(R.id.BLeft);
        BLeft.setOnClickListener(c -> SpicyApiTalker.updateSeatHeaterState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR_LEFT, !VehicleState.INSTANCE.getRearLeftSeatHeating(), this::onRearLeftToggled));
        BRight = findViewById(R.id.BRight);
        BRight.setOnClickListener(c -> SpicyApiTalker.updateSeatHeaterState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR_RIGHT, !VehicleState.INSTANCE.getRearRightSeatHeating(), this::onRearRightToggled));

        //Set the function to be called when the state of the seat heaters updates
        VehicleState.setSeatHeatingUpdatedFunc(this::onStateChanged);
        //Set the states when it opens
        onStateChanged(VehicleState.INSTANCE.getFrontLeftSeatHeating(), VehicleState.INSTANCE.getFrontRightSeatHeating(), VehicleState.INSTANCE.getRearLeftSeatHeating(), VehicleState.INSTANCE.getRearRightSeatHeating());
    }

    @Override
    protected void onDestroy() {
        //Clear the updated function because this screen will no longer be open, so we won't need to monitor for updates to the seat heaters.
        VehicleState.setSeatHeatingUpdatedFunc(null);
        super.onDestroy();
    }

    public Unit onStateChanged(boolean frontLeftSeatHeating, boolean frontRightSeatHeating, boolean rearLeftSeatHeating, boolean rearRightSeatHeating) {
        this.runOnUiThread(() -> {
            FLeft.setColorFilter(getResources().getColor(frontLeftSeatHeating ? R.color.Red : R.color.Black, null), PorterDuff.Mode.SRC_IN);
            FRight.setColorFilter(getResources().getColor(frontRightSeatHeating ? R.color.Red : R.color.Black, null), PorterDuff.Mode.SRC_IN);
            BLeft.setColorFilter(getResources().getColor(rearLeftSeatHeating ? R.color.Red : R.color.Black, null), PorterDuff.Mode.SRC_IN);
            BRight.setColorFilter(getResources().getColor(rearRightSeatHeating ? R.color.Red : R.color.Black, null), PorterDuff.Mode.SRC_IN);
        });
        return null;
    }

    public Unit onFrontLeftToggled(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateSeatHeaters(!VehicleState.INSTANCE.getFrontLeftSeatHeating(), VehicleState.INSTANCE.getFrontRightSeatHeating(), VehicleState.INSTANCE.getRearLeftSeatHeating(), VehicleState.INSTANCE.getRearRightSeatHeating());
        }
        return null;
    }

    public Unit onFrontRightToggled(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateSeatHeaters(VehicleState.INSTANCE.getFrontLeftSeatHeating(), !VehicleState.INSTANCE.getFrontRightSeatHeating(), VehicleState.INSTANCE.getRearLeftSeatHeating(), VehicleState.INSTANCE.getRearRightSeatHeating());
        }
        return null;
    }

    public Unit onRearLeftToggled(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateSeatHeaters(VehicleState.INSTANCE.getFrontLeftSeatHeating(), VehicleState.INSTANCE.getFrontRightSeatHeating(), !VehicleState.INSTANCE.getRearLeftSeatHeating(), VehicleState.INSTANCE.getRearRightSeatHeating());
        }
        return null;
    }

    public Unit onRearRightToggled(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateSeatHeaters(VehicleState.INSTANCE.getFrontLeftSeatHeating(), VehicleState.INSTANCE.getFrontRightSeatHeating(), VehicleState.INSTANCE.getRearLeftSeatHeating(), !VehicleState.INSTANCE.getRearRightSeatHeating());
        }
        return null;
    }
}