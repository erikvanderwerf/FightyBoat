package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gmail.eski787.fightyboat.game.Sea;

import javax.annotation.Nullable;

/**
 * Presents a {@link com.gmail.eski787.fightyboat.game.Player Player}'s opponent's {@link Sea} to
 * be fired upon by the current Player.
 */

public class PlayGameRadarPresenter extends RadarPresenter {
    private static final String TAG = PlayGameRadarPresenter.class.getSimpleName();
    @NonNull
    private TURN_STATE mState = TURN_STATE.AWAITING_SELECTION;
    @Nullable
    private Point selected;
    @Nullable
    private PlayGameRadarPresenterInteraction mListener;

    private void changeState(TURN_STATE turnState) {
        mState = turnState;
        if (mListener == null) {
            Log.d(TAG, "changeState: Changing state without a listener.");
        } else {
            mListener.setButtonState(mState.button_state, mState.button_text);
        }
    }

    public Point getSelected() {
        return selected != null ? new Point(selected) : null;
    }

    @Override
    public boolean onClick(PointF coordinate) {
        if (mState == TURN_STATE.AWAITING_SELECTION || mState == TURN_STATE.AWAITING_FIRE) {
            Point tempSelection = intCoordinate(coordinate);

            Sea.SeaStatus status = mSea.getStatus(tempSelection);
            if (status == Sea.SeaStatus.PEG_NONE) {
                selected = tempSelection;
                changeState(TURN_STATE.AWAITING_FIRE);
            } else {
                selected = null;
                mListener.notifyInvalidSelection();
                changeState(TURN_STATE.AWAITING_SELECTION);
            }
        } else if (mState == TURN_STATE.AWAITING_CONTINUE) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onLongClick(PointF coordinate) {
        return false;
    }

    public void onPlayButtonClick() {
        if (mListener == null) {
            Log.e(TAG, "onPlayButtonClick: Button callback without listener.");
            return;
        }

        if (mState == TURN_STATE.AWAITING_SELECTION) {
            Log.d(TAG, "onPlayButtonClick: User pressed disabled button. How?");
        } else if (mState == TURN_STATE.AWAITING_FIRE) {
            // Pressed FIRE
            if (mSea == null) {
                throw new RuntimeException("Cannot fire if there is no Sea.");
            }

            Sea.SeaStatus newStatus = getShipAtCoordinate(selected) != null ?
                    Sea.SeaStatus.PEG_HIT : Sea.SeaStatus.PEG_MISS;
            mSea.setStatus(selected.x, selected.y, newStatus);
            selected = null;
            changeState(TURN_STATE.AWAITING_CONTINUE);
        } else if (mState == TURN_STATE.AWAITING_CONTINUE) {
            mListener.advancePlayer();
        }
    }

    public void setListener(PlayGameRadarPresenterInteraction listener) {
        mListener = listener;
        changeState(TURN_STATE.AWAITING_SELECTION);
        selected = null;
    }

    @Override
    protected boolean drawShips() {
        return true;
    }

    private enum TURN_STATE {
        AWAITING_SELECTION(false, "Fire"),
        AWAITING_FIRE(true, "Fire"),
        AWAITING_CONTINUE(true, "Continue");

        private final boolean button_state;
        private final String button_text;

        TURN_STATE(boolean button_state, String button_text) {
            this.button_state = button_state;
            this.button_text = button_text;
        }
    }


    public interface PlayGameRadarPresenterInteraction {
        void setButtonState(boolean enabled, String text);

        void advancePlayer();

        void notifyInvalidSelection();
    }
}
