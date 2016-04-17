package com.voicesprint.variable_j.voicesprint;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by ashmin on 17/04/16.
 */
public class Audio
{
    private int audioSource = MediaRecorder.AudioSource.MIC;
    private int samplingRate = 8000; /* in Hz*/
    private int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int bufferSize = AudioRecord.getMinBufferSize(samplingRate, channelConfig, audioFormat);
    private int sampleNumBits = 16;
    private int numChannels = 1;
    private boolean isRecording = false;
    private AudioRecord recorder = null;
    private Thread recordingThread = null;

    public Audio()
    {
        recorder = new AudioRecord(audioSource, samplingRate, channelConfig, audioFormat, bufferSize);

    }

    /*public void startRecording() {


        recorder.startRecording();
        isRecording = true;
        recordingThread = new Thread(new Runnable() {

            @Override
            public void run() {

                ByteBuffer bData = ByteBuffer.allocate(bufferSize);
                byte bbarray[] = new byte[bData.remaining()];
                bData.get(bbarray);
                while (isRecording) {

                    int result = recorder.read(bbarray, 0, bufferSize);
                    System.out.println("READ DATA");
                    if (result > 0) {
                        //our algorithm
                        System.out.println("READING");
                    } else if (result == AudioRecord.ERROR_INVALID_OPERATION) {
                        Log.e("Recording", "Invalid operation error");
                        break;
                    } else if (result == AudioRecord.ERROR_BAD_VALUE) {
                        Log.e("Recording", "Bad value error");
                        break;
                    } else if (result == AudioRecord.ERROR) {
                        Log.e("Recording", "Unknown error");
                        break;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    private void stopRecording() {
        // stops the recording activity
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
        }
    }
*/}
