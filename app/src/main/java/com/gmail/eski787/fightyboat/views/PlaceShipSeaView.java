package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.presenters.ShipPresenter;

// TODO: Drag-and-drop of ships from bottom bar

/**
 * Created by Erik on 12/30/2016.
 */

public class PlaceShipSeaView extends SeaView {
    private static final String TAG = PlaceShipSeaView.class.getSimpleName();
    private int cnt = 0;


    public PlaceShipSeaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlaceShipSeaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PlaceShipSeaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onGridTouchEvent(Point coordinate, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return false;
        }

        if (mSea == null) {
            Log.d(TAG, "Sea is null");
            return false;
        }

        // TODO: Selection of ship highlights, brings up option menu.
        // TODO: Option menu allows for rotation and deletion of ship.
        for (ShipPresenter ship : mSea.getShips()) {
            if (ship.contains(coordinate)) {
                Log.d(TAG, "TouchEvent inside Ship.");
            }
        }


//        regenerateSeaTiles();
        invalidate();
        return true;
    }
}
