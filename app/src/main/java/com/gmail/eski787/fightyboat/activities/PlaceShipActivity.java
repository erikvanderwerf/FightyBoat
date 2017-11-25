package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;
import com.gmail.eski787.fightyboat.fragments.PlaceShipFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;

@Deprecated
public class PlaceShipActivity extends AppCompatActivity implements LockFragment.LockInteraction, PlaceShipFragment.PlaceShipInteraction {
    public final String TAG = PlaceShipActivity.class.getSimpleName();
    private Game mGame;
    private PlayerFragment mFragment;
    private Player currPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_ship);

        // Get game
        Intent intent = getIntent();
        mGame = intent.getParcelableExtra(IntentConstant.GAME);

        advanceAndLockPlayer();
    }

    /**
     * Iterate over every player to get their ship choice.
     */
    private void advanceAndLockPlayer() {
        // Iterate over each player.
        for (Player player : mGame.getPlayers()) {
            currPlayer = player;
            lock();
        }

        // Done placing ships
        Intent intent = new Intent(this, PlayGameActivity.class);
        intent.putExtra(IntentConstant.GAME, mGame);
        startActivity(intent);
    }

    /*
    Displays the player's chosen lock screen.
     */
    private void lock() {
        Class<? extends LockFragment> lockClass = currPlayer.getLockClass();
        try {
            mFragment = lockClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Log.e(TAG, "Unable to instantiate " + lockClass.getCanonicalName());
            mFragment = new ButtonLockFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putParcelable(PlayerFragment.ARG_PLAYER, currPlayer);
        mFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ship_placement, mFragment)
                .commit();
    }

    @Override
    public void onSuccessfulUnlock() {
        mFragment = new PlaceShipFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PlayerFragment.ARG_PLAYER, currPlayer);
        mFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ship_placement, mFragment)
                .commit();
    }

    @Override
    public void onShipPlaceComplete(Player player) {
        advanceAndLockPlayer();
    }

}
