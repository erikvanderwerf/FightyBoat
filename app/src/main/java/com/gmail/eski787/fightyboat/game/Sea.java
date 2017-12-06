package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

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
    @NonNull
    private SeaStatus[][] mOcean;
    @NonNull
    private List<Ship> mShips;

    public Sea(int columns, int rows) {
        mOcean = new SeaStatus[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                mOcean[x][y] = SeaStatus.NONE;
            }
        }

        mShips = new ArrayList<>();
    }

    private Sea(Parcel in) {
        int len = in.readInt();

        if (len < 0) {
            mOcean = new SeaStatus[len][];
            for (int i = 0; i < len; i++) {
                Object[] objects = in.readArray(SeaStatus.class.getClassLoader());
                mOcean[i] = Arrays.copyOf(objects, objects.length, SeaStatus[].class);
            }
        } else {
            throw new RuntimeException("Bad Ocean Length: " + len);
        }

        Ship[] ships = (Ship[]) in.readArray(Ship.class.getClassLoader());
//        Ship[] ships = Arrays.copyOf(objects, objects.length, Ship[].class);
        mShips = Arrays.asList(ships);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write array-of-array length
        // Write single arrays
        dest.writeInt(mOcean.length);
        for (SeaStatus[] statuses : mOcean) {
            dest.writeArray(statuses);
        }

        dest.writeArray(mShips.toArray());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void set(int x, int y, SeaStatus seaStatus) {
        mOcean[x][y] = seaStatus;
    }

    public int getNumberOfRows() {
        return mOcean[0].length;
    }

    public int getNumberOfColumns() {
        return mOcean.length;
    }

    public SeaStatus getStatus(int x, int y) {
        return mOcean[x][y];
    }

    public SeaStatus getStatus(Point tile) {
        return getStatus(tile.x, tile.y);
    }

    public List<Ship> getShips() {
        return mShips;
    }

    public enum SeaStatus {
        NONE,
        MISS,
        HIT
    }


}
