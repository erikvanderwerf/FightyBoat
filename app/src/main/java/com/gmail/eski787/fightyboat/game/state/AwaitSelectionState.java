package com.gmail.eski787.fightyboat.game.state;

import android.graphics.Point;
import android.graphics.PointF;

import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.util.Util;

/**
 * Created by Erik on 1/9/2018.
 */

class AwaitSelectionState extends TurnState {
    AwaitSelectionState() {
        super(STATE_SELECTION);
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_SELECT) {
            GameAction.SelectAction selectAction = (GameAction.SelectAction) action;
            PointF touch = selectAction.getCoordinate();
            int player = selectAction.getPlayer();

            Point coordinate = Util.intCoordinate(touch);
            Sea.SeaStatus status = game.getOpponents().get(player).getSea().getStatus(coordinate);
            if (status == Sea.SeaStatus.PEG_NONE) {
                return new AwaitFireState(coordinate);
            } else {
                return null;
            }
        }
        return super.handleAction(game, action);
    }
}
