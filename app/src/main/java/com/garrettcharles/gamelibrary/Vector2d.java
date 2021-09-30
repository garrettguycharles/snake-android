package com.garrettcharles.gamelibrary;

public class Vector2d {
    public float x;
    public float y;

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2d inDirection(float theta, float magnitude) {
        float x = (float) (magnitude * Math.cos(Math.toRadians(theta)));
        float y = (float) (magnitude * Math.sin(Math.toRadians(theta)));

        return new Vector2d(x, y);
    }

    public static Vector2d right() {
        return new Vector2d(1, 0);
    }

    public static Vector2d up() {
        return new Vector2d(0, -1);
    }

    public static Vector2d left() {
        return new Vector2d(-1, 0);
    }

    public static Vector2d down() {
        return new Vector2d(0, 0);
    }

    public static Vector2d from(Vector2d position) {
        return new Vector2d(position.x, position.y);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d scale(float scalar) {
        return new Vector2d(this.x * scalar, this.y * scalar);
    }

    public Vector2d normalize() {
        if (getMagnitude() > 0) {
            return scale(1.0f / this.getMagnitude());
        }

        return this;
    }

    public float dot(Vector2d other) {
        return x * other.x + y * other.y;
    }

    public Vector2d project(Vector2d onto) {
        if (onto.getMagnitude() == 0) {
            return onto;
        }

        if (this.getMagnitude() == 0) {
            return this;
        }

        float scalar = this.dot(onto) / onto.dot(onto);
        return onto.scale(scalar);
    }

    public float distanceTo(Vector2d other) {
        return other.subtract(this).getMagnitude();
    }

    public float angleTo(Vector2d other) {
        return other.subtract(this).getAngle();
    }

    public float absAngleBetween(Vector2d other) {
        float h = this.getMagnitude();
        float a = this.project(other).getMagnitude();
        float o = this.project(other).subtract(this).getMagnitude();

        return (float) Math.asin(o / h);
    }

    public float angleBetween(Vector2d other) {
        float theta = this.absAngleBetween(other);
        if (this.rotate(theta).absAngleBetween(other) > theta) {
            return -theta;
        }

        return theta;
    }

    public Vector2d rotate(float theta) {
        return new Vector2d(
                (float) (this.x * Math.cos(theta) - this.y * Math.sin(theta)),
                (float) (this.x * Math.sin(theta) + this.y * Math.cos(theta))
        );
    }

    public boolean acuteWith(Vector2d other) {
        return this.dot(other) > 0;
    }

    public boolean obtuseWith(Vector2d other) {
        return this.dot(other) < 0;
    }

    public boolean perpendicularTo(Vector2d other) {
        return this.dot(other) == 0;
    }

    public Vector2d flip() {
        return new Vector2d(-this.x, -this.y);
    }

    public Vector2d transpose() {
        return new Vector2d(y, x);
    }

    public boolean isEqualTo(Vector2d other) {
        if (other instanceof Vector2d) {
            return x == other.x && y == other.y;
        }

        return false;
    }

    public Vector2d lerpTowards(Vector2d other, Float val) {
        if (val == null) {
            val = 0.1f;
        }

        Vector2d toOther = this.to(other);

        return this.add(toOther.scale(val));
    }

    public Vector2d approachAverage(Vector2d other, Float val) {
        if (val == null) {
            val = 0.1f;
        }

        Vector2d avg = this.add(other).scale(0.5f);

        return this.lerpTowards(avg, val);
    }

    public Vector2d approachSum(Vector2d other, Float val) {
        if (val == null) {
            val = 0.1f;
        }

        Vector2d sum = this.add(other);

        return this.lerpTowards(sum, val);
    }

    public Vector2d to(Vector2d other) {
        return other.subtract(this);
    }

    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public float getAngle() {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }

    public Vector2d perpendicularR() {
        return new Vector2d(-y, x);
    }

    public Vector2d perpendicularL() {
        return new Vector2d(y, -x);
    }

    public Vector2d snapToCardinal() {
        Vector2d destAngle;
        if (Math.abs(x) > Math.abs(y)) {
            destAngle = new Vector2d(x, 0);
        } else {
            destAngle = new Vector2d(0, y);
        }

        return this.rotate(this.angleBetween(destAngle));
    }

    public int nearestCardinal() {
        int toReturn = (int) Math.floor((getAngle() + 45) / 90);
        if (toReturn < 0) {
            toReturn += 4;
        }

        return toReturn;
    }

    public int getQuadrant() {
        int toReturn = 1;
        if (x < 0 && y > 0) {
            toReturn = 2;
        } else if (x < 0 && y < 0) {
            toReturn = 3;
        } else if (x > 0 && y > 0) {
            toReturn = 4;
        }

        return toReturn;
    }

}
