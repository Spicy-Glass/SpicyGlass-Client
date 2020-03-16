package spicyglass.client.ui.screens.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class VehicleActivity extends AppCompatActivity {
    Button Add_Veh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_page);

        Add_Veh = (Button) findViewById(R.id.Add_Veh);


        Add_Veh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleActivity.this.AddVehicle();
            }
        });
    }

    public void AddVehicle(){
        Intent i = new Intent(this, VehLogActivity.class);
        startActivity(i);
        finish();
    }
}
