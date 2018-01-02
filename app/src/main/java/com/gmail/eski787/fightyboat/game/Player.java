package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Essentially a bean containing information for a player.
 */

public class Player implements Parcelable {
    static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
    private static final String TAG = Player.class.getSimpleName();

    @NonNull
    private String mName;
    @NonNull
    private Sea mSea;
    //    @NonNull
//    private Radar mRadar;
    @NonNull
    private LockSettings mLockSettings;

    public Player(@NonNull String name, @NonNull Sea sea) {
        mName = name;
        mSea = sea;
//        mRadar = radar;
        mLockSettings = new LockSettings.ButtonLockSettings();
    }

    /**
     * Expecting parcel in order:
     * 1. Name
     * 2. Sea
     * 3. Radar
     * 4. LockSettings class name
     * 5. LockSettings
     *
     * @param in Parcel to unpack.
     */
    private Player(Parcel in) {
        mName = in.readString();
        mSea = in.readParcelable(Sea.class.getClassLoader());
//        mRadar = in.readParcelable(Radar.class.getClassLoader());
        String className = in.readString();
        ClassLoader loader;
        try {
            loader = Class.forName(className).getClassLoader();
            mLockSettings = in.readParcelable(loader);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, String.format("Unable to find class (%s). Defaulting to Button.",
                    className));
            mLockSettings = new LockSettings.ButtonLockSettings();
        }

    }

    /**
     * @return Player name
     */
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    /**
     * @return Player Sea
     */
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
//        dest.writeParcelable(mRadar, flags);
        dest.writeString(mLockSettings.getClass().getName());
        dest.writeParcelable(mLockSettings, flags);
    }

    public LockSettings getLockSettings() {
        return mLockSettings;
    }

    public void setLockSettings(LockSettings lockSettings) {
        mLockSettings = lockSettings;
    }

    public boolean isReady() {
        // TODO Figure out actual ready conditions.
        final int MAX_SHIPS = 5;
        boolean shipsReady = mSea.getShips().size() == MAX_SHIPS;
//        for (Ship ship: mSea.getShips()) {
//            shipsReady &= ship != null;
//        }

        return !getName().isEmpty()
                & shipsReady;
    }
}
