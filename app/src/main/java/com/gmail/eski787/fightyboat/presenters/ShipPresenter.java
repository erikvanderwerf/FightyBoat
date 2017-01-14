package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Canvas;
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

    @Override
    public void draw(Canvas canvas, Point start, Point end) {

    }
}
