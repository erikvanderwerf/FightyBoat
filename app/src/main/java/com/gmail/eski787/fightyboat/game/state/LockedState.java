package com.gmail.eski787.fightyboat.game.state;

import android.os.Parcel;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

public class LockedState extends TurnState {
    static final Creator<LockedState> CREATOR = new Creator<LockedState>() {
        @Override
        public LockedState createFromParcel(Parcel in) {
            return new LockedState(in);
        }

        @Override
        public LockedState[] newArray(int size) {
            return new LockedState[size];
        }
    };
    
    public LockedState() {
        super(STATE_LOCKED);
    }

    private LockedState(Parcel in) {
        super(in);
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_UNLOCK) {
            return new NoSelectionState();
        }
        return super.handleAction(game, action);
    }

    @Override
    public boolean getPlayButtonState() {
        return false;
    }

    @Override
    public String getPlayButtonText() {
        return "ERROR";
    }
}
