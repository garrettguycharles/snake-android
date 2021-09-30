package com.garrettcharles.singleton;

import android.view.WindowManager;

import com.garrettcharles.gamelibrary.DPad;
import com.garrettcharles.gamelibrary.Game;
import com.garrettcharles.gamelibrary.Sprite;
import com.garrettcharles.snakegame.Boundary;
import com.garrettcharles.snakegame.Food;
import com.garrettcharles.snakegame.Snake;

public class Cache {

    private static Cache instance;

    private Game game;
    private WindowManager windowManager;
    private int WIDTH;
    private int HEIGHT;

    private Boundary boundary;
    private Snake player;
    private Food food;
    private DPad dPad;

    private int score;


    private Cache() {

    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }

        return instance;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public void setWIDTH(int widthPixels) {
        WIDTH = widthPixels;
    }

    public void setHEIGHT(int heightPixels) {
        HEIGHT = heightPixels;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    public Snake getPlayer() {
        return player;
    }

    public void setPlayer(Snake player) {
        this.player = player;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public void setDPad(DPad dPad) {
        this.dPad = dPad;
    }

    public DPad getDPad() {
        return dPad;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
