package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.game.Sea;

// TODO: Drag-and-drop of ships from bottom bar

/**
 * Created by Erik on 12/30/2016.
 */

public class PlaceShipSeaView extends SeaView {
    private int cnt = 0;

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
    public void onGridTouchEvent(Point coordinate, MotionEvent event) {
        // TODO: Selection of ship highlights, brings up option menu.
        // TODO: Option menu allows for rotation and deletion of ship.
        // TODO: Get rid of all of this

        if (event.getAction() != MotionEvent.ACTION_DOWN) {
            return;
        }

        if (mSea != null) {
            final int x = coordinate.x;
            final int y = coordinate.y;

            Sea.SeaStatus current = mSea.getStatus(x, y);
            Sea.SeaStatus advance = null;
            switch (current) {
                case NONE:
                    advance = Sea.SeaStatus.HIT;
                    break;
                case HIT:
                    advance = Sea.SeaStatus.MISS;
                    break;
                case MISS:
                    advance = Sea.SeaStatus.NONE;
                    break;
            }
            mSea.set(x, y, advance);
        } else {
            Log.d(TAG, "Sea is null");
        }
        regenerateSeaTiles();
        invalidate();
    }
}
