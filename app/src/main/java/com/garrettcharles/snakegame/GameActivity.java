package com.garrettcharles.snakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.garrettcharles.gamelibrary.Game;
import com.garrettcharles.singleton.Cache;

public class GameActivity extends AppCompatActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cache.getInstance().setWindowManager(getWindowManager());

        game = new SnakeGame(this);
        setContentView(game);
    }

    @Override
    public void onBackPressed() {
        ((SnakeGame) Cache.getInstance().getGame()).stopMusic();
        super.onBackPressed();
    }
}