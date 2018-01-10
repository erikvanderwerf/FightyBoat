package com.gmail.eski787.fightyboat.game.state;

/**
 * Created by Erik on 1/9/2018.
 */

class AwaitSelectionState extends TurnState {
    @Override
    public TurnState handleAction(GameAction action) {
        if (action.getActionId() == GameAction.ACTION_SELECT) {
            return new AwaitFireState();
        }
        return super.handleAction(action);
    }
}
