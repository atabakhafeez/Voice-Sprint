package com.voicesprint.variable_j.voicesprint;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * GamePanel Class that extends SurfaceView and implements SurfaceHolder.Callback. This class
 * acts as the view and functionality of the game
 * @author atabakh
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * Width of the background image
     */
    public static final int WIDTH = 856;

    /**
     * Height of the background image
     */
    public static final int HEIGHT = 480;

    /**
     * The thread instance which is run for the background to move
     */
    private MainThread thread;

    /**
     * Instance for the Background class
     */
    private Background bg;

    /**
     * Instance for the Player class
     */
    private Player player;

    /**
     * Instance for the PitchDetector class
     */
    private PitchDetector pitchDetector;

    Listener mListener = null;

    public interface Listener {
//        void onVoiceInputStopped(float finalScore);
        void onPitchDetected(float pitch, float pitchSum);
    }

    /**
     * Constructor for the GamePanel class
     * @param context
     */
    public GamePanel(Context context) {
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    /**
     * Overridden surfaceChanged method for the class
     * @param holder
     * @param format
     * @param width
     * @param height
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    /**
     * Overridden surfaceDestroyed method for the class
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                //set the thread level bool to false
                thread.setRunning(false);
                //stop the main thread
                thread.join();
                //stop the thread for the pitch detection
                pitchDetector.setRunning(false);
                pitchDetector.stop();

            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    /**
     * Overridden surfaceCreated method for the class
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.full_background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.superman), 0, 0);
        //set default vector speed
        //TODO: Check if we need this
        bg.setVector(-5);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
        //start thread for pitch detection
        pitchDetector = new PitchDetector(this);
        pitchDetector.setRunning(true);
    }

    /**
     * Overridden onTouchEvent for the class
     * @param event
     * @return super.onTouchEvent(event)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    /**
     * Method that updates the background images
     */
    public void update() { bg.update(); }

    /**
     * Overridden draw method for the class
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        bg.draw(canvas);
        super.draw(canvas);
        //set values for scale factor for the background
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            //draw superman and frequency on the background
            if (pitchDetector.isRunning()) {
                player.draw(canvas, pitchDetector.getPitchInHz());
                mListener.onPitchDetected(pitchDetector.getPitchInHz(), pitchDetector.getPitchSum());
            }

            canvas.restoreToCount(savedState);
        }
    }

    /**
     * Getter for the Background instance
     * @return Background instance of the class
     */
    public Background getBg() {
        return bg;
    }

    public void setListener(Listener l) {
        mListener = l;
    }
}
