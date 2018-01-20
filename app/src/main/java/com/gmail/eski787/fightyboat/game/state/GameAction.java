package com.gmail.eski787.fightyboat.game.state;

import android.graphics.PointF;
import android.support.annotation.IntDef;

/**
 * Created by Erik on 1/9/2018.
 */

public abstract class GameAction {
    public static final int ACTION_UNLOCK = 0;
    public static final int ACTION_SELECT = 1;
    public static final int ACTION_PLAY_BUTTON = 2;

    @ActionId
    private final int mActionId;

    GameAction(@ActionId int actionId) {
        mActionId = actionId;
    }

    @ActionId
    public int getActionId() {
        return mActionId;
    }

    public int getPlayer() {
        // TODO This is hardcoded and will work with only one opponent. If other opponents
        // are added this needs to be changed.
        return 0;
    }

    @IntDef({ACTION_UNLOCK, ACTION_SELECT, ACTION_PLAY_BUTTON})
    public @interface ActionId {
    }

    public abstract static class CoordinateGameAction extends GameAction {
        private final PointF mCoordinate;

        CoordinateGameAction(@ActionId int actionId, PointF coordinate) {
            super(actionId);
            mCoordinate = coordinate;
        }

        public PointF getCoordinate() {
            return new PointF(mCoordinate.x, mCoordinate.y);
        }
    }

    public static class UnlockAction extends GameAction {
        public UnlockAction() {
            super(ACTION_UNLOCK);
        }
    }

    public static class SelectAction extends CoordinateGameAction {
        public SelectAction(PointF coordinate) {
            super(ACTION_SELECT, coordinate);
        }
    }

    public static class PlayButtonAction extends GameAction {
        public PlayButtonAction() {
            super(ACTION_PLAY_BUTTON);
        }
    }
}
