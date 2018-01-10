package com.gmail.eski787.fightyboat.game.state;

/**
 * Created by Erik on 1/9/2018.
 */

public class LockedState extends TurnState {
    @Override
    public TurnState handleAction(GameAction action) {
        if (action.getActionId() == GameAction.ACTION_UNLOCK) {
            return new AwaitSelectionState();
        }
        return super.handleAction(action);
    }
}
