package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.gmail.eski787.fightyboat.presenters.ShipCap;

/**
 * Created by Erik on 12/26/2016.
 * POJO for a Ship
 */

public class Ship implements Parcelable {
    // TODO Ship instantiation needs a Factory.
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
    @NonNull
    private Point origin;
    @NonNull
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

    public Ship(@NonNull Point origin, @NonNull Orientation orientation, int length) {
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

    @NonNull
    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(@NonNull Point origin) {
        this.origin = origin;
    }

    @NonNull
    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(@NonNull Orientation orientation) {
        this.orientation = orientation;
    }

    public int getLength() {
        return length;
    }

    public boolean contains(Point point) {
        switch (orientation) {
            case VERTICAL:
                return point.x == origin.x && point.y >= origin.y && point.y < origin.y + length;
            case HORIZONTAL:
                return point.y == origin.y && point.x >= origin.x && point.x < origin.x + length;
        }
        throw new RuntimeException("Ignored orientation switch. Why? Case: " + orientation.toString());
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL;

        public ShipCap.CapDirection shipStartCap() {
            switch (this) {
                case HORIZONTAL:
                    return ShipCap.CapDirection.RIGHT;
                case VERTICAL:
                    return ShipCap.CapDirection.DOWN;
            }
            throw new RuntimeException("Invalid Ship Orientation: " + this.toString());
        }

        public ShipCap.CapDirection shipEndCap() {
            switch (this) {
                case HORIZONTAL:
                    return ShipCap.CapDirection.LEFT;
                case VERTICAL:
                    return ShipCap.CapDirection.UP;
            }
            throw new RuntimeException("Invalid Ship Orientation " + this.toString());
        }

        public Orientation toggle() {
            return this == HORIZONTAL ? VERTICAL : HORIZONTAL;
        }
    }
}
