package com.voicesprint.variable_j.voicesprint;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 856;
    public static final int HEIGHT = 480;
    private MainThread thread;
    private Background bg;
    private Superman superman;
    private float pitchInHz;
    private AudioDispatcher dispatcher;

    public GamePanel(Context context)
    {
        super(context);


        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        //voice = new VoiceInput();

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
                dispatcher.stop();

            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.full_background));
        superman = new Superman(BitmapFactory.decodeResource(getResources(), R.drawable.superman), 1000, 1000);
        bg.setVector(-5);
        //we can safely start the game loop
        thread.setRunning(true);

        thread.start();

        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, new PitchDetectionHandler() {

            @Override
            public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                    AudioEvent audioEvent) {
                pitchInHz = pitchDetectionResult.getPitch();
                System.out.println("Pitch = " + pitchInHz);
                bg.setVector((int)(-pitchInHz/10.0));
            }
        }));
        new Thread(dispatcher, "Audio Dispatcher").start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    public void update()
    {

        bg.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        String pitchStr = Float.toString(pitchInHz);
        bg.draw(canvas);
        super.draw(canvas);
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            superman.draw(canvas, pitchStr);
            canvas.restoreToCount(savedState);
        }
    }
}