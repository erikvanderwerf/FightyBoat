package com.gmail.eski787.fightyboat.views;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.gmail.eski787.fightyboat.game.Ship;
import com.gmail.eski787.fightyboat.presenters.PlaceShipSeaPresenter;
import com.gmail.eski787.fightyboat.presenters.SeaPresenter;

// TODO: Drag-and-drop of ships from bottom bar

/**
 * This View allows the user to place, move, and rotate ships on their personal Sea. Ships are
 * dragged up onto this view and released where the user wants to place them.
 */
public class PlaceShipSeaView extends SeaView<PlaceShipSeaPresenter> {
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

    public class PlaceShipClickListener extends ClickListener {
        @Override
        public boolean onLongClick(View v) {
            assert mPresenter != null;

            boolean handled = super.onLongClick(v);
            if (handled) {
                SeaPresenter.ShipPresenter ghostShip = new SeaPresenter.ShipPresenter(mPresenter.getGhostShip());
                ghostShip.getOrigin().set(0, 0);

                // If handled, begin a drag event.
                ClipData clipData = null;
                DragShadowBuilder shadowBuilder = new ShipDragShadow(ghostShip);
                Object localState = null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startDragAndDrop(clipData, shadowBuilder, localState, 0);
                } else {
                    startDrag(clipData, shadowBuilder, localState, 0);
                }
            }
            return handled;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            assert mPresenter != null;
            super.onDrag(v, event);

//            Log.d(TAG, String.format("onDrag: %d", event.getAction()));

            boolean handled = false;
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    handled = true;
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    handled = false;
                    break;
                case DragEvent.ACTION_DROP:
                    // Case when user releases ship over SeaView.
                    ClipData clipData = event.getClipData();
                    if (clipData != null) {
                        Ship.ShipType type = Ship.ShipType.valueOf(
                                clipData.getItemAt(0).getText().toString());
                        mPresenter.setGhostShip(type);
                    }
                    PointF coordinate = getCoordinate(event.getX(), event.getY());
                    handled = mPresenter.onDropShip(coordinate);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Case when user releases ship anywhere. getResult is false if the ACTION_DROP
                    // was not handled elsewhere. Return ship to where it was originally placed.
                    if (!event.getResult())
                        handled = mPresenter.onDropShip(null);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    handled = true;
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    mPresenter.clearGhostShip();
                default:
                    Log.e(TAG, String.format("Unexpected onDrag action: %d.", event.getAction()));
            }

            if (handled) {
                invalidate();
            }

            return handled;
        }
    }

    /**
     * Provides the drag shadow for when a user holds and drags a ship around.
     */
    public final class ShipDragShadow extends DragShadowBuilder {
        private final SeaPresenter.ShipPresenter ship;

        public ShipDragShadow(SeaPresenter.ShipPresenter ship) {
            this.ship = ship;
        }

        @Override
        public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
            outShadowSize.x = getTileWidth();
            outShadowSize.y = getTileHeight();
            switch (ship.getOrientation()) {
                case HORIZONTAL:
                    outShadowSize.x *= ship.getLength();
                    break;
                case VERTICAL:
                    outShadowSize.y *= ship.getLength();
                    break;
            }
            outShadowTouchPoint.set(getTileWidth() / 2, getTileHeight() / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            drawShip(ship, canvas);
        }
    }
}
