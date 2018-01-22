package com.gmail.eski787.fightyboat.game.state;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.Parcel;

import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.util.Util;

/**
 * Created by Erik on 1/9/2018.
 */

class NoSelectionState extends TurnState {
    static final Creator<NoSelectionState> CREATOR = new Creator<NoSelectionState>() {
        @Override
        public NoSelectionState createFromParcel(Parcel in) {
            return new NoSelectionState(in);
        }

        @Override
        public NoSelectionState[] newArray(int size) {
            return new NoSelectionState[size];
        }
    };

    NoSelectionState() {
        super(STATE_NO_SELECTION);
    }

    NoSelectionState(int state) {
        super(state);
    }

    NoSelectionState(Parcel in) {
        super(in);
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_SELECT) {
            final GameAction.SelectAction selectAction = (GameAction.SelectAction) action;
            final PointF touch = selectAction.getCoordinate();
            final Sea playerSea = game.getPlayers()[selectAction.getTargetPlayer()].getSea();

            final Point coordinate = Util.intCoordinate(touch);
            final Sea.SeaStatus status = playerSea.getStatus(coordinate);
            if (status == Sea.SeaStatus.PEG_NONE) {
                playerSea.setSelected(coordinate);
                return new AwaitFireState(coordinate);
            } else {
                // User selected invalid tile, do not change states.
                return null;
            }
        }
        return super.handleAction(game, action);
    }

    @Override
    public boolean getPlayButtonState() {
        return false;
    }

    @Override
    public String getPlayButtonText() {
        return "Select";
    }
}
