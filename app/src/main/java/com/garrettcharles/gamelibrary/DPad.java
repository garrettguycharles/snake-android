package com.garrettcharles.gamelibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.garrettcharles.singleton.Cache;
import com.garrettcharles.singleton.Touches;

public class DPad extends Sprite {

    private int direction = Utils.RIGHT;
    private boolean pressed;

    public DPad(float left, float top, float diameter) {
        super(left, top, diameter, diameter);
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
        RadialGradient backGrad = new RadialGradient(
                centerX() - getW() / 5,
                centerY() - getW() / 5,
                getRadius() + getW() / 5,
                new int[] {Color.LTGRAY, Color.DKGRAY, Color.BLACK},
                new float[] {0.0f, 0.7f, 1.0f},
                Shader.TileMode.CLAMP);

        paint.setShader(backGrad);
        canvas.drawCircle(centerX(), centerY(), getRadius(), paint);

        paint.setShader(null);
        float arrowHeight = getH() / 6;
        float arrowWidth = getW() / 5;
        float arrowHalfWidth = arrowWidth / 2;

        // draw arrows
        paint.setColor(Color.WHITE);
        if (getDirection() == Utils.UP && isPressed()) {
            paint.setColor(Color.YELLOW);
        }
        Path upArrow = new Path();
        upArrow.moveTo(centerX(), getTop() + getH() / 8);
        upArrow.rLineTo(arrowHalfWidth, arrowHeight);
        upArrow.rLineTo(-arrowWidth, 0);
        upArrow.rLineTo(arrowHalfWidth, -arrowHeight);
        canvas.drawPath(upArrow, paint);

        paint.setColor(Color.WHITE);
        if (getDirection() == Utils.RIGHT && isPressed()) {
            paint.setColor(Color.YELLOW);
        }
        Path rightArrow = new Path();
        rightArrow.moveTo(getRight() - getW() / 8, centerY());
        rightArrow.rLineTo(-arrowHeight, arrowHalfWidth);
        rightArrow.rLineTo(0, -arrowWidth);
        rightArrow.rLineTo(arrowHeight, arrowHalfWidth);
        canvas.drawPath(rightArrow, paint);

        paint.setColor(Color.WHITE);
        if (getDirection() == Utils.DOWN && isPressed()) {
            paint.setColor(Color.YELLOW);
        }
        Path downArrow = new Path();
        downArrow.moveTo(centerX(), getBottom() - getH() / 8);
        downArrow.rLineTo(-arrowHalfWidth, -arrowHeight);
        downArrow.rLineTo(arrowWidth, 0);
        downArrow.rLineTo(-arrowHalfWidth, arrowHeight);
        canvas.drawPath(downArrow, paint);

        paint.setColor(Color.WHITE);
        if (getDirection() == Utils.LEFT && isPressed()) {
            paint.setColor(Color.YELLOW);
        }
        Path leftArrow = new Path();
        leftArrow.moveTo(getLeft() + getW() / 8, centerY());
        leftArrow.rLineTo(arrowHeight, -arrowHalfWidth);
        leftArrow.rLineTo(0, arrowWidth);
        leftArrow.rLineTo(-arrowHeight, -arrowHalfWidth);
        canvas.drawPath(leftArrow, paint);
    }

    @Override
    public void update() {
        Touches touches = Touches.getInstance();
        Point touchPoint = touches.getTouchPoint();

        if (touches.isPressed() || touches.isDragging()) {
            if (this.collidepoint_circle(touchPoint)) {
                System.out.println("DPad touched.");
                this.pressed = true;
                this.direction = getCenter().to(touchPoint).nearestCardinal();
            } else {
                this.pressed = false;
            }
        } else {
            this.pressed = false;
        }
    }

    public int getDirection() {
        return direction;
    }

    public boolean isPressed() {
        return pressed;
    }
}
