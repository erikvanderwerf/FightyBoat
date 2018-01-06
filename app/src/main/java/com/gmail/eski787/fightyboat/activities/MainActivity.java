package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gmail.eski787.fightyboat.R;

/*
Provides the first user interaction with the game. Holds the buttons used for creating a new game,
loading a saved game, and the instructions for play.

TODO Needed drawables
2 male and 2 female captain art (full/small)
Change ship icon
Change lock icon
Ship icons (Carrier, Battleship, Submarine, Destroyer, Cruiser)
Radar/sea spacer decoration
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNewGame(View view) {
        Intent new_game = new Intent(this, NewGameActivity.class);
        startActivity(new_game);
    }
}
