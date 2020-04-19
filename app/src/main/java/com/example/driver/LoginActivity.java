package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_land);

        getSupportActionBar().hide();
        if (!CheckPermissions())RequestPermissions();

        Button loginButton=findViewById(R.id.login_button);
        Button QR_button=findViewById(R.id.qr_code);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                 startActivity(mainIntent);
              finish();
            }
        });
        QR_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, QrLoginActivity.class);
                startActivity(mainIntent);
            }
        });

    }
    public boolean CheckPermissions() {
        int result1 = ActivityCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ActivityCompat.checkSelfPermission(getApplicationContext(), INTERNET);

        int result3 = ActivityCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        int result4 = ActivityCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result5 = ActivityCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
        return result2 == PackageManager.PERMISSION_GRANTED
                &&result1 == PackageManager.PERMISSION_GRANTED
                &&result3 == PackageManager.PERMISSION_GRANTED
                &&result4 == PackageManager.PERMISSION_GRANTED
                &&result5 == PackageManager.PERMISSION_GRANTED;

    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE,ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION}, 1);
    }

}
