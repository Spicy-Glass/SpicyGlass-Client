package spicyglass.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.integration.system.HelloWorld;
import spicyglass.client.ui.screens.SettingActivity;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id., MainFragment.newInstance())
                    .commitNow();
        }*/

            button = (Button) findViewById(R.id.setting);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSettings();
                }
            });

            HelloWorld.helloWorld();
        }
        public void openSettings(){
            Intent intent = new Intent (this, SettingActivity.class);
            startActivity(intent);
        }

}

