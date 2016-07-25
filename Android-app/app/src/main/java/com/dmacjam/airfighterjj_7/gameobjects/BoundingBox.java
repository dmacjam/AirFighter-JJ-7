package com.dmacjam.airfighterjj_7.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jakub on 2.4.2015.
 */
public class BoundingBox {
    public int xMin, xMax, yMin, yMax;
    private Paint paint;  // paint style and color
    private Rect bounds;

    public BoundingBox(int color){
        paint = new Paint();
        paint.setColor(color);
        bounds = new Rect();
    }

    public void set(int x, int y, int width, int height) {
        xMin = x;
        xMax = x + width;
        yMin = y;
        yMax = y + height;
        // The box's bounds do not change unless the view's size changes
        bounds.set(xMin, yMin, xMax, yMax);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(bounds, paint);
    }

}
