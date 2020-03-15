package spicyglass.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.ui.screens.AutoActivity;
import spicyglass.client.ui.screens.DefrostActivity;
import spicyglass.client.ui.screens.DiagnosisActivity;
import spicyglass.client.ui.screens.LockerActivity;
import spicyglass.client.ui.screens.SeatActivity;
import spicyglass.client.ui.screens.SettingActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4, button5, button6, button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        button1 = findViewById(R.id.setting);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.diagnosis);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.defrost);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.seat);
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.locker);
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.Auto);
        button6.setOnClickListener(this);
        }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {

            case R.id.setting:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.diagnosis:
                intent = new Intent(this, DiagnosisActivity.class);
                startActivity(intent);
                break;

            case R.id.defrost:
                intent = new Intent(this, DefrostActivity.class);
                startActivity(intent);
                break;

            case R.id.seat:
                intent = new Intent(this, SeatActivity.class);
                startActivity(intent);
                break;

            case R.id.locker:
                intent = new Intent(this, LockerActivity.class);
                startActivity(intent);
                break;

            case R.id.Auto:
                intent = new Intent(this, AutoActivity.class);
                startActivity(intent);
                break;
                
        }
            //HelloWorld.helloWorld();
    }
}

