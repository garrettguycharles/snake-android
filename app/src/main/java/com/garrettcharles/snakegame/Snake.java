package com.garrettcharles.snakegame;

import static com.garrettcharles.gamelibrary.Utils.DOWN;
import static com.garrettcharles.gamelibrary.Utils.LEFT;
import static com.garrettcharles.gamelibrary.Utils.RIGHT;
import static com.garrettcharles.gamelibrary.Utils.UP;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.garrettcharles.gamelibrary.Joystick;
import com.garrettcharles.gamelibrary.Point;
import com.garrettcharles.gamelibrary.Rect;
import com.garrettcharles.gamelibrary.Sprite;
import com.garrettcharles.singleton.Cache;

import java.util.ArrayList;
import java.util.List;

public class Snake extends Sprite {

    int direction = RIGHT;
    int next_direction = RIGHT;
    int length;
    List<Rect> tail = new ArrayList<>();
    boolean add_section_next = true;
    private long movementTimer = System.currentTimeMillis();
    private boolean moving = false;
    private long movementDelay = 1000 / 3;

    public Snake() {
        super(10, 10, Cache.getInstance().getBoundary().getW() / 20, Cache.getInstance().getBoundary().getH() / 20);
        Cache.getInstance().setPlayer(this);
        setLeft(Cache.getInstance().getBoundary().getLeft() + this.getW());
        setTop(Cache.getInstance().getBoundary().getTop() + this.getH());
    }

    @Override
    public void draw(Canvas canvas) {

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawRect(getRect(), paint);

        paint.setAntiAlias(false);

        for (int i = 0; i < tail.size(); i++) {
            paint.setColor(Color.DKGRAY);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(tail.get(i).getRect(), paint);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void update() {
        Joystick joystick = Cache.getInstance().getGame().getJoystick();
        Rect screen = Cache.getInstance().getGame().getScreen();


        if (joystick.isActive()) {
            setDirection(joystick.getPosition().nearestCardinal());
        }

        long timeNow = System.currentTimeMillis();
        if (moving && timeNow - movementTimer > movementDelay) {
            scheduledMovement();
            movementTimer = timeNow;
        }
    }

    public void start() {
        moving = true;
    }

    public void stop() {
        moving = false;
        ((SnakeGame) Cache.getInstance().getGame()).navigateToGameOverScreen();
    }

    public void scheduledMovement() {
        Point previousPosition = new Point(getLeft(), getTop());

        if ((next_direction + 2) % 4 != direction) { // disallow 180deg turns
            direction = next_direction;
        }

        switch (direction) {
            case RIGHT:
                setX(getX() + getW());
                break;
            case UP:
                setY(getY() - getH());
                break;
            case LEFT:
                setX(getX() - getW());
                break;
            case DOWN:
                setY(getY() + getH());
                break;
        }

        Point nextPosition = new Point(previousPosition.x, previousPosition.y);

        for (Rect r : tail) {
            previousPosition = r.getTopLeft().copy();
            r.setLeft(nextPosition.x);
            r.setTop(nextPosition.y);
            nextPosition.x = previousPosition.x;
            nextPosition.y = previousPosition.y;
        }

        if (add_section_next) {
            add_section_next = false;
            this.tail.add(new Rect(previousPosition.x, previousPosition.y, getW(), getH()));
        }

        // check collisions
        for (Rect r : tail) {
            if (this.overlaps(r)) {
                stop();
            }
        }

        if (!this.overlaps(Cache.getInstance().getBoundary())) {
            stop();
        }
    }

    public void setDirection(int dir) {
        this.next_direction = dir;
    }

    public boolean isTouching(Sprite other) {
        if (this.overlaps(other)) {
            return true;
        }

        for (Rect box : tail) {
            if (box.overlaps(other)) {
                return true;
            }
        }

        return false;
    }

    public void addSectionNext() {
        add_section_next = true;
        movementDelay = Math.max(100, movementDelay - 2);
    }

    public int getLength() {
        return tail.size();
    }
}
