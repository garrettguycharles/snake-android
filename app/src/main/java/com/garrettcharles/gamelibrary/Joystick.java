package com.garrettcharles.gamelibrary;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import com.garrettcharles.singleton.Touches;

public class Joystick extends Sprite {

    private Vector2 position;
    private Vector2 homePosition;
    private boolean pressed = false;
    private Vector2 hatPosition;
    private float hatRadius;
    private boolean active;

    public Joystick(float left, float top, float diameter) {
        super(left, top, diameter, diameter);

        this.position = new Vector2(0, 0);
        this.homePosition = Vector2.from(this.position);
        this.hatPosition = new Point(centerX(), centerY());
        this.hatRadius = getRadius() / 2;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(0xa0);

        RadialGradient backGrad = new RadialGradient(
                centerX() - getW() / 5,
                centerY() - getW() / 5,
                getRadius() + getW() / 5,
                new int[] {Color.LTGRAY, Color.DKGRAY, Color.BLACK},
                new float[] {0.0f, 0.7f, 1.0f},
                Shader.TileMode.CLAMP);

        paint.setShader(backGrad);

        canvas.drawCircle(centerX(), centerY(), getRadius(), paint);

        paint.setShader(null);
        paint.setColor(Color.BLACK);
        paint.setAlpha(0x90);

        Path underHatPath = new Path();
        Point underHatPathPencil = Point.from(Vector2.up().scale(getRadius() * 0.7f));
        Point underHatPathStart = Point.from(getCenter().add(underHatPathPencil));
        underHatPath.moveTo(underHatPathStart.x, underHatPathStart.y);

        for (int i = 0; i < 8; i++) {
            underHatPathPencil = Point.from(underHatPathPencil.rotate((float) Math.toRadians(360.0f / 8.0f)));
            underHatPathStart = Point.from(getCenter().add(underHatPathPencil));
            underHatPath.lineTo(underHatPathStart.x, underHatPathStart.y);
        }

        canvas.drawPath(underHatPath, paint);

        RadialGradient hatGrad = new RadialGradient(
                hatPosition.x - getHatRadius() / 5,
                hatPosition.y - getHatRadius() / 5,
                getHatRadius() * 1.8f,
                new int[] {Color.LTGRAY, Color.GRAY, Color.DKGRAY, Color.BLACK},
                new float[] {0.0f, 0.5f, 0.7f, 0.8f},
                Shader.TileMode.CLAMP);

        paint.setShader(hatGrad);
        paint.setAlpha(0xff);
        canvas.drawCircle(hatPosition.x, hatPosition.y, hatRadius, paint);
    }

    @Override
    public void update() {
        Touches touches = Touches.getInstance();
        Point touchPoint = touches.getTouchPoint();

        setPressed(touches);

        if (isPressed()) {
            //System.out.println("Joystick touched.");
            this.hatPosition = getCenter().to(touchPoint);
            this.hatPosition = hatPosition.scale((float) (Math.min(1.0, getHatRadius() / hatPosition.getMagnitude())));

            Vector2 newPosition = getHatPosition().normalize().scale(getHatPosition().getMagnitude() / getHatRadius());

            if (newPosition.getMagnitude() > 0.2) {
                position = newPosition;
                active = true;
            } else {
                active = false;
            }

            this.hatPosition = getCenter().add(getHatPosition());
        } else {
            this.hatPosition = getCenter();
            this.position.x = 0;
            this.position.y = 0;
        }
    }

    private void setPressed(Touches touches) {

        if (!isPressed()
                && touches.isPressed()
                && !touches.isDragging()
                && this.collidepoint_circle(touches.getTouchPoint())) {
            setCenter(touches.getTouchPoint());
            pressed = true;
            return;
        }

        if (isPressed() && touches.isPressed()) {
            pressed = true;
            return;
        }

        if (!isPressed() && this.collidepoint_circle(touches.getTouchPoint())) {
            if (touches.isPressed()) {
                pressed = true;
                return;
            }
        }

        setCenter(Point.from(this.homePosition)); // snap back when released

        pressed = false;
        return;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isActive() {
        return pressed && active;
    }

    private Vector2 getHatPosition() {
        return hatPosition;
    }

    private float getHatRadius() {
        return hatRadius;
    }

    public void updateHomePosition() {
        this.homePosition = Vector2.from(getCenter());
    }
}
