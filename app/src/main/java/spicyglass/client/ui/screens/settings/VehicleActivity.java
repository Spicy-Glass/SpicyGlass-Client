package spicyglass.client.ui.screens.settings;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class VehicleActivity extends AppCompatActivity {

    Switch Add_Veh1, Add_Veh2;
    TextView Vehicle1, Vehicle2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_page);

        Vehicle1 = (TextView) findViewById(R.id.textV1);
        Vehicle2 = (TextView) findViewById(R.id.textV2);

        Add_Veh1 = (Switch) findViewById(R.id.switch1);
        Add_Veh2 = (Switch) findViewById(R.id.switch2);
        Vehicle1 = (TextView) findViewById(R.id.textV1);
        Vehicle2 = (TextView) findViewById(R.id.textV2);

        Add_Veh2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vehicle2.setText("hello from V2");
            }
        });

        Add_Veh1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Vehicle1.setText("hello from v1");
            }
        });

    }// oncreate end

}//class end
