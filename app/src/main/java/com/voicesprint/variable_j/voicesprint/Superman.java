package com.voicesprint.variable_j.voicesprint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * @file Superman.java
 * @abrief Superman class which displays the image of the player and also displays the text on the
 * canvas
 * @author atabakh
 * @bug No known bugs.
 * Created by atabakh on 16/04/2016.
 */
public class Superman {
    /**
     * @brief Stores the image of the superman
     */
    private Bitmap bitmap;

    /**
     * @brief x-Coordinate for the position of the image
     */
    private int x;

    /**
     * @brief x-Coordinate for the position of the image
     */
    private int y;

    /**
     * @brief Height of the image
     */
    private int height;

    /**
     * @brief Width of the image
     */
    private int width;

    /**
     * @brief Constructor for this class
     * @param res
     * @param w
     * @param h
     */
    public Superman(Bitmap res, int w, int h) {
        height = h;
        width = w;
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/3;
        bitmap = res;
    }

    /**
     * @brief Draws the image of the superman on the canvas
     * @param canvas
     * @param pitch
     */
    public void draw(Canvas canvas, String pitch) {
        drawText(canvas, pitch);
        canvas.drawBitmap(bitmap, x, y, null);
    }

    /**
     * @brief Draws text showing pitch on the screen
     * @param canvas
     * @param pitch
     */
    public void drawText(Canvas canvas, String pitch) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Pitch: " + pitch, 20, 50, paint);
    }

    /**
     * @brief Resizes the bitmap
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return Resized bitmap image
     */
    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // Create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // "Recreate" the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}

