package com.voicesprint.variable_j.voicesprint;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * @file PitchDetector.java
 * @brief Class that takes audio input from mic and calculates the pitch of the input.
 * @author atabakh
 * @bug No known bugs.
 * Created by atabakh on 25/05/2016.
 */
public class PitchDetector {

    /**
     * @brief The pitch in Hz
     */
    private float pitchInHz;

    /**
     * @brief The audio dispatcher which gets the sound from the microphone
     */
    private AudioDispatcher dispatcher;

    /**
     * @brief The gamepanel which this PitchDetector class instance is connected to
     */
    private GamePanel gamePanel;

    /**
     * @brief The sum of the detected pitches for the high score functionality
     */
    private float pitchSum;

    /**
     * @brief Boolean to check whether there is any input
     */
    private boolean noSound;

    /**
     * @brief Constructor for PitchDetector
     * @param gamePanel
     */
    public PitchDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        pitchSum = 0;
        run();
    }

    /**
     * @brief Runs the PitchDetector
     */
    private void run() {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
                22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                pitchInHz = pitchDetectionResult.getPitch();
                System.out.println("Pitch = " + pitchInHz);
                pitchSum += 0;

                //Set vector for speed
                //TODO: Refactor to remove coupling
                gamePanel.getBg().setVector((int)(-pitchInHz/10.0));
            }
        }));
        new Thread(dispatcher, "Audio Dispatcher").start();
    }

    /**
     * @brief Stops the AudioDispatcher from listening and processing sound input
     */
    public void stop() {
        dispatcher.stop();
    }

    /**
     * @brief Getter for pitch in string format for displaying on the screen
     * @return pitch in string format
     */
    public String getPitchInString () {
        return Float.toString(pitchInHz);
    }
}
