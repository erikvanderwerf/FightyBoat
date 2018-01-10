package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;

import com.gmail.eski787.fightyboat.game.state.GameAction;
import com.gmail.eski787.fightyboat.game.state.TurnState;

import java.util.Set;

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

    private final Player[] mPlayers;
    private final GameSettings mSettings;
    private final Set<GameChangeListener> mListener = new ArraySet<>();
    private int mCurrentPlayer;
    private TurnState mTurnState;

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

    public void addGameChangeListener(GameChangeListener gameChangeListener) {
        this.mListener.add(gameChangeListener);
    }

    public void sendAction(GameAction gameAction) {
        TurnState newState = mTurnState.handleAction(gameAction);
        if (newState != null) {
//            mTurnState.onExit();
            mTurnState = newState;
//            mTurnState.onEnter();
        }
        mListener.forEach(GameChangeListener::onGameChange);
    }

    public interface GameChangeListener {
        void onGameChange();
    }
}
