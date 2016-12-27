package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;

/**
 * Created by Erik on 12/26/2016.
 */

public class Ship {
    private Point origin;
    private Orientation orientation;
    private int length;

    public Ship(Point origin, Orientation orientation, int length) {
        this.origin = origin;
        this.orientation = orientation;
        this.length = length;
    }

    public Point getOrigin() {
        return origin;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public int getLength() {
        return length;
    }
}
