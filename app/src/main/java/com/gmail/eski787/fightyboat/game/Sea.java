package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by Erik on 12/13/2016.
 */
public class Sea implements Parcelable {
    private static final String TAG = Sea.class.getSimpleName();
    @Nullable private Status[][] ocean;

    public Sea(int columns, int rows) {
        ocean = new Status[columns][rows];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                ocean[x][y] = Status.NONE;
            }
        }
    }

    protected Sea(Parcel in) {
        Log.d(TAG, "Unpackaging Sea");

        int len = in.readInt();
        Status s = (Status) in.readSerializable();

        Log.d(TAG, String.format("Length: %d", len));
        Log.d(TAG, String.format("Initial: %s", s));

        ocean = new Status[len][];
        for (int i = 0; i < len; i++) {
            Object[] objects = in.readArray(Status.class.getClassLoader());
            ocean[i] = Arrays.copyOf(objects, objects.length, Status[].class);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write array-of-array length
        // Write single arrays
        Log.d(TAG, String.format("Writing Length %d, Tile %s", ocean.length, ocean[0][0]));

        dest.writeInt(ocean.length);
        dest.writeSerializable(ocean[0][0]);
        for (Status[] statuses : ocean) {
            dest.writeArray(statuses);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public void set(int x, int y, Status status) {
        ocean[x][y] = status;
    }

    public int getNumberOfRows() {
        if (ocean != null) {
            return ocean[0].length;
        } else {
            Log.d(TAG, "getNumberOfRows Ocean is null");
            return -1;
        }
    }

    public int getNumberOfColumns() {
        return ocean.length;
    }

    public Status getStatus(int x, int y) {
        return ocean[x][y];
    }

    public enum Status {
        NONE,
        MISS,
        HIT
    }


}
