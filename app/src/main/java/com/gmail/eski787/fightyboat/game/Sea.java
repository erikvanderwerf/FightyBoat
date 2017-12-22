package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.gmail.eski787.fightyboat.presenters.AppColors;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Ship> mShips;

    public Sea(int columns, int rows, int numShips) {
        mOcean = new SeaStatus[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                mOcean[x][y] = SeaStatus.PEG_NONE;
            }
        }

        mShips = new HashSet<>(numShips);
    }

    private Sea(Parcel in) {
        int len = in.readInt();

        if (len > 0) {
            mOcean = new SeaStatus[len][];
            for (int i = 0; i < len; i++) {
                Object[] objects = in.readArray(SeaStatus.class.getClassLoader());
                mOcean[i] = Arrays.copyOf(objects, objects.length, SeaStatus[].class);
            }
        } else {
            throw new RuntimeException("Bad Ocean Length: " + len);
        }

        Object[] shipObjects = in.readArray(Ship.class.getClassLoader());
        Ship[] shipArray = Arrays.copyOf(shipObjects, shipObjects.length, Ship[].class);
        mShips = new HashSet<>(shipArray.length);
        Collections.addAll(mShips, shipArray);
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

    public int getNumberOfRows() {
        return mOcean[0].length;
    }

    public int getNumberOfColumns() {
        return mOcean.length;
    }

    public Point getSize() {
        return new Point(getNumberOfRows(), getNumberOfColumns());
    }

    public SeaStatus getStatus(int x, int y) {
        return mOcean[x][y];
    }

    public SeaStatus getStatus(Point tile) {
        return getStatus(tile.x, tile.y);
    }

    public Set<Ship> getShips() {
        return mShips;
    }

    public void setStatus(int x, int y, SeaStatus seaStatus) {
        mOcean[x][y] = seaStatus;
    }

    public enum SeaStatus {
        PEG_NONE(AppColors.PEG_NONE),
        PEG_MISS(AppColors.PEG_MISS),
        PEG_HIT(AppColors.PEG_HIT);

        private final AppColors color;

        SeaStatus(AppColors color) {
            this.color = color;
        }

        public AppColors getColor() {
            return color;
        }
    }
}
