package com.garrettcharles.singleton;

import android.annotation.SuppressLint;

import com.garrettcharles.gamelibrary.Point;

public class Touches {
    private static Touches instance;

    private Point touch;
    private boolean pressed;
    private boolean released;
    private boolean dragging;

    private Touches() {
        this.touch = new Point(0, 0);
    }

    public static Touches getInstance() {
        if (instance == null) {
            instance = new Touches();
        }

        return instance;
    }

    @SuppressLint("NewApi")
    public void setTouchPoint(float x, float y) {
        this.touch.x = x;
        this.touch.y = y;

        //System.out.println(Cache.getInstance().getGame().getScreen().getCenter().to(getTouchPoint()).nearestCardinal());
    }

    public Point getTouchPoint() {
        return touch;
    }

    public void setPressed() {
//        System.out.println("pressed");
        pressed = true;
        released = false;
        dragging = false;
    }

    public void setReleased() {
//        System.out.println("released");
        released = true;
        dragging = false;
        pressed = false;
    }

    public void setDragging() {
//        System.out.println("dragging");
        dragging = true;
        pressed = true;
        released = false;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isReleased() {
        return released;
    }

    public boolean isDragging() {
        return dragging;
    }
}
