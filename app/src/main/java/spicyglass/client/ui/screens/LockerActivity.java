package spicyglass.client.ui.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import kotlin.Unit;
import spicyglass.client.R;
import spicyglass.client.integration.external.APIResponse;
import spicyglass.client.integration.external.SpicyApiTalker;
import spicyglass.client.model.VehicleState;

public class LockerActivity extends AppCompatActivity {
    ImageButton FLeftU, FLeftL, FRightU, FRightL, BLeftU, BLeftL, BRightU, BRightL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_page);

        FLeftU = findViewById(R.id.FLeftUnlock);
        FLeftU.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT_LEFT, true, this::onLockFrontLeft));
        FLeftL = findViewById(R.id.FLeftLock);
        FLeftL.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT_LEFT, false, this::onUnlockFrontLeft));
        FRightU = findViewById(R.id.FRightUnlock);
        FRightU.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT_RIGHT, true, this::onLockFrontRight));
        FRightL = findViewById(R.id.FRightLock);
        FRightL.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.FRONT_RIGHT, false, this::onUnlockFrontRight));
        BLeftU = findViewById(R.id.BLeftUnlock);
        BLeftU.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR_LEFT, true, this::onLockRearLeft));
        BLeftL = findViewById(R.id.BLeftLock);
        BLeftL.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR_LEFT, false, this::onUnlockRearLeft));
        BRightU = findViewById(R.id.BRightUnlock);
        BRightU.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR_RIGHT, true, this::onLockRearRight));
        BRightL = findViewById(R.id.BRightLock);
        BRightL.setOnClickListener(c -> SpicyApiTalker.updateLockState(VehicleState.INSTANCE.getVehicleId(), SpicyApiTalker.REAR_RIGHT, false, this::onUnlockRearRight));

        //Set the function to be called when the state of the locks updates
        VehicleState.setLockUpdatedFunc(this::onStateChanged);
        //Set the states when it opens
        onStateChanged(VehicleState.INSTANCE.getFrontLeftLocked(), VehicleState.INSTANCE.getFrontRightLocked(), VehicleState.INSTANCE.getRearLeftLocked(), VehicleState.INSTANCE.getRearRightLocked());
    }

    @Override
    protected void onDestroy() {
        //Clear the updated function because this screen will no longer be open, so we won't need to monitor for updates to the locks.
        VehicleState.setLockUpdatedFunc(null);
        super.onDestroy();
    }
    
    public Unit onStateChanged(boolean frontLeftLocked, boolean frontRightLocked, boolean rearLeftLocked, boolean rearRightLocked) {
        this.runOnUiThread(() -> {
            FLeftU.setVisibility(!frontLeftLocked ?View.VISIBLE :View.INVISIBLE);
            FLeftL.setVisibility(!frontLeftLocked ?View.INVISIBLE :View.VISIBLE);
            FLeftU.setEnabled(!frontLeftLocked);
            FLeftL.setEnabled(frontLeftLocked);

            FRightU.setVisibility(!frontRightLocked ?View.VISIBLE :View.INVISIBLE);
            FRightL.setVisibility(!frontRightLocked ?View.INVISIBLE :View.VISIBLE);
            FRightU.setEnabled(!frontRightLocked);
            FRightL.setEnabled(frontRightLocked);

            BLeftU.setVisibility(!rearLeftLocked ?View.VISIBLE :View.INVISIBLE);
            BLeftL.setVisibility(!rearLeftLocked ?View.INVISIBLE :View.VISIBLE);
            BLeftU.setEnabled(!rearLeftLocked);
            BLeftL.setEnabled(rearLeftLocked);

            BRightU.setVisibility(!rearRightLocked ?View.VISIBLE :View.INVISIBLE);
            BRightL.setVisibility(!rearRightLocked ?View.INVISIBLE :View.VISIBLE);
            BRightU.setEnabled(!rearRightLocked);
            BRightL.setEnabled(rearRightLocked);
        });
        return null;
    }

    public Unit onUnlockFrontLeft(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(false, VehicleState.INSTANCE.getFrontRightLocked(), VehicleState.INSTANCE.getRearLeftLocked(), VehicleState.INSTANCE.getRearRightLocked());
        }
        return null;
    }

    public Unit onLockFrontLeft(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(true, VehicleState.INSTANCE.getFrontRightLocked(), VehicleState.INSTANCE.getRearLeftLocked(), VehicleState.INSTANCE.getRearRightLocked());
        }
        return null;
    }

    public Unit onUnlockFrontRight(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(VehicleState.INSTANCE.getFrontLeftLocked(), false, VehicleState.INSTANCE.getRearLeftLocked(), VehicleState.INSTANCE.getRearRightLocked());
        }
        return null;
    }

    public Unit onLockFrontRight(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(VehicleState.INSTANCE.getFrontLeftLocked(), true, VehicleState.INSTANCE.getRearLeftLocked(), VehicleState.INSTANCE.getRearRightLocked());
        }
        return null;
    }

    public Unit onUnlockRearLeft(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(VehicleState.INSTANCE.getFrontLeftLocked(), VehicleState.INSTANCE.getFrontRightLocked(), false, VehicleState.INSTANCE.getRearRightLocked());
        }
        return null;
    }

    public Unit onLockRearLeft(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(VehicleState.INSTANCE.getFrontLeftLocked(), VehicleState.INSTANCE.getFrontRightLocked(), true, VehicleState.INSTANCE.getRearRightLocked());
        }
        return null;
    }

    public Unit onUnlockRearRight(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(VehicleState.INSTANCE.getFrontLeftLocked(), VehicleState.INSTANCE.getFrontRightLocked(), VehicleState.INSTANCE.getRearLeftLocked(), false);
        }
        return null;
    }

    public Unit onLockRearRight(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(VehicleState.INSTANCE.getFrontLeftLocked(), VehicleState.INSTANCE.getFrontRightLocked(), VehicleState.INSTANCE.getRearLeftLocked(), true);
        }
        return null;
    }
}
