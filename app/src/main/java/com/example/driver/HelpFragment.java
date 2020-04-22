package com.example.driver;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class HelpFragment extends Fragment {

    public HelpFragment() {
        // Required empty public constructor
    }
    private Button btnSpeak;
    private EditText txtText;
    MyTextToSpeak myTextToSpeek;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_help, container, false);
        myTextToSpeek=new MyTextToSpeak(getContext());

        btnSpeak = root.findViewById(R.id.btnSpeak);

        txtText =  root.findViewById(R.id.txtText);
        final MediaPlayer errorSound = MediaPlayer.create(getContext(), R.raw.error_sound);

        // button on click event
        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                myTextToSpeek.speakOut(txtText.getText().toString(),errorSound);
            }

        });
        return root;
    }


}
