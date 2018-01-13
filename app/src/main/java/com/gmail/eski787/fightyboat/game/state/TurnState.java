package com.gmail.eski787.fightyboat.game.state;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.util.Log;

import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.util.Util;

/**
 * Represents a state that a game turn state can be.
 */

public abstract class TurnState implements Parcelable {
    public static final int STATE_LOCKED = 0;
    public static final int STATE_SELECTION = 1;
    public static final int STATE_FIRE = 2;
    public static final int STATE_CONTINUE = 3;
    private static String TAG = TurnState.class.getName();
    private final int mState;

    TurnState(@StateId int state) {
        mState = state;
    }

    public int getState() {
        return mState;
    }

    /**
     * @param action The action to handle.
     * @return If nonnull, initiate a state change to the returned {@link TurnState}.
     */
    public TurnState handleAction(Game game, GameAction action) {
        Log.d(TAG, String.format("Unknown Action %d for %s", action.getActionId(), getClass().getName()));
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    @IntDef({STATE_LOCKED, STATE_SELECTION, STATE_FIRE, STATE_CONTINUE})
    public @interface StateId {
    }

    /**
     * Created by Erik on 1/9/2018.
     */

    public static class LockedState extends TurnState {
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

    /**
     * Created by Erik on 1/9/2018.
     */

    static class AwaitSelectionState extends TurnState {
        AwaitSelectionState() {
            super(STATE_SELECTION);
        }

        @Override
        public TurnState handleAction(Game game, GameAction action) {
            if (action.getActionId() == GameAction.ACTION_SELECT) {
                Point coordinate = Util.intCoordinate(action.getCoordinate());
                Sea.SeaStatus status = game.getOpponents().getSea().getStatus(coordinate);
                if (status == Sea.SeaStatus.PEG_NONE) {
                    return new AwaitFireState(coordinate);
                } else {
                    return null;
                }
            }
            return super.handleAction(game, action);
        }
    }

    /**
     * Created by Erik on 1/9/2018.
     */

    static class AwaitFireState extends TurnState {
        private final Point selected;

        public AwaitFireState(Point selected) {
            super(STATE_FIRE);
            this.selected = selected;
        }

        @Override
        public TurnState handleAction(Game game, GameAction action) {
            if (action.getActionId() == GameAction.ACTION_FIRE) {
                game.getOpponents().getSea().hit(selected);
                return new AwaitContinueState();
            }
            return super.handleAction(game, action);
        }
    }

    /**
     * Created by Erik on 1/9/2018.
     */

    static class AwaitContinueState extends TurnState {
        AwaitContinueState() {
            super(STATE_CONTINUE);
        }

        @Override
        public TurnState handleAction(Game game, GameAction action) {
            if (action.getActionId() == GameAction.ACTION_CONTINUE) {
                // TODO figure out how to advance player from state machine.
            }
            return super.handleAction(game, action);
        }
    }
}
