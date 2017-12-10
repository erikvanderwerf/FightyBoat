package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gmail.eski787.fightyboat.game.Ship;

/**
 * Created by Erik on 12/6/2017.
 */

public class PlaceShipSeaPresenter extends SeaPresenter {
    private static final String TAG = PlaceShipSeaPresenter.class.getSimpleName();

    @Override
    public boolean onClick(@NonNull Point coordinate) {
        Log.d(TAG, "Click Event: " + coordinate);
        Ship ship = getShipAtCoordinate(coordinate);
        if (ship != null) {
            onShipClick(ship);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLongClick(@NonNull Point coordinate) {
        // TODO: Long Selection of ship highlights, brings up option menu.
        // TODO: Option menu allows for rotation and deletion of ship.
        Ship ship = getShipAtCoordinate(coordinate);
        if (ship != null) {
            Log.d(TAG, "Long click ship");
        }
        return false;
    }

    private void onShipClick(@NonNull Ship ship) {
        assert mSea != null;

        Ship.Orientation toggle = ship.getOrientation().toggle();
        ship.setOrientation(toggle);

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
}
