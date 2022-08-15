package com.garrettcharles.gamelibrary;

public class Point extends Vector2 {
    public Point(float x, float y) {
        super(x, y);
    }

    public Point copy() {
        return new Point(this.x, this.y);
    }

    public static Point from(Vector2 v) {
        return new Point(v.x, v.y);
    }
}
