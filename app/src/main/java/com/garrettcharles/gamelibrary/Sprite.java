package com.garrettcharles.gamelibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.garrettcharles.singleton.Cache;
import com.garrettcharles.singleton.Touches;

import java.util.Random;

public class Sprite extends Rect {
    protected Paint paint;

    public Sprite(float left, float top, float width, float height) {
        super(left, top, width, height);
        this.paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.RED);
        canvas.drawRect(this.getRect(), paint);
    }

    public void update() {
//        Touches touches = Touches.getInstance();
//        Rect screen = Cache.getInstance().getGame().getScreen();
//
//        if (touches.isPressed() || touches.isDragging()) {
//            Point touchPoint = touches.getTouchPoint();
//            xPlusEquals((touchPoint.x - centerX()) / 10);
//            yPlusEquals((touchPoint.y - centerY()) / 10);
//        }
    }
}
