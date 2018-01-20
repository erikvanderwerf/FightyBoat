package com.gmail.eski787.fightyboat.game.state;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.util.Log;

import com.gmail.eski787.fightyboat.game.Game;

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

    TurnState(Parcel in) {
        mState = in.readInt();
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mState);
    }


    @IntDef({STATE_LOCKED, STATE_SELECTION, STATE_FIRE, STATE_CONTINUE})
    @interface StateId {
    }
}
