package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;
import com.gmail.eski787.fightyboat.fragments.PlaceShipFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;

import java.util.Arrays;
import java.util.Iterator;

public class PlaceShipActivity extends AppCompatActivity implements LockFragment.LockInteraction, PlaceShipFragment.PlaceShipInteraction {
    public final String TAG = PlaceShipActivity.class.getSimpleName();
    private Game mGame;
    private Iterator<Player> mPlayerIterator;
    private PlayerFragment mFragment;
    private Player currPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_placement);

        // Get game
        Intent intent = getIntent();
        mGame = intent.getParcelableExtra(IntentConstant.GAME);

        // Get player iterable
        mPlayerIterator = Arrays.asList(mGame.getPlayers()).iterator();

        advanceAndLockPlayer();
    }

    private void advanceAndLockPlayer() {
        if (!mPlayerIterator.hasNext()) {
            // Done placing ships
            Intent intent = new Intent(this, PlayGameActivity.class);
            intent.putExtra(IntentConstant.GAME, mGame);
            startActivity(intent);
        } else {
            // Next player place ships.
            currPlayer = mPlayerIterator.next();
            lock();
        }
    }

    private void lock() {
        Class<? extends LockFragment> lockClass = currPlayer.getLockClass();
        try {
            mFragment = lockClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            mFragment = new ButtonLockFragment();
        }

        mFragment.onAttachPlayer(currPlayer);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ship_placement, mFragment)
                .commit();
    }

    @Override
    public void onUnlockAttempt(boolean success) {
        if (success) {
            unlock();
        }
    }

    @Override
    public void onComplete(Player player) {
        advanceAndLockPlayer();
    }

    private void unlock() {
        mFragment = new PlaceShipFragment();
        mFragment.onAttachPlayer(currPlayer);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ship_placement, mFragment)
                .commit();
    }
}
