package spicyglass.client.ui.screens.settings;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class VehLogActivity extends AppCompatActivity implements View.OnClickListener {

    Button Submit;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_add_page);

        Submit = (Button) findViewById(R.id.VehSubmit);
        Submit.setOnClickListener(this);

    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.VehSubmit:
                //RowAddition();
                VehicleAddition();
                //RowAddition();
                break;

            default:
                break;
        }
    }



    protected void VehicleAddition(){
        Intent i = new Intent(this, VehicleActivity.class);
        st = "hello";
        i.putExtra("Value", st);
        i.putExtra("counter",0);
        startActivity(i);
        finish();
    }

}
/*

if (!Nickname.getText().toString().isEmpty() && !Maker.getText().toString().isEmpty() &&
                        !Type.getText().toString().isEmpty() &&  !Year.getText().toString().isEmpty())

    tr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // Create a Button to be the row-content.
            Button b = new Button(this);
            b.setText("Dynamic Button");
            b.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            // Add Button to row.
            tr.addView(b);
            // Add row to TableLayout.
            //tr.setBackgroundResource(R.drawable.sf_gradient_03);
            tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

*/

