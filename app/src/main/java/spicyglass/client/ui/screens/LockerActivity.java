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

public class LockerActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton FLeftU, FLeftL, FRightU, FRightL, BLeftU, BLeftL, BRightU, BRightL;

    //ImageButton lock, unlock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_page);

        FLeftU = (ImageButton) findViewById(R.id.FLeftUnlock);
        FLeftU.setOnClickListener(this);
        FLeftL = (ImageButton) findViewById(R.id.FLeftLock);
        FLeftL.setOnClickListener(this);
        FRightU = (ImageButton) findViewById(R.id.FRightUnlock);
        FRightU.setOnClickListener(this);
        FRightL = (ImageButton) findViewById(R.id.FRightLock);
        FRightL.setOnClickListener(this);
        BLeftU = (ImageButton) findViewById(R.id.BLeftUnlock);
        BLeftU.setOnClickListener(this);
        BLeftL = (ImageButton) findViewById(R.id.BLeftLock);
        BLeftL.setOnClickListener(this);
        BRightU = (ImageButton) findViewById(R.id.BRightUnlock);
        BRightU.setOnClickListener(this);
        BRightL = (ImageButton) findViewById(R.id.BRightLock);
        BRightL.setOnClickListener(this);

        VehicleState.setLockUpdatedFunc(this::onStateChanged);
    }

    @Override
    protected void onDestroy() {
        VehicleState.setLockUpdatedFunc(null);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.FLeftUnlock:
                SpicyApiTalker.updateLockState("V-1", true, this::onUnlock);
                break;

            case R.id.FLeftLock:
                SpicyApiTalker.updateLockState("V-1", false, this::onLock);
                break;

            case R.id.FRightUnlock:
                FRightL.setVisibility(View.VISIBLE);
                FRightU.setVisibility(View.INVISIBLE);
                FRightL.setEnabled(true);
                FRightU.setEnabled(false);
                break;

            case R.id.FRightLock:
                FRightU.setVisibility(View.VISIBLE);
                FRightL.setVisibility(View.INVISIBLE);
                FRightU.setEnabled(true);
                FRightL.setEnabled(false);
                break;

            case R.id.BLeftUnlock:
                BLeftL.setVisibility(View.VISIBLE);
                BLeftU.setVisibility(View.INVISIBLE);
                BLeftL.setEnabled(true);
                BLeftU.setEnabled(false);
                break;

            case R.id.BLeftLock:
                BLeftU.setVisibility(View.VISIBLE);
                BLeftL.setVisibility(View.INVISIBLE);
                BLeftU.setEnabled(true);
                BLeftL.setEnabled(false);
                break;

            case R.id.BRightUnlock:
                BRightL.setVisibility(View.VISIBLE);
                BRightU.setVisibility(View.INVISIBLE);
                BRightL.setEnabled(true);
                BRightU.setEnabled(false);
                break;

            case R.id.BRightLock:
                BRightU.setVisibility((View.VISIBLE));
                BRightL.setVisibility(View.INVISIBLE);
                BRightU.setEnabled(true);
                BRightL.setEnabled(false);
                break;

        }
    }
    
    public Unit onStateChanged(boolean frontLeftLocked, boolean frontRightLocked, boolean rearLeftLocked, boolean rearRightLocked) {
        FLeftU.setVisibility(frontLeftLocked ? View.VISIBLE : View.INVISIBLE);
        FLeftL.setVisibility(frontLeftLocked ? View.INVISIBLE : View.VISIBLE);
        FLeftU.setEnabled(frontLeftLocked);
        FLeftL.setEnabled(!frontLeftLocked);

        FRightU.setVisibility(frontRightLocked ? View.VISIBLE : View.INVISIBLE);
        FRightL.setVisibility(frontRightLocked ? View.INVISIBLE : View.VISIBLE);
        FRightU.setEnabled(frontRightLocked);
        FRightL.setEnabled(!frontRightLocked);

        BLeftU.setVisibility(rearLeftLocked ? View.VISIBLE : View.INVISIBLE);
        BLeftL.setVisibility(rearLeftLocked ? View.INVISIBLE : View.VISIBLE);
        BLeftU.setEnabled(rearLeftLocked);
        BLeftL.setEnabled(!rearLeftLocked);

        BRightU.setVisibility(rearRightLocked ? View.VISIBLE : View.INVISIBLE);
        BRightL.setVisibility(rearRightLocked ? View.INVISIBLE : View.VISIBLE);
        BRightU.setEnabled(rearRightLocked);
        BRightL.setEnabled(!rearRightLocked);
        return null;
    }

    public Unit onUnlock(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(false, true, true, true);
        }
        return null;
    }

    public Unit onLock(APIResponse<Boolean> response) {
        if(response.getSuccess() && response.getResponse()) {
            VehicleState.updateLocks(true, true, true, true);
        }
        return null;
    }
}
