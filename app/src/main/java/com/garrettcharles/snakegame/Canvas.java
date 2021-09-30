package com.garrettcharles.snakegame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Canvas extends View {

    Paint paint = new Paint();
    Rect rect = new Rect(10, 10, 60, 60);
    private android.graphics.Canvas drawCanvas;

    public Canvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        this.drawCanvas = canvas;
        paint.setColor(Color.WHITE);

        canvas.drawRect(new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), paint);

        paint.setColor(Color.RED);

        rect.left = new Random().nextInt(canvas.getWidth());

        canvas.drawRect(rect, paint);
    }

    public void start() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                invalidate();
            }
        }, 0, 1000 / 60);
    }
}
