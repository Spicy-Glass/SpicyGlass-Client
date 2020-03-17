package spicyglass.client.ui.screens;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class SeatActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton FLeft, FRight, BLeft, BRight;
    int FLeftState=1;
    int FRightState=1;
    int BLeftState=1;
    int BRightState=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_page);

        FLeft = (ImageButton) findViewById(R.id.FLeft);
        FLeft.setOnClickListener(this);
        FRight = (ImageButton) findViewById(R.id.FRight);
        FRight.setOnClickListener(this);
        BLeft = (ImageButton) findViewById(R.id.BLeft);
        BLeft.setOnClickListener(this);
        BRight = (ImageButton) findViewById(R.id.BRight);
        BRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.FLeft:
                FLeftState++;
                if (FLeftState % 2 == 0) {
                    FLeft.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_IN);
                }
                else{
                    FLeft.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_IN);
                }
                break;

            case R.id.FRight:
                FRightState++;
                if (FRightState % 2 == 0) {
                    FRight.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_IN);
                }
                else{
                    FRight.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_IN);
                }
                break;

            case R.id.BLeft:
                BLeftState++;
                if (BLeftState % 2 == 0) {
                    BLeft.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_IN);
                }
                else{
                    BLeft.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_IN);
                }
                break;

            case R.id.BRight:
                BRightState++;
                if (BRightState % 2 == 0) {
                    BRight.setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_IN);
                }
                else{
                    BRight.setColorFilter(getResources().getColor(R.color.Black), PorterDuff.Mode.SRC_IN);
                }
                break;

        }

    }
}