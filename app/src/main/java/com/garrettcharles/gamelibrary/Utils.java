package com.garrettcharles.gamelibrary;

public class Utils {
    public static final int RIGHT = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;

    static int random_range(int a, int b) {
        return (int) Math.floor(Math.random() * ((b - a) + 1)) + a;
    }

    public static int random_range(float a, float b) {
        return (int) ((int) Math.floor(Math.random() * ((b - a) + 1)) + a);
    }
}
