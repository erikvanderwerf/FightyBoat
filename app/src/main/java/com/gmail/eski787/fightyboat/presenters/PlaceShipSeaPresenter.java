package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;

/**
 * Created by Erik on 12/6/2017.
 */

public class PlaceShipSeaPresenter extends SeaPresenter {
    private static final String TAG = PlaceShipSeaPresenter.class.getCanonicalName();

    public PlaceShipSeaPresenter(Sea sea) {
        super(sea);
    }

    @Override
    public boolean onGridTouchEvent(Point coordinate, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        // TODO: Long Selection of ship highlights, brings up option menu.
        // TODO: Option menu allows for rotation and deletion of ship.
        for (Ship ship : mSea.getShips()) {
            if (ship.contains(coordinate)) {
                onShipTouch(ship);
            }
        }
        return true;
    }

    private void onShipTouch(Ship ship) {
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
