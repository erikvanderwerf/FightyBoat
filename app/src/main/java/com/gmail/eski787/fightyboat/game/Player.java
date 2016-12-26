package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik on 12/13/2016.
 */

public class Player implements Parcelable {
    private String mName;
    private Sea mSea;

    public Player(String name, Sea sea) {
        this.mName = name;
        mSea = sea;
    }

    protected Player(Parcel in) {
        mName = in.readString();
        mSea = in.readParcelable(Sea.class.getClassLoader());
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

    public String getName() {
        return mName;
    }

    public Sea getSea() {
        return mSea;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeParcelable(mSea, flags);
    }
}
