package com.gmail.eski787.fightyboat;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik on 12/13/2016.
 */

public class Player implements Parcelable {
    public final String name;

    public Player(String name) {
        this.name = name;
    }

    protected Player(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
