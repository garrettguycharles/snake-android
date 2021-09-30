package com.garrettcharles.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.garrettcharles.gamelibrary.Rect;
import com.garrettcharles.gamelibrary.Sprite;

public class Boundary extends Sprite {
    public Boundary(float left, float top, float width, float height) {
        super(left, top, width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(getLeft() - 5, getTop() - 5, getRight() + 5, getBottom() + 5),
                getW() / 40, getH() / 40, paint);

    }
}
