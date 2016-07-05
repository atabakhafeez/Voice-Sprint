package com.voicesprint.variable_j.voicesprint;


import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * @file MainThread.java
 * @brief The main thread that moves the background to create a moving player illusion.
 * @author atabakh
 * @bug No known bugs.
 */
public class MainThread extends Thread {
    /**
     * @brief The canvas where the image is drawn
     */
    public static Canvas canvas;

    /**
     * @brief Frames per second is set to 30
     */
    private int FPS = 30;

    /**
     * @brief The average frames per second that is calculated
     */
    private double averageFPS;

    /**
     * @brief SurfaceHolder instance from the GamePanel class
     */
    private SurfaceHolder surfaceHolder;

    /**
     * @brief GamePanel instance which runs this thread class
     */
    private GamePanel gamePanel;

    /**
     * @brief Boolean which keeps track of whether the thread is running or not
     */
    private boolean running;

    /**
     * @brief Constructor for this class
     * @param surfaceHolder
     * @param gamePanel
     */
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    /**
     * @brief Overridden method to run the thread
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            // Try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            // Calculate the time needed for the pixel editing and wait the time remaining
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;
            try {
                this.sleep(waitTime);
            } catch(Exception e){}

            // Check and print the average FPS
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    /**
     * @brief Sets the Boolean 'running' to either false or true
     * @param b
     */
    public void setRunning(boolean b) { running = b; }
}
