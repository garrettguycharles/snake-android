package com.garrettcharles.gamelibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.garrettcharles.singleton.Cache;
import com.garrettcharles.singleton.Touches;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public abstract class Game extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Canvas myCanvas;
    private SurfaceHolder myHolder;
    private boolean isDrawing;

    protected Rect screen;

    public Rect getScreen() {
        return screen;
    }

    protected Paint paint = new Paint();

    private List<Sprite> sprites = new ArrayList<>();

    public Game(Context context) {
        super(context);

        Cache.getInstance().setGame(this);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Touches.getInstance().setPressed();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Touches.getInstance().setDragging();
                        break;
                    case MotionEvent.ACTION_UP:
                        Touches.getInstance().setReleased();
                        break;
                    default:

                }
                Touches.getInstance().setTouchPoint(motionEvent.getX(), motionEvent.getY());
                return true;
            }
        });

        initView();
    }

    private void initView() {
        myHolder = getHolder();
        myHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        isDrawing = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing) {
            long fStart = System.currentTimeMillis();

            // draw the frame on the canvas
            try {
                myCanvas = myHolder.lockCanvas();
                drawFrame(myCanvas);

            } catch (Exception e) {
            } finally {
                if (myCanvas != null) {
                    myHolder.unlockCanvasAndPost(myCanvas);
                }
            }

            update();

            long drawTime = System.currentTimeMillis() - fStart;

            // wait for FPS
            try {
                Thread.sleep(Math.max(0, (long) (1000 / 60) - drawTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected long time = System.currentTimeMillis();

    public void drawFrame(Canvas canvas) {

        if (screen == null) {
            screen = new Rect(0, 0, 10, 10);
        }

        screen.setW(canvas.getWidth());
        screen.setH(canvas.getHeight());

        paint.setColor(Color.WHITE);

        canvas.drawRect(new RectF(0, 0, screen.w, screen.h), paint);

        drawSprites(canvas);

        /*

        long timeNow = System.currentTimeMillis();

        StringBuilder timeDiff = new StringBuilder("FPS: ");
        timeDiff.append(Float.toString(1.0f / (((float) (timeNow - time)) / 1000)));

        time = timeNow;

        paint.setColor(Color.BLACK);
        paint.setTextSize(30.0f);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(timeDiff.toString(), 10 , 30 + 10, paint);

         */
    }

    protected void drawSprites(Canvas canvas) {
        for (Sprite s : sprites) {
            s.draw(canvas);
        }
    }

    public void update() {
        for (Sprite s : sprites) {
            s.update();
        }
    }

    public void addSprite(Sprite s) {
        if (!sprites.contains(s)) {
            sprites.add(s);
        }
    }

    public void removeSprite(Sprite s) {
        if (sprites.contains(s)) {
            sprites.remove(s);
        }
    }

    public abstract Joystick getJoystick();

}
