package spicyglass.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "LoginPrefs";
    Button login;


    @Override
    protected void onCreate(Bundle SavedInstanceSTate){
        super.onCreate(SavedInstanceSTate);
        setContentView(R.layout.main_login);

        login = (Button) findViewById(R.id.LogBut);
        EditText username = (EditText) findViewById(R.id.Email);
        EditText password = (EditText) findViewById(R.id.Password);

        SharedPreferences login_info = getSharedPreferences("logged", 0);


        if (login_info.getString("logged","").toString().equals("logged")){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //within the on click if login info is correct then you apply these if not give error msg
                //if(username.getText().toString().equals("spicy@ttu.edu") && password.getText().toString().equals("glass")){
                    SharedPreferences login_info = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = login_info.edit();
                    editor.putString("logged","logged");
                    editor.apply();
                    LoginActivity.this.SwitchMainActivity();
                //}
                //else{
                    Toast.makeText(getApplicationContext(), "Stop Trying To Hack Someone!", Toast.LENGTH_SHORT).show();
                //}

            }
        });

    }

    public void SwitchMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
