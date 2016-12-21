package com.gmail.eski787.fightyboat;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

/**
 * Created by Erik on 12/13/2016.
 */
public class GameSettings implements Parcelable {
    public Point seaSize = new Point();
    public int[] ships = {2, 3, 3, 4, 5};

    public GameSettings() {

    }

    protected GameSettings(Parcel in) {
        seaSize = in.readParcelable(getClass().getClassLoader());
        ships = in.createIntArray();
    }

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
