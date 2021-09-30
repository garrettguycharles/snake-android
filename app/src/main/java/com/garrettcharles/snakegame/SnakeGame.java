package com.garrettcharles.snakegame;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import com.garrettcharles.gamelibrary.DPad;
import com.garrettcharles.gamelibrary.Game;
import com.garrettcharles.gamelibrary.Joystick;
import com.garrettcharles.gamelibrary.Point;
import com.garrettcharles.singleton.Cache;

public class SnakeGame extends Game {

    MediaPlayer mPlayer;
    MediaPlayer bgPlayer;

    Boundary boundary;

    public Boundary getBoundary() {
        return boundary;
    }

    public void setBoundary(Boundary boundary) {
        this.boundary = boundary;
    }

    Joystick joystick;

    public Joystick getJoystick() {
        return joystick;
    }

    public void setJoystick(Joystick joystick) {
        this.joystick = joystick;
    }

    @SuppressLint("NewApi")
    Snake player;
    Food food;

    @SuppressLint("NewApi")
    public SnakeGame(Context context) {
        super(context);

        bgPlayer = MediaPlayer.create(context, R.raw.bgmusic);
        bgPlayer.start();

        DisplayMetrics metrics = new DisplayMetrics();
        Cache.getInstance().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Cache.getInstance().setWIDTH(metrics.widthPixels);
        Cache.getInstance().setHEIGHT(metrics.heightPixels);

        boundary = new Boundary(0, 0, metrics.widthPixels * 0.9f, metrics.widthPixels * 0.9f);
        boundary.setCenter(new Point(Cache.getInstance().getWIDTH() / 2, Cache.getInstance().getWIDTH() * 0.5f));

        Cache.getInstance().setBoundary(boundary);

        player = new Snake();
        food = new Food();

        addSprite(boundary);
        addSprite(player);
        addSprite(food);

        joystick = new Joystick(0, 0, Cache.getInstance().getWIDTH() / 2);
        joystick.setMidBottom(new Point(Cache.getInstance().getWIDTH() / 2,
                Cache.getInstance().getHEIGHT() - Cache.getInstance().getWIDTH() / 10));
        joystick.updateHomePosition();

        addSprite(joystick);
        player.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void drawFrame(Canvas canvas) {
        paint.setColor(Color.WHITE);

        canvas.drawRect(0, 0, Cache.getInstance().getWIDTH(), Cache.getInstance().getHEIGHT(), paint);
        this.drawSprites(canvas);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(boundary.getH() / 10);

        paint.setColor(Color.BLACK);

        String score = Integer.toString(player.getLength());
        float scoreWidth = paint.measureText(score);

        canvas.drawText(score, boundary.centerX(), (float) (boundary.getBottom() + boundary.getH() * 0.15), paint);
    }

    @SuppressLint("NewApi")
    @Override
    public void update() {
        super.update();

        if (player.overlaps(food)) {
            player.addSectionNext();
            food.newSpot();
            mPlayer = MediaPlayer.create(getContext(), R.raw.boop);
            mPlayer.start();
        }
    }

    public void stopMusic() {
        bgPlayer.stop();
    }

    public void navigateToTitleScreen() {
        stopMusic();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(intent);
    }

    public void navigateToGameOverScreen() {
        stopMusic();
        Cache.getInstance().setScore(player.getLength());
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        getContext().startActivity(intent);
    }
}
