package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;
import com.gmail.eski787.fightyboat.presenters.AppColors;
import com.gmail.eski787.fightyboat.presenters.SeaPresenter;
import com.gmail.eski787.fightyboat.presenters.ShipCap;

import java.util.EnumMap;

/**
 * Base class for displaying a {@link Sea}.
 * Created by Erik on 12/23/2016.
 */

public class SeaView<T extends SeaPresenter> extends GridView.SquareView {
    public static final float SHIP_RADIUS = 0.47f;
    static final float PEG_RADIUS = 0.3f;
    private static final String TAG = SeaView.class.getSimpleName();
    private final EnumMap<AppColors, Integer> mPaintMap = new EnumMap<>(AppColors.class);
    @Nullable
    protected T mPresenter;
    /**
     * Preallocate instance to be modified during drawing.
     */
    protected Paint mPaint = new Paint();
    private ClickListener mClickListener;


    public SeaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaintMap();
    }

    public SeaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializePaintMap();
    }

    public SeaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializePaintMap();
    }

    /**
     * Draws a Ship on the View.
     *
     * @param ship   The ship to draw.
     * @param canvas The canvas to draw on.
     */
    protected void drawShip(SeaPresenter.ShipPresenter ship, Canvas canvas) {
        final Paint paint = new Paint();
        paint.setColor(mPaintMap.get(ship.getColor()));

        // Setup math
        final int height = getTileHeight();
        final int width = getTileWidth();
        final int length = ship.getLength();
        final Ship.Orientation orientation = ship.getOrientation();
        final int x = ship.getOrigin().x;
        final int y = ship.getOrigin().y;
        final int startX = x * width;
        final int startY = y * height;
        final int midX = startX + (width / 2);
        final int midY = startY + (height / 2);
        // The following declarations should be treated as final after the switch.
        int endX, endY;
        float minX, maxX, minY, maxY;
        switch (orientation) {
            case HORIZONTAL:
                endX = (x + length) * width;
                endY = startY + height;
                maxX = endX - width;
                minX = startX + width;
                maxY = midY + (width * SHIP_RADIUS);
                minY = midY - (width * SHIP_RADIUS);
                break;
            case VERTICAL:
                endX = startX + width;
                endY = (y + length) * height;
                maxX = midX + (width * SHIP_RADIUS);
                minX = midX - (width * SHIP_RADIUS);
                maxY = endY - height;
                minY = startY + height;
                break;
            default:
                throw new RuntimeException(String.format("Orientation is an invalid state. %s", orientation));
        }

        // Start
        ShipCap.getCap(ship.getStartCap(), orientation.start)
                .drawCap(canvas, paint, startX, startY, startX + width, startY + height);

        // Middle
        canvas.drawRect(minX, minY, maxX, maxY, paint);

        // End
        ShipCap.getCap(ship.getEndCap(), orientation.end)
                .drawCap(canvas, paint, endX - width, endY - height, endX, endY);
    }

    @Override
    protected Point getGridSize() {
        if (mPresenter == null) {
            if (isInEditMode()) {
                // Check if View is being rendered in a static developer tool.
                return new Point(10, 10);
            }
            throw new RuntimeException("SeaView has no size without Sea attached.");
        } else {
            return mPresenter.getSize();
        }
    }

    protected void initializePaintMap() {
        Context cont = getContext();
        mPaintMap.put(AppColors.GHOST_SHIP, ContextCompat.getColor(cont, R.color.ghostShip));
        mPaintMap.put(AppColors.STEEL_GRAY, ContextCompat.getColor(cont, R.color.steelGray));
        mPaintMap.put(AppColors.PEG_NONE, ContextCompat.getColor(cont, R.color.tileNone));
        mPaintMap.put(AppColors.PEG_HIT, ContextCompat.getColor(cont, R.color.tileHit));
        mPaintMap.put(AppColors.PEG_MISS, ContextCompat.getColor(cont, R.color.tileMiss));
    }

    /**
     * Redraws the Sea onto the View.
     *
     * @param canvas Canvas to draw on.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final boolean EDIT = isInEditMode();
        if (mPresenter == null || EDIT) {
            Log.d(TAG, "mPresenter is null.");
            return;
        }

        // Ships
        for (SeaPresenter.ShipPresenter ship : mPresenter.getShips()) {
            drawShip(ship, canvas);
        }

        // Pegs
        final int height = getTileHeight();
        final int width = getTileWidth();
        Point oceanSize = mPresenter.getSize();
        for (int x = 0; x < oceanSize.x; x++) {
            for (int y = 0; y < oceanSize.y; y++) {
                final Sea.SeaStatus seaStatus = mPresenter.getStatus(x, y);
                if (seaStatus == Sea.SeaStatus.PEG_NONE) {
                    continue;
                }

                final int startX = x * width;
                final int startY = y * height;
                final int midX = startX + (width / 2);
                final int midY = startY + (height / 2);
                final int minDimension = Math.min(width, height);
                final float radius = minDimension * PEG_RADIUS;
                mPaint.setColor(mPaintMap.get(seaStatus.getColor()));
                canvas.drawCircle(midX, midY, radius, mPaint);
            }
        }
    }

    public void setPresenter(@Nullable T presenter) {
        // Unset previous presenter, and set new one.
//        if (mPresenter != null) {
//            mPresenter.setGridView(null);
//        }
        mPresenter = presenter;
//        if (mPresenter != null) {
//            mPresenter.setGridView(this);
//        }
        invalidate();
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;

        setOnClickListener(mClickListener);
        setOnLongClickListener(mClickListener);
        setOnDragListener(mClickListener);
    }

    public class ClickListener implements OnClickListener, OnLongClickListener, OnDragListener {
        @Override
        public void onClick(View v) {
            assert mPresenter != null;
            final boolean handled = mPresenter.onClick(
                    getCoordinate(mTouchEvent.getX(), mTouchEvent.getY()));
            if (handled) {
                invalidate();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            assert mPresenter != null;
            final boolean handled = mPresenter.onLongClick(
                    getCoordinate(mTouchEvent.getX(), mTouchEvent.getY()));
            return handled;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            return false;
        }
    }
}
