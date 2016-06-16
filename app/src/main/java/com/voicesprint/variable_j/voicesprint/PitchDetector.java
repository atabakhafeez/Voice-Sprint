package com.voicesprint.variable_j.voicesprint;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

/**
 * Created by atabakh on 25/05/2016.
 */
public class PitchDetector {

    private float pitchInHz;
    private AudioDispatcher dispatcher;
    private GamePanel gamePanel;

    public PitchDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        run();
    }

    private void run() {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN,
                22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                pitchInHz = pitchDetectionResult.getPitch();
                System.out.println("Pitch = " + pitchInHz);
                //Set vector for speed
                gamePanel.getBg().setVector((int)(-pitchInHz/10.0));
            }
        }));
        new Thread(dispatcher, "Audio Dispatcher").start();
    }

    public void stop() {
        dispatcher.stop();
    }

    public String getPitchInString () {
        return Float.toString(pitchInHz);
    }
}
