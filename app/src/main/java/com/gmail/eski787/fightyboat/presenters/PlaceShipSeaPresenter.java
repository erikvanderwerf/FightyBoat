package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.util.Log;
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

        // TODO: Selection of ship highlights, brings up option menu.
        // TODO: Option menu allows for rotation and deletion of ship.
        for (Ship ship : mSea.getShips()) {
            if (ship.contains(coordinate)) {
                Log.d(TAG, "TouchEvent inside Ship.");
            }
        }
        return true;
    }
}
