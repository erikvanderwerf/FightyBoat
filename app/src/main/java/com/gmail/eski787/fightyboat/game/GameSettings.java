package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik on 12/13/2016.
 */
public class GameSettings implements Parcelable {
    public static final Creator<GameSettings> CREATOR = new Creator<GameSettings>() {
        @Override
        public GameSettings createFromParcel(Parcel in) {
            return new GameSettings(in);
        }

        @Override
        public GameSettings[] newArray(int size) {
            return new GameSettings[size];
        }
    };
    private final Point seaSize;
    private final int[] ships;

    public GameSettings(Point seaSize, int[] ships) {
        this.seaSize = seaSize;
        this.ships = ships;
    }

    private GameSettings(Parcel in) {
        seaSize = in.readParcelable(Point.class.getClassLoader());
        ships = in.createIntArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(seaSize, flags);
        dest.writeIntArray(ships);
    }
}
