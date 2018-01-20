package com.gmail.eski787.fightyboat.game.state;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

public class LockedState extends TurnState {
    public LockedState() {
        super(STATE_LOCKED);
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_UNLOCK) {
            return new AwaitSelectionState();
        }
        return super.handleAction(game, action);
    }
}
