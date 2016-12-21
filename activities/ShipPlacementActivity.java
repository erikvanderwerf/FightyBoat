package com.gmail.eski787.fightyboat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gmail.eski787.fightyboat.Game;
import com.gmail.eski787.fightyboat.R;

public class ShipPlacementActivity extends AppCompatActivity {
    public final String TAG = ShipPlacementActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_placement);

        Game game = savedInstanceState.getParcelable("game");

        Log.d(TAG, game.toString());
    }
}
