package spicyglass.client.ui.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.LoginActivity;
import spicyglass.client.R;
import spicyglass.client.ui.screens.settings.VehicleActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    Button LogOut, Vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        LogOut = (Button) findViewById(R.id.logout_but);
        LogOut.setOnClickListener(this);
        Vehicle = (Button) findViewById(R.id.Vehicle_but);
        Vehicle.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){

            case R.id.logout_but:
                SharedPreferences login_info = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = login_info.edit();
                editor.remove("token");
                editor.apply();
                SettingActivity.this.SwitchToLogin();
                break;

            case R.id.Vehicle_but:
                Intent i = new Intent(this, VehicleActivity.class);
                startActivity(i);
                break;

        }

    }

    public void SwitchToLogin(){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finishAffinity();
    }
}
