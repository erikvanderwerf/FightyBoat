package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;

/**
 * Created by Erik on 1/15/2017.
 */

public class RadarPresenter implements GridPresenter {
    @Override
    public boolean onClick(Point coordinate) {
        return false;
    }

    @Override
    public boolean onLongClick(Point coordinate) {
        return false;
    }
}
