package com.voicesprint.variable_j.voicesprint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Player class which displays the image of the player and also displays the text on the
 * canvas
 * @author atabakh
 * Created by atabakh on 16/04/2016.
 */
public class Player {

    String noMicInput = "You are silent!\n Lets make a sound.";
    String lowPitch = "Come on! You\n can do better!!";
    String mediumPitch = "WE ARE GETTING\n THERE!!";
    String highPitch = "YOU ARE AMAZING!!";

    int noInputFromMic = -1;
    int lowPitchInput = 150;
    int mediumPitchInput = 400;
    int highPitchInput = 750;

    /**
     * Stores the image of the superman
     */
    private Bitmap bitmap;

    /**
     * x-Coordinate for the position of the image
     */
    private int x;

    /**
     * x-Coordinate for the position of the image
     */
    private int y;

    /**
     * Height of the image
     */
    private int height;

    /**
     * Width of the image
     */
    private int width;

    /**
     * Paint instance that draws the pitch value detected
     */
    private Paint paintPitchShow;

    /**
     * Paint instance that gives the player feedback according to input received
     */
    private Paint paintPlayerFeedback;

    /**
     * Constructor for this class
     * @param res
     * @param w
     * @param h
     */
    public Player(Bitmap res, int w, int h) {
        height = h;
        width = w;
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/3;
        bitmap = res;

        // Initialize values for paint instances
        paintPitchShow = new Paint();
        paintPitchShow.setStyle(Paint.Style.FILL);
        paintPitchShow.setColor(Color.BLACK);
        paintPitchShow.setTextSize(50);

        paintPlayerFeedback = new Paint();
        paintPlayerFeedback.setStyle(Paint.Style.FILL);
        paintPlayerFeedback.setTextSize(30);
    }

    /**
     * Draws the image of the player on the canvas
     * @param canvas
     * @param pitch
     */
    public void draw(Canvas canvas, float pitch) {
        drawText(canvas, pitch);
        canvas.drawBitmap(bitmap, x, y, null);
    }

    /**
     * Draws text showing pitch on the screen
     * @param canvas
     * @param pitch
     */
    public void drawText(Canvas canvas, float pitch) {
        // Display the pitch on the screen
        canvas.drawText("Pitch: " + Float.toString(pitch), 20, 50, paintPitchShow);

        if ((int) pitch == noInputFromMic) {
            paintPlayerFeedback.setColor(Color.RED);
            canvas.drawText(noMicInput, 250, 100, paintPlayerFeedback);
        } else if ((int) pitch > lowPitchInput && (int) pitch < mediumPitchInput) {
            paintPlayerFeedback.setColor(Color.BLUE);
            canvas.drawText(lowPitch, 250, 100, paintPlayerFeedback);
        } else if ((int) pitch >= mediumPitchInput && (int) pitch < highPitchInput) {
            paintPlayerFeedback.setColor(Color.green(5));
            canvas.drawText(mediumPitch, 250, 100, paintPlayerFeedback);
        } else if ((int) pitch >= highPitchInput) {
            paintPlayerFeedback.setColor(Color.GREEN);
            canvas.drawText(highPitch, 250, 100, paintPlayerFeedback);
        } else {
            //TODO: Get rid of this case
        }
    }

    /**
     * Resizes the bitmap
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

