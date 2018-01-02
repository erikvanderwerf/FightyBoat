package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 1/15/2017.
 */

public abstract class RadarPresenter extends SeaPresenter {
    private static final String TAG = RadarPresenter.class.getSimpleName();

    /**
     * Called during the draw phase of a View to determine if ships should be drawn. Typically will
     * be false but once the game is over may return true.
     *
     * @return Whether to draw ships on the containing {@link android.view.View}.
     * @see com.gmail.eski787.fightyboat.views.SeaView#onDraw(Canvas)
     */
    protected abstract boolean drawShips();

    @Override
    @NonNull
    public List<ShipPresenter> getShips() {
        if (drawShips()) return super.getShips();
        else return new ArrayList<>();
    }
}
