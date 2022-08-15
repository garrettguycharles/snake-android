package com.garrettcharles.snakegame;

import static com.garrettcharles.gamelibrary.Utils.DOWN;
import static com.garrettcharles.gamelibrary.Utils.LEFT;
import static com.garrettcharles.gamelibrary.Utils.RIGHT;
import static com.garrettcharles.gamelibrary.Utils.UP;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.garrettcharles.gamelibrary.Joystick;
import com.garrettcharles.gamelibrary.Point;
import com.garrettcharles.gamelibrary.Rect;
import com.garrettcharles.gamelibrary.Sprite;
import com.garrettcharles.gamelibrary.Vector2;
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
//        canvas.drawRect(getRect(), paint);
        RectF rect = getRect();

        Float left = 0.0f;
        Float top = 0.0f;
        Float right = 0.0f;
        Float bottom = 0.0f;
        Integer startAngle = 0;

        // draw tail

        paint.setAntiAlias(false);
        paint.setStyle(Paint.Style.FILL);

        for (int i = tail.size() - 1; i >= 0; i--) {
            // decide what type of segment this is

            Rect previous = i > 0 ? tail.get(i - 1) : this;
            Rect current = tail.get(i);
            Rect next = i < tail.size() - 1 ? tail.get(i + 1) : null;

            if (next == null) {
                // shape is rounded butt
                paint.setColor(Color.argb(255, 77, 94, 78));

                Vector2 midpoint = current.getCenter().approachAverage(previous.getCenter(), 1f);
                canvas.drawCircle(midpoint.x, midpoint.y, current.getRadius(), paint);

                canvas.drawCircle(current.centerX(), current.centerY(), current.getRadius(), paint);
            } else {
                paint.setColor(Color.argb(255, 0, 156, 8));
                Vector2 midpoint = previous.getCenter().approachAverage(next.getCenter(), 1f);

                List<Vector2> anchors = List.of(
                        current.getCenter(),
                        current.getTopLeft(), current.getTopRight(),
                        current.getBottomRight(), current.getBottomLeft()
                );

                Vector2 nearestAnchor = null;

                // find nearest anchor
                for (Vector2 anchor : anchors) {
                    if (nearestAnchor == null) {
                        nearestAnchor = anchor;
                    } else if (anchor.distanceTo(midpoint) < nearestAnchor.distanceTo(midpoint)) {
                        nearestAnchor = anchor;
                    }
                }

                if (nearestAnchor == current.getCenter()) {
                    // shape is square


                    paint.setColor(Color.argb(255, 0, 156, 8));
                    canvas.drawRect(current.getRect(), paint);
                } else {
                    // draw the head arc

                    left = midpoint.x - current.getW();
                    right = midpoint.x + current.getW();
                    top = midpoint.y - current.getH();
                    bottom = midpoint.y + current.getH();

                    Float angle = midpoint.angleTo(current.getCenter());

                    canvas.drawArc(left, top, right, bottom, angle - 45, 90, true, paint);
                }
            }
        }

        // draw head

        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(255, 138, 230, 39));

        if (List.of(LEFT, RIGHT).contains(this.direction)) {
            top = rect.top;
            bottom = rect.bottom;

            if (direction == LEFT) {
                right = rect.right + rect.width();
                startAngle = 90;
            } else {
                right = rect.right;
                startAngle = 270;
            }

            left = right - rect.width() * 2;
        }

        if (List.of(UP, DOWN).contains(this.direction)) {
            left = rect.left;
            right = rect.right;

            if (direction == UP) {
                top = rect.top;
                startAngle = 180;
            } else {
                top = rect.top - rect.height();
                startAngle = 0;
            }

            bottom = top + rect.height() * 2;
        }

        canvas.drawArc(left, top, right, bottom, startAngle, 180, true, paint);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void stop() {
        moving = false;
        ((SnakeGame) Cache.getInstance().getGame()).navigateToGameOverScreen();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
