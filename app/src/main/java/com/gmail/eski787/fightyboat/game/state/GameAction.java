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
    private final int mTargetPlayer;

    GameAction(@ActionId int actionId, int targetPlayer) {
        mActionId = actionId;
        mTargetPlayer = targetPlayer;
    }

    @ActionId
    public int getActionId() {
        return mActionId;
    }

    public int getTargetPlayer() {
        return mTargetPlayer;
    }

    @IntDef({ACTION_UNLOCK, ACTION_SELECT, ACTION_PLAY_BUTTON})
    public @interface ActionId {
    }

    public abstract static class CoordinateGameAction extends GameAction {
        private final PointF mCoordinate;

        CoordinateGameAction(@ActionId int actionId, int targetPlayer, PointF coordinate) {
            super(actionId, targetPlayer);
            mCoordinate = coordinate;
        }

        public PointF getCoordinate() {
            return new PointF(mCoordinate.x, mCoordinate.y);
        }
    }

    public static class UnlockAction extends GameAction {
        public UnlockAction(int targetPlayer) {
            super(ACTION_UNLOCK, targetPlayer);
        }
    }

    public static class SelectAction extends CoordinateGameAction {
        public SelectAction(int targetPlayer, PointF coordinate) {
            super(ACTION_SELECT, targetPlayer, coordinate);
        }
    }

    public static class PlayButtonAction extends GameAction {
        public PlayButtonAction(int targetPlayer) {
            super(ACTION_PLAY_BUTTON, targetPlayer);
        }
    }
}
