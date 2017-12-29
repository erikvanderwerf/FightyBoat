package com.gmail.eski787.fightyboat.activities;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerFragment;
import com.gmail.eski787.fightyboat.game.Player;

/**
 * This Activity contains all the required code for displaying a user's lock screen and creating the
 * necessary callback hooks for when the user unlocks the screen. Use a subclass of this activity
 * whenever a player's lock screen will be displayed.
 */
public abstract class LockableActivity extends AppCompatActivity implements LockFragment.LockInteraction {
    private static final String TAG = LockableActivity.class.getSimpleName();
    private static final String ARG_LOCKED = "ARG_LOCKED";
    private boolean mLocked = false;

    /**
     * @return The fragment id for the {@link LockableActivity} to replace with the lock and player
     * fragments.
     */
    @IdRes
    protected abstract int getFragmentId();

    public boolean isLocked() {
        return mLocked;
    }

    /**
     * Replace {@link LockableActivity#getFragmentId()} with the passed player's lock screen.
     *
     * @param player The player whose lock screen to display.
     */
    protected final void lock(Player player) {
        mLocked = true;
        PlayerFragment mFragment;

        try {
            mFragment = player.getLockFragment();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, String.format("Unable to instantiate player lock (%s). Defaulting to " +
                    "Button.", player.getLockSettings()));
            mFragment = ButtonLockFragment.newInstance(player);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentId(), mFragment)
                .commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLocked = savedInstanceState.getBoolean(ARG_LOCKED);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_LOCKED, mLocked);
    }

    @Override
    @CallSuper
    public void onSuccessfulUnlock() {
        mLocked = false;
    }
}
