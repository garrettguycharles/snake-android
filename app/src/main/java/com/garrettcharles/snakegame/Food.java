package com.garrettcharles.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;

import com.garrettcharles.gamelibrary.Sprite;
import com.garrettcharles.gamelibrary.Utils;
import com.garrettcharles.singleton.Cache;

public class Food extends Sprite {
    public Food() {
        super(10, 10, Cache.getInstance().getBoundary().getW() / 20, Cache.getInstance().getBoundary().getH() / 20);
        Cache.getInstance().setFood(this);
        this.newSpot();
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.BLUE);

        canvas.drawCircle(centerX(), centerY(), getRadius(), paint);
    }

    @Override
    public void update() {
    }

    public void newSpot() {
        Boundary boundary = Cache.getInstance().getBoundary();

        do {
            setLeft(Utils.random_range(boundary.getLeft(), boundary.getRight()));
            setTop(Utils.random_range(boundary.getTop(), boundary.getBottom()));
            setLeft(getLeft() - (getLeft() - boundary.getLeft()) % getW());
            setTop(getTop() - (getTop() - boundary.getTop()) % getH());
        } while (Cache.getInstance().getPlayer().isTouching(this));
    }
}
