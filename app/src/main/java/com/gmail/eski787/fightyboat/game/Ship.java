package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.graphics.PointF;
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
    @NonNull
    private ShipCap.CapType startCap = ShipCap.CapType.ROUND;
    @NonNull
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

    public Ship(Ship ship) {
        origin = new Point(ship.origin);
        orientation = ship.orientation;
        length = ship.length;
        startCap = ship.startCap;
        endCap = ship.endCap;
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

    @NonNull
    public ShipCap.CapType getStartCap() {
        return startCap;
    }

    @NonNull
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

    public boolean contains(PointF point) {
        int x = (int) point.x;
        int y = (int) point.y;
        switch (orientation) {
            case VERTICAL:
                return x == origin.x && y >= origin.y && y < origin.y + length;
            case HORIZONTAL:
                return y == origin.y && x >= origin.x && x < origin.x + length;
        }
        throw new RuntimeException("Ignored orientation switch. Why? Case: " + orientation.toString());
    }

    public enum Orientation {
        HORIZONTAL(ShipCap.CapDirection.RIGHT, ShipCap.CapDirection.LEFT),
        VERTICAL(ShipCap.CapDirection.DOWN, ShipCap.CapDirection.UP);

        public final ShipCap.CapDirection start;
        public final ShipCap.CapDirection end;

        Orientation(ShipCap.CapDirection start, ShipCap.CapDirection end) {
            this.start = start;
            this.end = end;
        }

        public Orientation toggle() {
            return this == HORIZONTAL ? VERTICAL : HORIZONTAL;
        }
    }
}
