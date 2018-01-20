package com.gmail.eski787.fightyboat.presenters;

import android.graphics.PointF;
import android.support.annotation.NonNull;

import com.gmail.eski787.fightyboat.game.Sea;

/**
 * Created by Erik on 12/24/2017.
 */

public class PlayGameSeaPresenter extends SeaPresenter {
    public PlayGameSeaPresenter(@NonNull Sea sea) {
        super(sea);
    }

    @Override
    public boolean onClick(PointF coordinate) {
        return false;
    }

    @Override
    public boolean onLongClick(PointF coordinate) {
        return false;
    }
}
