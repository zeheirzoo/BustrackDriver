package com.example.driver;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.Locale;
public class MyTextToSpeak implements TextToSpeech.OnInitListener {
    TextToSpeech tts;
    Context context;
    public MyTextToSpeak(Context context) {
        this.tts=new TextToSpeech(context,this);

    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.getDefault());
//
             tts.setPitch(-5); // set pitch level
//
//             tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
                speakOut("",null);
            }

        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    public void speakOut(final String text,final MediaPlayer mediaPlayer) {
        if (mediaPlayer!=null&&!text.equals("")) {
            mediaPlayer.start();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //new intent here
                    mediaPlayer.stop();
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
            }, 1200);
        }
    }



}
