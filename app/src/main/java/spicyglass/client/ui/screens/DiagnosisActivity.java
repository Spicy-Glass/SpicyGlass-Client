package spicyglass.client.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;
import spicyglass.client.ui.screens.diagnosis.CameraActivity;
import spicyglass.client.ui.screens.diagnosis.FuelActivity;
import spicyglass.client.ui.screens.diagnosis.MCodeActivity;
import spicyglass.client.ui.screens.diagnosis.TiresActivity;

public class DiagnosisActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosis_page);

        button1 = findViewById(R.id.fuel_but);
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.Tires_but);
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.Maintenance_but);
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.Camera_but);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()) {

            case R.id.fuel_but:
                intent = new Intent(this, FuelActivity.class);
                startActivity(intent);
                break;

            case R.id.Tires_but:
                intent = new Intent(this, TiresActivity.class);
                startActivity(intent);
                break;

            case R.id.Maintenance_but:
                intent = new Intent(this, MCodeActivity.class);
                startActivity(intent);
                break;

            case R.id.Camera_but:
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;

        }


    }
}
