package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;

import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Presents the game logic model for {@link Sea} to be used by a {@link com.gmail.eski787.fightyboat.views.GridView}.
 */

public abstract class SeaPresenter extends GridPresenter {
    private static final String TAG = ShipPresenter.class.getSimpleName();
    @Nullable
    Sea mSea;

    public List<ShipPresenter> getShips() {
        if (mSea == null) {
            throw new RuntimeException("Cannot get ships without Sea attached.");
        }
        List<ShipPresenter> ships = new LinkedList<>();
        for (Ship ship : mSea.getShips()) {
            ships.add(new ShipPresenter(ship));
        }
        return ships;
    }

    public Point getSize() {
        if (mSea == null) {
            throw new RuntimeException("Cannot get size with no sea.");
        }
        return mSea.getSize();
    }

    public Sea.SeaStatus getStatus(int x, int y) {
        if (mSea == null) {
            throw new RuntimeException("Cannot get status with no sea.");
        }
        return mSea.getStatus(x, y);
    }

    public void setSea(Sea sea) {
        mSea = sea;
    }

    @Nullable
    Ship getShipAtCoordinate(@NonNull Point coordinate) {
        if (mSea == null) {
            throw new RuntimeException("Cannot find ships with no sea.");
        }
        for (Ship ship : mSea.getShips()) {
            if (ship.contains(coordinate))
                return ship;
        }
        return null;
    }

    protected final Point intCoordinate(PointF coordinate) {
        return new Point(((int) coordinate.x), ((int) coordinate.y));
    }

    /**
     * Basically just a wrapper for Ship until I figure out if there is any
     * use to having a presenter for something that's basically already
     * read-only.
     */
    public static class ShipPresenter implements Presenter {
        private final Ship mShip;

        ShipPresenter(Ship ship) {
            this.mShip = ship;
        }

        public ShipPresenter(ShipPresenter shipPresenter) {
            mShip = new Ship(shipPresenter.mShip);
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

        public AppColors getColor() {
            return AppColors.STEEL_GRAY;
        }
    }
}
