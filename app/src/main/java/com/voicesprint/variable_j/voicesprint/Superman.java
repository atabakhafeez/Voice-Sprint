package com.voicesprint.variable_j.voicesprint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by atabakh on 16/04/2016.
 */
public class Superman {
    private Bitmap spritesheet;
    private int x;
    private int y;
    private int height;
    private int width;

    public Superman(Bitmap res, int w, int h) {
        height = h;
        width = w;
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/3;
        spritesheet = res;
    }

    public void update() { }

    public void draw(Canvas canvas, String pitch) {
        drawText(canvas, pitch);
        canvas.drawBitmap(spritesheet, x, y, null);
    }

    public void drawText(Canvas canvas, String pitch) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        canvas.drawText("Pitch: " + pitch, 20, 50, paint);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}
