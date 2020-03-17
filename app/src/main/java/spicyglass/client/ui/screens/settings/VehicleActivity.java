package spicyglass.client.ui.screens.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class VehicleActivity extends AppCompatActivity {

    Button Add_Veh;
    TextView Test1, Text2, Text3;
    TableLayout VehTab;
    TableRow VehRow;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_page);

        Add_Veh = (Button) findViewById(R.id.AddVeh);
        Test1 = (TextView) findViewById(R.id.TestRowView);
        VehTab = (TableLayout) findViewById(R.id.VehTab);
        //TODO find a work around for a way to get an if statement to check line below and update from there
        //st = getIntent().getExtras().getString("Value");



        //if(!st.isEmpty()){
        //    RowAddition();
        //}

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
    }

    protected void RowAddition(){
        VehRow = new TableRow(this);
        Text2 = new TextView(this);
        Text2.setText(st);
        Text2.setGravity(Gravity.CENTER);
        Text2.setTextSize(15);
        VehRow.addView(Text2);
        VehTab.addView(VehRow);
    }
}
