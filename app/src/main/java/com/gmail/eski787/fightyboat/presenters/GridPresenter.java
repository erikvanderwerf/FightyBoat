package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Point;
import android.view.MotionEvent;

/**
 * Created by Erik on 1/15/2017.
 */
public interface GridPresenter {
    void onTouchEvent(Point coordinate, MotionEvent event);
}