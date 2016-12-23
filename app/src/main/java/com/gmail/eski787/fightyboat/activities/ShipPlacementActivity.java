package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gmail.eski787.fightyboat.fragments.PlaceShipFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerLockFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Player;

import java.util.Arrays;
import java.util.Iterator;

public class ShipPlacementActivity extends AppCompatActivity implements PlayerLockFragment.PlayerLockInteraction, PlaceShipFragment.PlaceShipInteraction {
    public final String TAG = ShipPlacementActivity.class.getName();
    private Game mGame;
    private Iterator<Player> mPlayerIterator;
    private Fragment mFragment;
    private Player currPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_placement);

        // Get game
        Intent intent = getIntent();
        mGame = intent.getParcelableExtra("game");

        // Get player iterable
        mPlayerIterator = Arrays.asList(mGame.players).iterator();

        advanceAndLockPlayer();
    }

    private void advanceAndLockPlayer() {
        if (!mPlayerIterator.hasNext()) {
            // Done placing ships
            Intent intent = new Intent(this, PlayGameActivity.class);
            intent.putExtra("game", mGame);
            startActivity(intent);
            return;
        }

        currPlayer = mPlayerIterator.next();

        mFragment = PlayerLockFragment.newInstance(currPlayer);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ship_placement, mFragment)
                .commit();

    }

    @Override
    public void onUnlockAttempt(boolean success) {
        if (success) {
            mFragment = PlaceShipFragment.newInstance(currPlayer);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_ship_placement, mFragment)
                    .commit();
        }
    }

    @Override
    public void onComplete() {
        advanceAndLockPlayer();
    }
}
