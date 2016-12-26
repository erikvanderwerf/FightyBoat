package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.GameSettings;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Sea;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNewGame(View view) {
        Player[] players = new Player[2];
        players[0] = new Player("Alice", new Sea(10, 10));
        players[1] = new Player("Bob", new Sea(10, 10));

        GameSettings settings = new GameSettings();
        settings.seaSize.set(10, 10);

        Game game = new Game(players, settings);

        Intent new_game = new Intent(this, PlaceShipActivity.class);
        new_game.putExtra(IntentConstant.GAME, game);

        startActivity(new_game);
    }
}
