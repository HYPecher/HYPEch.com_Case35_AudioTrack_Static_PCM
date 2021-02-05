package com.hypech.static_pcm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PlayThread mPlayThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = (Button) findViewById(R.id.btn_play);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play){
            if (null != mPlayThread) {
                mPlayThread.stop();
                mPlayThread = null;
            }
            mPlayThread = new PlayThread(this, "raw/tts1.pcm");
            mPlayThread.start();
        }
    }
}