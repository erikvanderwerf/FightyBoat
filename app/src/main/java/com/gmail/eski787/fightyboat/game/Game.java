package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.eski787.fightyboat.game.state.GameAction;
import com.gmail.eski787.fightyboat.game.state.TurnState;

import java.util.Locale;
import java.util.stream.IntStream;

/**
 * Created by Erik on 12/13/2016.
 */

public class Game implements Parcelable {
    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
//    public static final int AWAITING_UNLOCK = 0;
//    public static final int AWAITING_SELECTION = 1;
//    public static final int AWAITING_CONTINUE = 3;
//    public static final int AWAITING_FIRE = 2;
//    @IntDef({AWAITING_UNLOCK, AWAITING_SELECTION, AWAITING_FIRE, AWAITING_CONTINUE})
//    public @interface TurnState {}

    private final Player[] mPlayers;

    private final GameSettings mSettings;
    private int mCurrentPlayer;
    private TurnState mTurnState;
    private GameChangeListener mListener;

    public Game(Player[] players, GameSettings settings) {
        this.mPlayers = players;
        this.mSettings = settings;
        mCurrentPlayer = 0;
    }

    private Game(Parcel in) {
        mPlayers = in.createTypedArray(Player.CREATOR);
        mSettings = in.readParcelable(getClass().getClassLoader());
        mCurrentPlayer = in.readInt();
    }

    public final int getNumberOfPlayers() {
        return mPlayers.length;
    }

    @TurnState
    public int getTurnState() {
        return mTurnState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(mPlayers, flags);
        dest.writeParcelable(mSettings, flags);
        dest.writeInt(mCurrentPlayer);
    }

    public Player getCurrentPlayer() {
        return mPlayers[mCurrentPlayer];
    }

    public void setGameChangeListener(GameChangeListener gameChangeListener) {
        this.mListener = gameChangeListener;
    }

    public void sendAction(GameAction gameAction) {
        boolean validTransition = IntStream.of(gameAction.validStates()).anyMatch(x -> x == mTurnState);
        if (!validTransition) {
            throw new RuntimeException(String.format(Locale.getDefault(), "Invalid State Change: State %d, Action %s", mTurnState, gameAction.getClass().getName()));
        }

        mTurnState = gameAction.transition();
        mListener.onGameChange();
    }

    public interface GameChangeListener {
        void onGameChange();
    }
}
