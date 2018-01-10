package com.gmail.eski787.fightyboat.game.state;

/**
 * Created by Erik on 1/9/2018.
 */

class AwaitContinueState extends TurnState {
    @Override
    public TurnState handleAction(GameAction action) {
        if (action.getActionId() == GameAction.ACTION_CONTINUE) {
            // TODO figure out how to advance player from state machine.
        }
        return super.handleAction(action);
    }
}
