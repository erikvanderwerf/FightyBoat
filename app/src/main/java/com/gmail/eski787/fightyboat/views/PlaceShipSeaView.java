package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.game.Sea;

// TODO: Drag-and-drop of ships from bottom bar

/**
 * Created by Erik on 12/30/2016.
 */

public class PlaceShipSeaView extends SeaView {
    private static final String TAG = PlaceShipSeaView.class.getSimpleName();

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
    public boolean onTouchEvent(MotionEvent event) {
        // TODO: Selection of ship highlights, brings up option menu.
        // TODO: Option menu allows for rotation and deletion of ship.
        boolean b = super.onTouchEvent(event);

        if (mSea != null) {
            float x = event.getX();
            float y = event.getY();

            int tx = ((int) x) / getTileWidth();
            int ty = ((int) y) / getTileHeight();

            Sea.Status current = mSea.getStatus(tx, ty);
            Sea.Status advance = null;
            switch (current) {
                case NONE:
                    advance = Sea.Status.HIT;
                    break;
                case HIT:
                    advance = Sea.Status.MISS;
                    break;
                case MISS:
                    advance = Sea.Status.NONE;
                    break;
            }

            mSea.set(tx, ty, advance);
        } else {
            Log.d(TAG, "Sea is null");
        }
        regenerateSeaTiles();
        invalidate();
        return b;
    }
}
