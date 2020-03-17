package spicyglass.client.ui.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;


public class DefrostActivity extends AppCompatActivity implements View.OnClickListener {
    Button Start, Stop, Calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defrost_page);

        Start = (Button) findViewById(R.id.DefStartBut);
        Start.setOnClickListener(this);
        Stop = (Button) findViewById(R.id.DefStopBut);
        Stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.DefStartBut:
                Start.setVisibility(View.INVISIBLE);
                Stop.setVisibility(View.VISIBLE);
                Start.setEnabled(false);
                Stop.setEnabled(true);
                break;

            case R.id.DefStopBut:
                Start.setVisibility(View.VISIBLE);
                Stop.setVisibility(View.INVISIBLE);
                Start.setEnabled(true);
                Stop.setEnabled(false);
        }

    }
}
