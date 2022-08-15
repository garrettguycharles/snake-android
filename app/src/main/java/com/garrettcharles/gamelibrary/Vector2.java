package com.garrettcharles.gamelibrary;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 inDirection(float theta, float magnitude) {
        float x = (float) (magnitude * Math.cos(Math.toRadians(theta)));
        float y = (float) (magnitude * Math.sin(Math.toRadians(theta)));

        return new Vector2(x, y);
    }

    public static Vector2 right() {
        return new Vector2(1, 0);
    }

    public static Vector2 up() {
        return new Vector2(0, -1);
    }

    public static Vector2 left() {
        return new Vector2(-1, 0);
    }

    public static Vector2 down() {
        return new Vector2(0, 0);
    }

    public static Vector2 from(Vector2 position) {
        return new Vector2(position.x, position.y);
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 scale(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public Vector2 normalize() {
        if (getMagnitude() > 0) {
            return scale(1.0f / this.getMagnitude());
        }

        return this;
    }

    public float dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    public Vector2 project(Vector2 onto) {
        if (onto.getMagnitude() == 0) {
            return onto;
        }

        if (this.getMagnitude() == 0) {
            return this;
        }

        float scalar = this.dot(onto) / onto.dot(onto);
        return onto.scale(scalar);
    }

    public float distanceTo(Vector2 other) {
        return other.subtract(this).getMagnitude();
    }

    public float angleTo(Vector2 other) {
        return other.subtract(this).getAngle();
    }

    public float absAngleBetween(Vector2 other) {
        float h = this.getMagnitude();
        float a = this.project(other).getMagnitude();
        float o = this.project(other).subtract(this).getMagnitude();

        return (float) Math.asin(o / h);
    }

    public float angleBetween(Vector2 other) {
//        float theta = this.absAngleBetween(other);
//        if (this.rotate(theta).absAngleBetween(other) > theta) {
//            return -theta;
//        }
//
//        return theta;

        Float toReturn = other.getAngle() - this.getAngle();

        while (toReturn > 180f) {
            toReturn -= 360f;
        }

        while (toReturn < -180f) {
            toReturn += 360f;
        }

        return (float) Math.toRadians(toReturn);
    }

    public Vector2 rotate(float theta) {
        return new Vector2(
                (float) (this.x * Math.cos(theta) - this.y * Math.sin(theta)),
                (float) (this.x * Math.sin(theta) + this.y * Math.cos(theta))
        );
    }

    public boolean acuteWith(Vector2 other) {
        return this.dot(other) > 0;
    }

    public boolean obtuseWith(Vector2 other) {
        return this.dot(other) < 0;
    }

    public boolean perpendicularTo(Vector2 other) {
        return this.dot(other) == 0;
    }

    public Vector2 flip() {
        return new Vector2(-this.x, -this.y);
    }

    public Vector2 transpose() {
        return new Vector2(y, x);
    }

    public boolean isEqualTo(Vector2 other) {
        if (other instanceof Vector2) {
            return x == other.x && y == other.y;
        }

        return false;
    }

    public Vector2 lerpTowards(Vector2 other, Float val) {
        if (val == null) {
            val = 0.1f;
        }

        Vector2 toOther = this.to(other);

        return this.add(toOther.scale(val));
    }

    public Vector2 approachAverage(Vector2 other, Float val) {
        if (val == null) {
            val = 0.1f;
        }

        Vector2 avg = this.add(other).scale(0.5f);

        return this.lerpTowards(avg, val);
    }

    public Vector2 approachSum(Vector2 other, Float val) {
        if (val == null) {
            val = 0.1f;
        }

        Vector2 sum = this.add(other);

        return this.lerpTowards(sum, val);
    }

    public Vector2 to(Vector2 other) {
        return other.subtract(this);
    }

    public float getMagnitude() {
        return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public float getAngle() {
        return (float) Math.toDegrees(Math.atan2(y, x));
    }

    public Vector2 perpendicularR() {
        return new Vector2(-y, x);
    }

    public Vector2 perpendicularL() {
        return new Vector2(y, -x);
    }

    public Vector2 snapToCardinal() {
        Vector2 destAngle;
        if (Math.abs(x) > Math.abs(y)) {
            destAngle = new Vector2(x, 0);
        } else {
            destAngle = new Vector2(0, y);
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
