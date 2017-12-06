package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 1/15/2017.
 */

public class SeaPresenter implements GridPresenter {
    private static final String TAG = ShipPresenter.class.getCanonicalName();
    private final Sea mSea;

    public SeaPresenter(Sea sea) {
        mSea = sea;
    }

    @Override
    public void onTouchEvent(Point coordinate, MotionEvent event) {

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

    public void setStatus(int x, int y, Sea.SeaStatus status) {
        mSea.setStatus(x, y, status);
    }
}
