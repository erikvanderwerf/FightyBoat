package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.util.Util;

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
        if (mState == Game.TurnState.AWAITING_SELECTION || mState == Game.TurnState.AWAITING_FIRE) {
            Point tempSelection = Util.intCoordinate(coordinate);

            Sea.SeaStatus status = mSea.getStatus(tempSelection);
            if (status == Sea.SeaStatus.PEG_NONE) {
                selected = tempSelection;
                changeState(Game.TurnState.AWAITING_FIRE);
            } else {
                selected = null;
                mListener.notifyInvalidSelection();
                changeState(Game.TurnState.AWAITING_SELECTION);
            }
        } else if (mState == Game.TurnState.AWAITING_CONTINUE) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onLongClick(PointF coordinate) {
        return false;
    }

    public void onPlayButtonClick() {
        if (mState == Game.TurnState.AWAITING_SELECTION) {
            Log.d(TAG, "onPlayButtonClick: User pressed disabled button. How?");
        } else if (mState == Game.TurnState.AWAITING_FIRE) {
            // Pressed FIRE
            if (mSea == null) {
                throw new RuntimeException("Cannot fire if there is no Sea.");
            }

            mSea.hit(selected);
            selected = null;
            changeState(Game.TurnState.AWAITING_CONTINUE);
        } else if (mState == Game.TurnState.AWAITING_CONTINUE) {
            mListener.advancePlayer();
        }
    }

    @Override
    protected boolean drawShips() {
        return true;
    }
}
