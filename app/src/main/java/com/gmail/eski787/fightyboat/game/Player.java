package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;

/**
 * Created by Erik on 12/13/2016.
 */

public class Player implements Parcelable {
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
    private static final String TAG = Player.class.getSimpleName();
    private String mName;
    private Sea mSea;
    //    private Radar mRadar;
    private Class<? extends LockFragment> mLockClass = ButtonLockFragment.class;

    public Player(String name, Sea sea) {
        this.mName = name;
        mSea = sea;
    }

    private Player(Parcel in) {
        mName = in.readString();
        mSea = in.readParcelable(Sea.class.getClassLoader());
        String className = in.readString();
        try {
            // noinspection unchecked
            mLockClass = (Class<? extends LockFragment>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "Unable to find class " + className);
        }
    }

    public Class<? extends LockFragment> getLockClass() {
        return mLockClass;
    }

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
        dest.writeString(mLockClass.getCanonicalName());
    }
}
