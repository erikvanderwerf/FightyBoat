package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;
import com.gmail.eski787.fightyboat.presenters.SeaPresenter;
import com.gmail.eski787.fightyboat.presenters.ShipCap;
import com.gmail.eski787.fightyboat.presenters.ShipPresenter;

import java.util.EnumMap;

/**
 * Base class for displaying a {@link Sea}.
 * Created by Erik on 12/23/2016.
 */

public class SeaView extends GridView.SquareView {
    public static final float SHIP_RADIUS = 0.47f;
    static final float PEG_RADIUS = 0.3f;
    private static final String TAG = SeaView.class.getSimpleName();
    // TODO move mPaintMap to SeaPresenter.
    private final EnumMap<Sea.SeaStatus, Paint> mPaintMap = new EnumMap<>(Sea.SeaStatus.class);
    @Nullable
    protected SeaPresenter mSeaPresenter;
    private ClickListener mClickListener;


    public SeaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializePaintMap();
    }

    public SeaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializePaintMap();
    }

    public SeaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializePaintMap();
    }

    @Override
    protected Point getGridSize() {
        if (mSeaPresenter == null) {
            throw new RuntimeException("SeaView has no size without Sea attached.");
        }
        return mSeaPresenter.getSize();
    }

    @Override
    protected boolean onClick(MotionEvent event) {
        return mSeaPresenter != null && mSeaPresenter.onClick(
                getCoordinate((int) event.getX(), (int) event.getY()), event);
    }

    protected void initializePaintMap() {
        Paint none = new Paint(), hit = new Paint(), miss = new Paint();

        none.setColor(ResourcesCompat.getColor(getResources(), R.color.tileNone, null));
        hit.setColor(ResourcesCompat.getColor(getResources(), R.color.tileHit, null));
        miss.setColor(ResourcesCompat.getColor(getResources(), R.color.tileMiss, null));

        mPaintMap.put(Sea.SeaStatus.NONE, none);
        mPaintMap.put(Sea.SeaStatus.HIT, hit);
        mPaintMap.put(Sea.SeaStatus.MISS, miss);
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
        if (mSeaPresenter == null || EDIT) {
            Log.d(TAG, "mSeaPresenter is null.");
            return;
        }

        // Ships
        for (ShipPresenter ship : mSeaPresenter.getShips()) {
            drawShip(ship, canvas);
        }

        // Pegs
        final int height = getTileHeight();
        final int width = getTileWidth();
        Point oceanSize = mSeaPresenter.getSize();
        for (int x = 0; x < oceanSize.x; x++) {
            for (int y = 0; y < oceanSize.y; y++) {
                final Sea.SeaStatus seaStatus = mSeaPresenter.getStatus(x, y);
                if (seaStatus == Sea.SeaStatus.NONE) {
                    continue;
                }

                final int startX = x * width;
                final int startY = y * height;
                final int midX = startX + (width / 2);
                final int midY = startY + (height / 2);
                final int minDimension = Math.min(width, height);
                final float radius = minDimension * PEG_RADIUS;
                final Paint paint_peg = mPaintMap.get(seaStatus);
                canvas.drawCircle(midX, midY, radius, paint_peg);
            }
        }
    }

    /**
     * Draws a Ship on the View.
     *
     * @param ship   The ship to draw.
     * @param canvas The canvas to draw on.
     */
    private void drawShip(ShipPresenter ship, Canvas canvas) {
        final Paint paint = new Paint();
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.steelGray, null));

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
        // The following declarations should be treated as final.
        int endX = 0, endY = 0;
        float minX = 0, maxX = 0, minY = 0, maxY = 0;
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
        }

        // Start
        ShipCap.getCap(ship.getStartCap(), orientation.shipStartCap())
                .drawCap(canvas, paint, startX, startY, startX + width, startY + height);

        // Middle
        canvas.drawRect(minX, minY, maxX, maxY, paint);

        // End
        ShipCap.getCap(ship.getEndCap(), orientation.shipEndCap())
                .drawCap(canvas, paint, endX - width, endY - height, endX, endY);
    }

    public void setSeaPresenter(SeaPresenter seaPresenter) {
        mSeaPresenter = seaPresenter;
        mClickListener = new ClickListener();
        setOnClickListener(mClickListener);
        setOnLongClickListener(mClickListener);
        setOnGenericMotionListener(mClickListener);
        invalidate();
    }

    public void onDetachSeaPresenter() {
        mSeaPresenter = null;
        setOnClickListener(null);
        setOnLongClickListener(null);
        setOnGenericMotionListener(null);
    }

    private class ClickListener implements OnClickListener, OnLongClickListener, OnGenericMotionListener {

        @Override
        public void onClick(View v) {

            mSeaPresenter.onClick()
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public boolean onGenericMotion(View v, MotionEvent event) {
            return false;
        }
    }
}
