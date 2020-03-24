package spicyglass.client.ui.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

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

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){


            case R.id.FLeftUnlock:
                FLeftL.setVisibility(View.VISIBLE);
                FLeftU.setVisibility(View.INVISIBLE);
                FLeftL.setEnabled(true);
                FLeftU.setEnabled(false);
                break;

            case R.id.FLeftLock:
                FLeftU.setVisibility(View.VISIBLE);
                FLeftL.setVisibility(View.INVISIBLE);
                FLeftU.setEnabled(true);
                FLeftL.setEnabled(false);
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
}
