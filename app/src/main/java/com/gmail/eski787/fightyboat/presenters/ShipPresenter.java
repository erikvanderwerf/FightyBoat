package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;

import com.gmail.eski787.fightyboat.game.Ship;

/**
 * Created by Erik on 12/30/2016.
 */

public class ShipPresenter implements Presenter {
    private final Ship mShip;

    public ShipPresenter(Ship ship) {
        this.mShip = ship;
    }

    public Point getOrigin() {
        return mShip.getOrigin();
    }

    public int getLength() {
        return mShip.getLength();
    }

    public Ship.Orientation getOrientation() {
        return mShip.getOrientation();
    }

    public boolean contains(Point point) {
        return mShip.contains(point);
    }

    public ShipCap.CapType getStartCap() {
        return mShip.getStartCap();
    }

    public ShipCap.CapType getEndCap() {
        return mShip.getEndCap();
    }
}
