package com.gmail.eski787.fightyboat.game.state;

import android.graphics.PointF;
import android.support.annotation.IntDef;

/**
 * Created by Erik on 1/9/2018.
 */

public abstract class GameAction {
    public static final int ACTION_UNLOCK = 0;
    public static final int ACTION_SELECT = 1;
    public static final int ACTION_FIRE = 2;
    public static final int ACTION_CONTINUE = 3;

    @ActionId
    private final int mActionId;

    GameAction(@ActionId int actionId) {
        mActionId = actionId;
    }

    @ActionId
    public int getActionId() {
        return mActionId;
    }

    @IntDef({ACTION_UNLOCK, ACTION_SELECT, ACTION_FIRE, ACTION_CONTINUE})
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
        SelectAction(PointF coordinate) {
            super(ACTION_SELECT, coordinate);
        }
    }

    public static class FireAction extends GameAction {
        FireAction() {
            super(ACTION_FIRE);
        }
    }

    public static class ContinueAction extends GameAction {
        ContinueAction() {
            super(ACTION_CONTINUE);
        }
    }
}
