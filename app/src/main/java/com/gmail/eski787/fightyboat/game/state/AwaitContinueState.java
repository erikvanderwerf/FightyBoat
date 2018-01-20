package com.gmail.eski787.fightyboat.game.state;

import android.os.Parcel;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

class AwaitContinueState extends TurnState {
    static final Creator<AwaitContinueState> CREATOR = new Creator<AwaitContinueState>() {
        @Override
        public AwaitContinueState createFromParcel(Parcel in) {
            return new AwaitContinueState(in);
        }

        @Override
        public AwaitContinueState[] newArray(int size) {
            return new AwaitContinueState[size];
        }
    };

    AwaitContinueState() {
        super(STATE_CONTINUE);
    }

    private AwaitContinueState(Parcel in) {
        super(in);
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_CONTINUE) {
            game.advancePlayer();
            return new LockedState();
        }
        return super.handleAction(game, action);
    }
}
