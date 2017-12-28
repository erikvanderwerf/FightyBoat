package com.gmail.eski787.fightyboat.activities;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerFragment;
import com.gmail.eski787.fightyboat.game.Player;

import java.util.Iterator;

/**
 * This Activity contains all the required code for displaying a user's lock screen and creating the
 * necessary callback hooks for when the user unlocks the screen. Use a subclass of this activity
 * whenever a player's lock screen will be displayed.
 */
public abstract class LockableActivity extends AppCompatActivity implements LockFragment.LockInteraction {
    public final String TAG = LockableActivity.class.getSimpleName();
    protected PlayerFragment mFragment;
    private Iterator<Player> mPlayers;
    private Player mLockedPlayer;

    /**
     * @return The fragment id for the {@link LockableActivity} to replace with the lock and player
     * fragments.
     */
    protected abstract @IdRes
    int getFragmentId();

    /**
     * Set the Player iterator. When the iterator completes
     * {@link LockableActivity#onIteratorComplete()} is called.
     */
    protected void setPlayers(Iterator<Player> players) {
        mPlayers = players;
    }

    /**
     * Lock the screen with the next user's lock screen in the Player iterator. If there is no
     * next player, call {@link LockableActivity#onIteratorComplete()}.
     * @see LockableActivity#lock(Player)
     */
    protected final void advanceAndLock() {
        if (mPlayers == null || !mPlayers.hasNext()) {
            onIteratorComplete();
        }
        lock(mPlayers.next());
    }

    /**
     * Called when {@link LockableActivity#advanceAndLock()} is called and the end of the player
     * iterator has been reached or if the player iterator is null.
     */
    protected abstract void onIteratorComplete();

    /**
     * Replace {@link LockableActivity#getFragmentId()} with the passed player's lock screen.
     *
     * @param player The player whose lock screen to display.
     */
    protected final void lock(Player player) {
        mLockedPlayer = player;
        try {
            mFragment = mLockedPlayer.getLockFragment();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, String.format("Unable to instantiate player lock (%s). Defaulting to " +
                    "Button.", mLockedPlayer.getLockSettings()));
            mFragment = ButtonLockFragment.newInstance(player);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentId(), mFragment)
                .commit();
    }

    /**
     * Called by the {@link LockFragment} when the user has unlocked the displayed lock.
     * Simply call the abstract onSuccessfulUnlock with the current player.
     * @see LockableActivity#onSuccessfulUnlock(Player)
     */
    @Override
    public final void onSuccessfulUnlock() {
        onSuccessfulUnlock(mLockedPlayer);
    }

    /**
     * Called when the user has unlocked the lock screen for the passed Player.
     *
     * @param player The current player whose lock screen has been unlocked.
     */
    protected abstract void onSuccessfulUnlock(Player player);
}
