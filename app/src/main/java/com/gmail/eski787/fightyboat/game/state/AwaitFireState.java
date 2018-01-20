package com.gmail.eski787.fightyboat.game.state;

import android.graphics.Point;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

class AwaitFireState extends TurnState {
    private final Point selected;

    public AwaitFireState(Point selected) {
        super(STATE_FIRE);
        this.selected = selected;
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_PLAY_BUTTON) {
            int player = action.getPlayer();
            game.getOpponents().get(player).getSea().hit(selected);
            return new AwaitContinueState();
        }
        return super.handleAction(game, action);
    }
}
