package com.gmail.eski787.fightyboat.game.state;

import android.graphics.Point;
import android.os.Parcel;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

class AwaitFireState extends NoSelectionState {
    static final Creator<AwaitFireState> CREATOR = new Creator<AwaitFireState>() {
        @Override
        public AwaitFireState createFromParcel(Parcel in) {
            return new AwaitFireState(in);
        }

        @Override
        public AwaitFireState[] newArray(int size) {
            return new AwaitFireState[size];
        }
    };
    
    private final Point selected;

    AwaitFireState(Point selected) {
        super(STATE_SELECTION);
        this.selected = selected;
    }

    private AwaitFireState(Parcel in) {
        super(in);
        selected = in.readParcelable(Point.class.getClassLoader());
    }

    @Override
    public TurnState handleAction(Game game, GameAction action) {
        if (action.getActionId() == GameAction.ACTION_PLAY_BUTTON) {
            game.getPlayers()[action.getTargetPlayer()].getSea().hit(selected);
            return new AwaitContinueState();
        }
        return super.handleAction(game, action);
    }

    @Override
    public boolean getPlayButtonState() {
        return true;
    }

    @Override
    public String getPlayButtonText() {
        return "Fire";
    }
}
