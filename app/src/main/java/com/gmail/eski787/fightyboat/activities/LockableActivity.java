package com.gmail.eski787.fightyboat.activities;

import android.support.v7.app.AppCompatActivity;

import com.gmail.eski787.fightyboat.fragments.LockFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerFragment;
import com.gmail.eski787.fightyboat.game.Player;

import java.util.Iterator;

/**
 * This Activity contains all the required code for displaying a user's lock screen and creating the
 * necessary callback hooks for when the user unlocks the screen.
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
    protected abstract int getFragmentId();

    /**
     * Set the Player iterator.
     */
    protected void setPlayers(Iterator<Player> players) {
        mPlayers = players;
    }

    /**
     * Lock the screen with the next user's lock screen in the Player iterator. If there is no
     * next Player, call the onIteratorComplete.
     */
    protected void advanceAndLock() {
        if (mPlayers == null || !mPlayers.hasNext()) {
            onIteratorComplete();
        }
        lock(mPlayers.next());
    }

    /**
     * Called when advanceAndLock is called and the end of the Player iterator has been reached
     * or if the Player iterator is null.
     */
    protected abstract void onIteratorComplete();

    /**
     * Display the player's chosen lock screen to the fragment from getFragmentId.
     *
     * @param player The player whose lock screen to display.
     */
    private void lock(Player player) {
        mLockedPlayer = player;
        mFragment = mLockedPlayer.getLockFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(getFragmentId(), mFragment)
                .commit();
    }

    /**
     * Called by the {@link LockFragment} when the user has unlocked the displayed lock.
     * Simply call the abstract onSuccessfulUnlock with the current player.
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
