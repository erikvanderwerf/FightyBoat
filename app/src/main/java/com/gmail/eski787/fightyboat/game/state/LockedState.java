package com.gmail.eski787.fightyboat.game.state;

import android.util.Log;

/**
 * Created by Erik on 1/9/2018.
 */

public class LockedState extends TurnState {
    private static String TAG = LockedState.class.getName();

    @Override
    public TurnState handleAction(GameAction action) {
        if (action.getActionId() == GameAction.ACTION_UNLOCK) {
            return new AwaitSelectionState();
        }
        Log.d(TAG, String.format("Unknown Action for LockedState %d", action.getActionId()));
        return null;
    }
}
