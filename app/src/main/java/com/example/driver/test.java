package com.example.driver;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class test extends Activity{
    /** Called when the activity is first created. */

    private Button btnSpeak;
    private EditText txtText;
    MyTextToSpeak myTextToSpeek;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
myTextToSpeek=new MyTextToSpeak(getApplicationContext());

        btnSpeak = findViewById(R.id.btnSpeak);

        txtText =  findViewById(R.id.txtText);
        final MediaPlayer errorSound = MediaPlayer.create(this, R.raw.error_sound);

        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                myTextToSpeek.speakOut(txtText.getText().toString(),errorSound);
            }

        });
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown!

        super.onDestroy();
    }






}
