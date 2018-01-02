package com.gmail.eski787.fightyboat.presenters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 1/15/2017.
 */

public abstract class RadarPresenter extends SeaPresenter {
    private static final String TAG = RadarPresenter.class.getSimpleName();

    public boolean drawShips = true;

    @Override
    public List<ShipPresenter> getShips() {
        if (drawShips) return super.getShips();
        else return new ArrayList<>();
    }
}
