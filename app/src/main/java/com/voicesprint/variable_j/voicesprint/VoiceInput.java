package com.voicesprint.variable_j.voicesprint;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by atabakh on 17/04/2016.
 */
class VoiceInput extends Thread {
    private static final String LOG_TAG = "VoiceInput";
    private boolean stopped    = false;

    public VoiceInput() {

        start();
    }

    @Override
    public void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        AudioRecord recorder = null;
        short[][]   buffers  = new short[256][160];
        int         ix       = 0;

        try { // ... initialise

            int N = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);

            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    8000,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    N*10);

            recorder.startRecording();
            Log.i(LOG_TAG,"Audio Recording started");

            // ... loop

            while(!stopped) {
                short[] buffer = buffers[ix++ % buffers.length];

                N = recorder.read(buffer,0,buffer.length);
                //process is what you will do with the data...not defined here
                process(buffer);
                //System.out.println("Buffer is here!");
            }
        } catch(Throwable x) {
            /*new Log("Error reading voice audio",x);*/
        } finally {
            close();
        }
    }

    private void close() {
        stopped = true;
    }

    private float process(short[] buffer) {
        System.out.println("process starts");
        for (short b: buffer
             ) {
            System.out.print(b+ " ");
        }
        return 0;
    }

}