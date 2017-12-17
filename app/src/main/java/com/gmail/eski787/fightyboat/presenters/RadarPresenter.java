package com.gmail.eski787.fightyboat.presenters;

import android.graphics.PointF;

/**
 * Created by Erik on 1/15/2017.
 */

public class RadarPresenter extends GridPresenter {
    @Override
    public boolean onClick(PointF coordinate) {
        return false;
    }

    @Override
    public boolean onLongClick(PointF coordinate) {
        return false;
    }
}
