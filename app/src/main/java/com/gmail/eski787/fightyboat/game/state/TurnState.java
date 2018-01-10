package com.gmail.eski787.fightyboat.game.state;

import android.util.Log;

/**
 * Created by Erik on 1/9/2018.
 */

public abstract class TurnState {
    private static String TAG = TurnState.class.getName();

//    protected Game mGame;

    /**
     * @param action The action to handle.
     * @return If nonnull, initiate a state change to the returned {@link TurnState}.
     */
    public TurnState handleAction(GameAction action) {
        Log.d(TAG, String.format("Unknown Action %d for %s", action.getActionId(), getClass().getName()));
        return null;
    }
}
