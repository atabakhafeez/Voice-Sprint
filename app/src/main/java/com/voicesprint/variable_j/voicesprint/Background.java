package com.voicesprint.variable_j.voicesprint;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Background class to create illusion of moving background
 * @author atabakh
 */
public class Background {
    /**
     * The image for the background
     */
    private Bitmap image;

    /**
     * The x-coordinate for the image where it is placed on the screen
     */
    private int x;

    /**
     * The y-coordinate for the image where it is placed on the screen
     */
    private int y;

    /**
     * The vector which determines the speed of movement of the image
     */
    private int dx;

    /**
     * The constructor the class
     * @param res
     */
    public Background(Bitmap res) {
        image = res;
    }

    /**
     * Updated the x-coordinate value to move the image to the right in accordance with the vector.
     */
    public void update() {
        x += dx;
        // If the image has scrolled out of view completely, reset.
        if (x < -GamePanel.WIDTH) {
            x = 0;
        }
    }

    /**
     * Draw the background images on the canvas
     * @param canvas
     */
    public void draw(Canvas canvas) {
        // Draw the image on the left which is disappearing
        canvas.drawBitmap(image, x, y, null);
        // Draw the image on the right sliding into view
        if (x < 0) {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }

    /**
     * Sets vector for speed of background image movement
     * @param dx
     */
    public void setVector(int dx) {
        this.dx = dx;
    }
}
