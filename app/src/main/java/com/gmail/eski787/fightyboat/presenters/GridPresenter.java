package com.gmail.eski787.fightyboat.presenters;

import android.graphics.PointF;

/**
 * Created by Erik on 1/15/2017.
 */
public abstract class GridPresenter implements Presenter {
//    @Nullable
//    private GridView mGridView;

    /**
     * Called when the user has clicked on a grid coordinate.
     *
     * @param coordinate The grid coordinate that the event was called on.
     * @return true if the display has changed, false otherwise.
     */
    public abstract boolean onClick(PointF coordinate);

    /**
     * Called when the user has long clicked on a grid coordinate.
     *
     * @param coordinate The grid coordinate that the event was called on.
     * @return true is the display has changed, false otherwise.
     */
    public abstract boolean onLongClick(PointF coordinate);

//    public void setGridView(GridView gridView) {
//        mGridView = gridView;
//    }
}
