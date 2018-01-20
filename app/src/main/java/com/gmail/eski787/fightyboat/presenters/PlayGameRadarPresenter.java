package com.gmail.eski787.fightyboat.presenters;

import android.graphics.PointF;

import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.state.GameAction;

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
        GameAction action = new GameAction.SelectAction(coordinate);

        return true;
    }

    @Override
    public boolean onLongClick(PointF coordinate) {
        return false;
    }

    public void onPlayButtonClick() {
        GameAction action = new GameAction.PlayButtonAction();

    }

    @Override
    protected boolean drawShips() {
        return true;
    }
}
