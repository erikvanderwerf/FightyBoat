package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

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
    private static final int AWAITING_SELECTION = 0;
    private static final int AWAITING_FIRE = 1;
    private static final int AWAITING_CONTINUE = 2;
    private final Player[] mPlayers;
    private final GameSettings mSettings;
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

    public Player[] getPlayers() {
        return mPlayers;
    }

    @IntDef({AWAITING_SELECTION, AWAITING_FIRE, AWAITING_CONTINUE})
    public @interface TurnState {
    }
}
