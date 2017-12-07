package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents the game logic model for {@link Sea} to be used by a {@link com.gmail.eski787.fightyboat.views.GridView}.
 */

public abstract class SeaPresenter implements GridPresenter {
    private static final String TAG = ShipPresenter.class.getCanonicalName();
    @NonNull
    final Sea mSea;

    public SeaPresenter(@NonNull Sea sea) {
        mSea = sea;
    }

    public List<ShipPresenter> getShips() {
        List<ShipPresenter> ships = new ArrayList<>(mSea.getShips().size());
        for (Ship ship : mSea.getShips()) {
            ships.add(new ShipPresenter(ship));
        }
        return ships;
    }

    public Point getSize() {
        return mSea.getSize();
    }

    public Sea.SeaStatus getStatus(int x, int y) {
        return mSea.getStatus(x, y);
    }

    @Override
    public abstract boolean onGridTouchEvent(Point coordinate, MotionEvent event);
}
