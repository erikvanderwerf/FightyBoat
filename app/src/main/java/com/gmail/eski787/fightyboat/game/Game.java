package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik on 12/13/2016.
 */

public class Game implements Parcelable {
    private final Player[] players;
    private final GameSettings settings;

    public Game(Player[] players, GameSettings settings) {
        this.players = players;
        this.settings = settings;
    }

    protected Game(Parcel in) {
        players = in.createTypedArray(Player.CREATOR);
        settings = in.readParcelable(getClass().getClassLoader());
    }

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

    public int getNumberOfPlayers() {
        return players.length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(players, flags);
        dest.writeParcelable(settings, flags);
    }
}
