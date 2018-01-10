package com.gmail.eski787.fightyboat.game.state;

import android.graphics.PointF;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;

/**
 * Created by Erik on 1/9/2018.
 */

public abstract class GameAction {
    public static final int ACTION_UNLOCK = 0;
    public static final int ACTION_SELECT = 1;
    public static final int ACTION_FIRE = 2;
    public static final int ACTION_CONTINUE = 3;
    private final @ActionId
    int mActionId;
    private final PointF mCoordinate;
    private final @IdRes
    int mViewId;

    GameAction(@ActionId int actionId, PointF coordinate, @IdRes int viewId) {
        mActionId = actionId;
        mCoordinate = coordinate;
        mViewId = viewId;
    }

    public @ActionId
    int getActionId() {
        return mActionId;
    }

    public PointF getCoordinate() {
        return new PointF(mCoordinate.x, mCoordinate.y);
    }

    public @IdRes
    int getViewId() {
        return mViewId;
    }

    @IntDef({ACTION_UNLOCK, ACTION_SELECT, ACTION_FIRE, ACTION_CONTINUE})
    public @interface ActionId {
    }
}
