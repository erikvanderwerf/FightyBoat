package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gmail.eski787.fightyboat.fragments.PlayerAdvancementFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Player;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ShipPlacementActivity extends AppCompatActivity implements PlayerAdvancementFragment.OnFragmentInteractionListener {
    public final String TAG = ShipPlacementActivity.class.getName();
    private Game mGame;
    private Iterator<Player> mPlayerIterator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_placement);

        // Get game
        Intent intent = getIntent();
        mGame = intent.getParcelableExtra("game");

        // Initialize Fragment
        PlayerAdvancementFragment advancementFragment = new PlayerAdvancementFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ship_placement, advancementFragment)
                .commit();

        mPlayerIterator = Arrays.asList(mGame.players).iterator();
        if (mPlayerIterator.hasNext()) {
            Player next = mPlayerIterator.next();
            advancementFragment.advanceTo(next, null);
        } else {
            Log.e(TAG, "Game has 0 players. What's up with that?");
        }
    }

    @Override
    public void onFragmentInteraction() {

    }
}
