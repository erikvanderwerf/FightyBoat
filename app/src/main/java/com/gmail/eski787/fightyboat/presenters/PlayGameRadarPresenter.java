package com.gmail.eski787.fightyboat.presenters;

import android.graphics.PointF;

import com.gmail.eski787.fightyboat.game.Sea;

/**
 * Presents a {@link com.gmail.eski787.fightyboat.game.Player Player}'s opponent's {@link Sea} to
 * be fired upon by the current Player.
 */

public class PlayGameRadarPresenter extends RadarPresenter {
    private static final String TAG = PlayGameRadarPresenter.class.getSimpleName();

    public PlayGameRadarPresenter(Sea sea) {
        super(sea);
    }

    @Override
    public boolean onClick(PointF coordinate) {


        return true;
    }

    @Override
    public boolean onLongClick(PointF coordinate) {
        return false;
    }

    @Override
    protected boolean drawShips() {
        return true;
    }
}
