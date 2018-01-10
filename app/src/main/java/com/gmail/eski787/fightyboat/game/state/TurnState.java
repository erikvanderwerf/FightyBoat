package com.gmail.eski787.fightyboat.game.state;

/**
 * Created by Erik on 1/9/2018.
 */

public interface TurnState {
    /**
     * @param action The action to handle.
     * @return If nonnull, initiate a state change to the returned {@link TurnState}.
     */
    TurnState handleAction(GameAction action);
}
