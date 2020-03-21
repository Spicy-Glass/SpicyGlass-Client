package spicyglass.client.ui.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class AutoStartActivity extends AppCompatActivity implements View.OnClickListener {
    Button Start, Stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_page);

        Start = (Button) findViewById(R.id.AutoStartBut);
        Start.setOnClickListener(this);
        Stop = (Button) findViewById(R.id.AutoStopBut);
        Stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.AutoStartBut:
                Start.setVisibility(View.INVISIBLE);
                Stop.setVisibility(View.VISIBLE);
                Start.setEnabled(false);
                Stop.setEnabled(true);
                break;

            case R.id.AutoStopBut:
                Start.setVisibility(View.VISIBLE);
                Stop.setVisibility(View.INVISIBLE);
                Start.setEnabled(true);
                Stop.setEnabled(false);
                break;
        }

    }
}
