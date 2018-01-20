package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.ArraySet;

import com.gmail.eski787.fightyboat.game.state.GameAction;
import com.gmail.eski787.fightyboat.game.state.LockedState;
import com.gmail.eski787.fightyboat.game.state.TurnState;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @NonNull
    private final Player[] mPlayers;
    @NonNull
    private final GameSettings mSettings;
    @NonNull
    private final Set<GameChangeListener> mListeners = new ArraySet<>();
    @NonNull
    private TurnState mTurnState;
    private int mCurrentPlayerIndex;

    public Game(@NonNull Player[] players, @NonNull GameSettings settings) {
        this.mPlayers = players;
        this.mSettings = settings;
        mTurnState = new LockedState();
        mCurrentPlayerIndex = 0;
    }

    private Game(Parcel in) {
        mPlayers = in.createTypedArray(Player.CREATOR);
        mSettings = in.readParcelable(GameSettings.class.getClassLoader());
        mTurnState = in.readParcelable(TurnState.class.getClassLoader());
        mCurrentPlayerIndex = in.readInt();
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
        dest.writeParcelable(mTurnState, flags);
        dest.writeInt(mCurrentPlayerIndex);
    }

    public boolean addGameChangeListener(GameChangeListener gameChangeListener) {
        return mListeners.add(gameChangeListener);
    }

    public void sendAction(GameAction gameAction) {
        TurnState newState = mTurnState.handleAction(this, gameAction);
        if (newState != null) {
            mTurnState = newState;
        }
        mListeners.forEach(GameChangeListener::onGameInvalidate);
    }

    public List<Player> getOpponents() {
        return Stream.of(mPlayers).filter(player -> player != getCurrentPlayer()).collect(Collectors.toCollection(LinkedList::new));
    }

    public Player getCurrentPlayer() {
        return mPlayers[mCurrentPlayerIndex];
    }

    public boolean removeGameChangeListener(GameChangeListener gameChangeListener) {
        return mListeners.remove(gameChangeListener);
    }

    public TurnState getTurnState() {
        return mTurnState;
    }

    public final void advancePlayer() {
        mCurrentPlayerIndex = (mCurrentPlayerIndex + 1) % getNumberOfPlayers();
    }

    public interface GameChangeListener {
        void onGameInvalidate();
    }
}
