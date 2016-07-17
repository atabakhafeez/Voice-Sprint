package com.voicesprint.variable_j.voicesprint;

import android.util.Log;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * Class that takes audio input from mic and calculates the pitch of the input.
 * @author atabakh
 * Created by atabakh on 25/05/2016.
 */
public class PitchDetector {

    static final String TAG = "PitchDetector";

    static final float MOVEMENT_SPEED_PARAM = (float) 50.0;

    static final float MINIMUM_PITCH = 100;

    /**
     * The pitch in Hz
     */
    private float pitchInHz;

    /**
     * The audio dispatcher which gets the sound from the microphone
     */
    private AudioDispatcher dispatcher;

    /**
     * The gamepanel which this PitchDetector class instance is connected to
     */
    private GamePanel gamePanel;

    /**
     * The sum of the detected pitches for the high score functionality
     */
    private float pitchSum;

    /**
     * Boolean to check whether there is any input
     */
    private boolean noSound;

    boolean scoreSumStarted;

    /**
     * Constructor for PitchDetector
     * @param gamePanel
     */
    public PitchDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        pitchSum = 0;
        scoreSumStarted = false;
        run();
    }

    /**
     * Runs the PitchDetector
     */
    private void run() {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
                22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                pitchInHz = pitchDetectionResult.getPitch();
                if (pitchInHz >= MINIMUM_PITCH) {
                    scoreSumStarted = true;
                }
                if (scoreSumStarted) {
                    pitchSum += pitchInHz;
                }
                Log.i(TAG, "Pitch = " + pitchInHz);

                //Set vector for speed
                //TODO: Refactor to remove coupling
                gamePanel.getBg().setVector((int)(-pitchInHz/MOVEMENT_SPEED_PARAM));
            }
        }));
        new Thread(dispatcher, "Audio Dispatcher").start();
    }

    /**
     * Stops the AudioDispatcher from listening and processing sound input
     */
    public void stop() {
        dispatcher.stop();
    }

    /**
     * Getter for pitch
     * @return pitch
     */
    public float getPitchInHz () {
        return pitchInHz;
    }

    public float getPitchSum() { return pitchSum; }
}
