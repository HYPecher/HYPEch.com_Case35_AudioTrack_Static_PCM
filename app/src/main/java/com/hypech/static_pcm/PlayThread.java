package com.hypech.static_pcm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlayThread extends Thread {
    private Activity mActivity;
    private AudioTrack mAudioTrack;
    private byte[] audioData;
    private String mFileName;

    public PlayThread(Activity activity, String fileName) {
        mActivity = activity;
        mFileName = fileName;
    }

    @Override
    public void run() {
        super.run();
        try {
            try (InputStream in = mActivity.getResources().openRawResource(R.raw.ding)) {

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                for (int b; (b = in.read()) != -1; ) {
                    out.write(b);
                }
                audioData = out.toByteArray();
            }
        } catch (IOException e) {
            Log.wtf("TAG", "Failed to read", e);
        }

        // R.raw.ding铃声文件的相关属性为 22050Hz, 8-bit, Mono
        mAudioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder().setSampleRate(22050)
                        .setEncoding(AudioFormat.ENCODING_PCM_8BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build(),
                audioData.length,
                AudioTrack.MODE_STATIC,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        mAudioTrack.write(audioData, 0, audioData.length);
        mAudioTrack.play();
    }
}