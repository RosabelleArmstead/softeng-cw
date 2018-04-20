package com2027.cw.group7.resteasy;

import java.io.IOException;
import android.media.MediaRecorder;

/**
 * Uses the microphone to record audio
 *
 * Created with help from: http://s2ptech.blogspot.gr/2014/01/how-to-detect-noise-in-android.html
 */
public class SoundMeter {

    private MediaRecorder mRecorder = null;


    /**
     * Start recording
     */
    public void start() {

        if (mRecorder == null) {

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");

            try {
                mRecorder.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mRecorder.start();

        }
    }

    /**
     * Stot recording
     */
    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * Get amplitude from the sound input
     * @return current amplitude or 0 if not recording
     */
    public double getAmplitude() {
        if (mRecorder != null)
            return  (mRecorder.getMaxAmplitude()/2700.0);
        else
            return 0;

    }


}