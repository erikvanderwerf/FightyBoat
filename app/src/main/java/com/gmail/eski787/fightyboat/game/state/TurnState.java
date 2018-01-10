package com.gmail.eski787.fightyboat.game.state;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

public abstract class TurnState {
    protected Game mGame;

    /**
     * @param action The action to handle.
     * @return If nonnull, initiate a state change to the returned {@link TurnState}.
     */
    public abstract TurnState handleAction(GameAction action);
}
