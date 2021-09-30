package com.garrettcharles.snakegame;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.garrettcharles.singleton.Cache;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        TextView gameOverScoreView = findViewById(R.id.gameOverScore);
        StringBuilder sb = new StringBuilder("Score: ");
        sb.append(Cache.getInstance().getScore());
        gameOverScoreView.setText(sb.toString());
    }

    @Override
    public void onBackPressed() {
        navigateToStartScreen(null);
    }

    public void navigateToStartScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}