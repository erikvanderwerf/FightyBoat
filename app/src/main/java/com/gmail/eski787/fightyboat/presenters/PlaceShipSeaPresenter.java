package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;

import com.gmail.eski787.fightyboat.game.Ship;

/**
 * This Presenter contains all of the interactions that the user will need to perform to place ships
 * on their Sea.
 */

public class PlaceShipSeaPresenter extends SeaPresenter {
    private static final String TAG = PlaceShipSeaPresenter.class.getSimpleName();
    /**
     * The ship that is currently selected by the user to drag around.
     */
    private Ship mGhostShip;

    @Override
    public boolean onClick(@NonNull PointF coordinate) {
//        Log.d(TAG, "Click Event: " + coordinate);
        Ship ship = getShipAtCoordinate(intCoordinate(coordinate));
        if (ship != null) {
            onShipClick(ship);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLongClick(@NonNull PointF coordinate) {
        assert mSea != null;

        Ship ship = getShipAtCoordinate(intCoordinate(coordinate));
        if (ship != null) {
            mGhostShip = ship;
            mSea.getShips().remove(mGhostShip);
            return true;
        }
        return false;
    }

    private void onShipClick(@NonNull Ship ship) {
        assert mSea != null;

        // Switch ship orientation
        Ship.Orientation toggle = ship.getOrientation().toggle();
        ship.setOrientation(toggle);

        // Move ship to fit Sea bounds.
        Point dimensions = mSea.getSize();
        Point origin = ship.getOrigin();
        switch (toggle) {
            case HORIZONTAL:
                origin.x = Math.min(dimensions.x - ship.getLength(), origin.x);
                break;
            case VERTICAL:
                origin.y = Math.min(dimensions.y - ship.getLength(), origin.y);
                break;
        }
    }

    public boolean hasGhostShip() {
        return mGhostShip != null;
    }

    public ShipPresenter getGhostShip() {
        return new ShipPresenter(mGhostShip) {
            @Override
            public AppColors getColor() {
                return AppColors.GHOST_SHIP;
            }
        };
    }

    /**
     * Drop the ghost ship into the sea at the given coordinate.
     *
     * @param coordinate Coordinate to place the origin of the ship.
     * @return true if the ghost ship was dropped, false otherwise.
     */
    public boolean onDropShip(PointF coordinate) {
        assert mSea != null;

        if (mGhostShip != null) {
            if (coordinate != null) {
                mGhostShip.getOrigin().set((int) coordinate.x, (int) coordinate.y);
            }
            boolean added = mSea.getShips().add(mGhostShip);
            mGhostShip = null;
            return added;
        }
        return false;
    }

    // TODO This should take a ShipType instead of a Ship
    public boolean setGhostShip(Ship ship) {
        if (mGhostShip == null) {
            mGhostShip = ship;
            return true;
        } else {
            return false;
        }
    }

    // TODO Remove this once Ship type decoding happens.
    public boolean setGhostShip(Ship.ShipType shipType) {
        if (mGhostShip == null) {
            mGhostShip = new Ship(new Point(0, 0), Ship.Orientation.HORIZONTAL, shipType);
            return true;
        } else {
            return false;
        }
    }

    public void clearGhostShip() {
        mGhostShip = null;
    }
}
