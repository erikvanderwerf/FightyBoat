package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds information for a Player's ships and hits taken
 * Created by Erik on 12/13/2016.
 */
public class Sea implements Parcelable {
    public static final Creator<Sea> CREATOR = new Creator<Sea>() {
        @Override
        public Sea createFromParcel(Parcel in) {
            return new Sea(in);
        }

        @Override
        public Sea[] newArray(int size) {
            return new Sea[size];
        }
    };
    private static final String TAG = Sea.class.getSimpleName();
    private static final int NUMBER_OF_SHIPS = 5;
    @Nullable
    private Status[][] mOcean;
    @Nullable
    private List<Ship> mShips;

    public Sea(int columns, int rows) {
        mOcean = new Status[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                mOcean[x][y] = Status.NONE;
            }
        }

        mShips = new ArrayList<>(NUMBER_OF_SHIPS);
    }

    private Sea(Parcel in) {
        int len = in.readInt();

        if (len != -1) {
            mOcean = new Status[len][];
            for (int i = 0; i < len; i++) {
                Object[] objects = in.readArray(Status.class.getClassLoader());
                mOcean[i] = Arrays.copyOf(objects, objects.length, Status[].class);
            }
        } else {
            // No ocean?
        }

        Object[] objects = in.readArray(Ship.class.getClassLoader());
        Ship[] ships = Arrays.copyOf(objects, objects.length, Ship[].class);
        mShips = Arrays.asList(ships);
        //mShips = new ArrayList<Ship>(objects);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mOcean != null) {
            // Write array-of-array length
            // Write single arrays
            dest.writeInt(mOcean.length);
            for (Status[] statuses : mOcean) {
                dest.writeArray(statuses);
            }
        } else {
            dest.writeInt(-1);
        }

        Object[] array = null;
        if (mShips != null) {
            array = mShips.toArray();
        }
        dest.writeArray(array);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void set(int x, int y, Status status) {
        if (mOcean != null) {
            mOcean[x][y] = status;
        }
    }

    public int getNumberOfRows() {
        if (mOcean != null) {
            return mOcean[0].length;
        } else {
            Log.d(TAG, "getNumberOfRows Ocean is null");
            return -1;
        }
    }

    public int getNumberOfColumns() {
        if (mOcean != null) {
            return mOcean.length;
        } else {
            return -1;
        }
    }

    public Status getStatus(int x, int y) {
        if (mOcean != null) {
            return mOcean[x][y];
        } else {
            return null;
        }
    }

    public List<Ship> getShips() {
        return mShips;
    }

    public enum Status {
        NONE,
        MISS,
        HIT
    }


}
