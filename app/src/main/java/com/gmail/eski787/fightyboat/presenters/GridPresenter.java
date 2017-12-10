package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;

/**
 * Created by Erik on 1/15/2017.
 */
public interface GridPresenter extends Presenter {
    /**
     * Called when the user has clicked on a grid coordinate.
     *
     * @param coordinate The grid coordinate that the event was called on.
     * @return true if the display has changed, false otherwise.
     */
    boolean onClick(Point coordinate);

    /**
     * Called when the user has long clicked on a grid coordinate.
     *
     * @param coordinate The grid coordinate that the event was called on.
     * @return true is the display has changed, false otherwise.
     */
    boolean onLongClick(Point coordinate);
}
