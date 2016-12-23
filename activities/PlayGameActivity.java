package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;
import com.google.common.collect.Iterables;

public class PlayGameActivity extends AppCompatActivity {

    private Game mGame;
    private Iterable<Player> mPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        // Get extras
        Intent intent = getIntent();
        mGame = intent.getParcelableExtra("game");

        mPlayers = Iterables.cycle(mGame.players);
    }
}
