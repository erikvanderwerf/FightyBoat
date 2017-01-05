package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.eski787.fightyboat.presenters.ShipCap;

/**
 * Created by Erik on 12/26/2016.
 * POJO for a Ship
 */

public class Ship implements Parcelable {
    public static final Creator<Ship> CREATOR = new Creator<Ship>() {
        @Override
        public Ship createFromParcel(Parcel in) {
            return new Ship(in);
        }

        @Override
        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };
    private Point origin;
    private Orientation orientation;
    private int length;
    private ShipCap.CapType startCap = ShipCap.CapType.ROUND;
    private ShipCap.CapType endCap = ShipCap.CapType.SQUARE;

    private Ship(Parcel in) {
        origin = in.readParcelable(Point.class.getClassLoader());
        orientation = (Orientation) in.readSerializable();
        length = in.readInt();
        startCap = (ShipCap.CapType) in.readSerializable();
        endCap = (ShipCap.CapType) in.readSerializable();
    }

    public Ship(Point origin, Orientation orientation, int length) {
        this.origin = origin;
        this.orientation = orientation;
        this.length = length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(origin, i);
        parcel.writeSerializable(orientation);
        parcel.writeInt(length);
        parcel.writeSerializable(startCap);
        parcel.writeSerializable(endCap);
    }

    public ShipCap.CapType getStartCap() {
        return startCap;
    }

    public ShipCap.CapType getEndCap() {
        return endCap;
    }

    public Point getOrigin() {
        return origin;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getLength() {
        return length;
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
}
