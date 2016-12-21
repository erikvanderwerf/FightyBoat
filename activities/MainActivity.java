package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gmail.eski787.fightyboat.Game;
import com.gmail.eski787.fightyboat.GameSettings;
import com.gmail.eski787.fightyboat.Player;
import com.gmail.eski787.fightyboat.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNewGame(View view) {
        Player[] players = new Player[2];
        players[0] = new Player("Alice");
        players[1] = new Player("Bob");

        GameSettings settings = new GameSettings();
        settings.seaSize.set(10, 10);

        Game game = new Game(players, settings);

        Intent new_game = new Intent(this, ShipPlacementActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("game", game);

        startActivity(new_game, bundle);
    }
}
