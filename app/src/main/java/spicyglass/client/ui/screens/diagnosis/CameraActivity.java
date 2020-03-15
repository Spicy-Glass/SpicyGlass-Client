package spicyglass.client.ui.screens.diagnosis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import spicyglass.client.R;

public class CameraActivity extends AppCompatActivity {
    Button BCamera,FCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_page);

        BCamera = (Button) findViewById(R.id.front_but);
        FCamera = (Button) findViewById(R.id.back_but);

        BCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Framework for back camera
            }
        });

        FCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Framework for dash camera
            }
        });
    }
}

