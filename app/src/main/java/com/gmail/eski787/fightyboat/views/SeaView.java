package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Erik on 12/23/2016.
 * Methods for displaying a Sea object.
 */

public class SeaView extends View {
    static final float SHIP_RADIUS = 0.47f;
    static final float PEG_RADIUS = 0.3f;
    private static final String TAG = SeaView.class.getSimpleName();
    private final EnumMap<Sea.Status, Paint> mPaintMap = new EnumMap<>(Sea.Status.class);
    @Nullable
    private Sea mSea;
    private int mScreenHeight;
    private int mScreenWidth;
    private SeaTile[][] mTiles;

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

    private void initializePaintMap() {
        Paint none = new Paint(), hit = new Paint(), miss = new Paint();

        none.setColor(ResourcesCompat.getColor(getResources(), R.color.tileNone, null));
        hit.setColor(ResourcesCompat.getColor(getResources(), R.color.tileHit, null));
        miss.setColor(ResourcesCompat.getColor(getResources(), R.color.tileMiss, null));

        mPaintMap.put(Sea.Status.NONE, none);
        mPaintMap.put(Sea.Status.HIT, hit);
        mPaintMap.put(Sea.Status.MISS, miss);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //final int size = Math.min(w, h);
        mScreenHeight = h; //size;
        mScreenWidth = w; //size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final boolean EDIT = isInEditMode();

        if (mSea == null || EDIT) {
            Log.d(TAG, "mSea is null.");
            return;
        }

        final int height = getTileHeight();
        final int width = getTileWidth();

        // For each tile
        for (int row = 0; row < mTiles.length; row++) {
            for (int col = 0; col < mTiles[row].length; col++) {
                int startX = col * width;
                int startY = row * height;
                int endX = startX + width;
                int endY = startY + height;

                mTiles[row][col].draw(canvas, mPaintMap, startX, startY, endX, endY);
            }
        }
    }

    private int getTileWidth() {
        if (mSea != null) {
            return mScreenWidth / mSea.getNumberOfColumns();
        } else {
            return 0;
        }
    }

    private int getTileHeight() {
        if (mSea != null) {
            return mScreenHeight / mSea.getNumberOfRows();
        } else {
            return 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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

    public void setSea(Sea sea) {
        mSea = sea;
        regenerateSeaTiles();
        invalidate();
    }

    private void regenerateSeaTiles() {
        assert mSea != null;

        int rows = mSea.getNumberOfRows();
        int cols = mSea.getNumberOfColumns();

        mTiles = new SeaTile[rows][cols];
        // Assignment and Status
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                SeaTile tile = new SeaTile();
                tile.status = mSea.getStatus(x, y);
                mTiles[y][x] = tile;
            }
        }
        // Ships
        for (Ship ship : mSea.getShips()) {
            int length = ship.getLength();
            int x = ship.getOrigin().x;
            int y = ship.getOrigin().y;
            Ship.Orientation orientation = ship.getOrientation();
            for (int i = 0; i < length; i++) {
                SeaTile tile = mTiles[y][x];

                tile.status = mSea.getStatus(x, y);
                tile.orientation = orientation;
                ShipCap.CapDirection direction;
                if (i == 0) {
                    direction = orientation == Ship.Orientation.HORIZONTAL ? ShipCap.CapDirection.RIGHT : ShipCap.CapDirection.DOWN;
                    tile.cap = ShipCap.getCap(ship.getStartCap(), direction);
                } else if (i == (length - 1)) {
                    direction = orientation == Ship.Orientation.HORIZONTAL ? ShipCap.CapDirection.LEFT : ShipCap.CapDirection.UP;
                    tile.cap = ShipCap.getCap(ship.getEndCap(), direction);
                }

                // Increment x or y
                if (orientation == Ship.Orientation.HORIZONTAL) {
                    x++;
                } else {
                    y++;
                }
            }
        }
    }



    private class SeaTile {
        Sea.Status status;
        @Nullable
        ShipCap cap;
        @Nullable
        Ship.Orientation orientation;

        void draw(Canvas canvas, Map<Sea.Status, Paint> paintMap,
                  int startX, int startY, int endX, int endY) {
            final int width = endX - startX;
            final int height = endY - startY;

            // Ship
            Paint paint = new Paint();
            paint.setColor(ResourcesCompat.getColor(getResources(), R.color.steelGray, null));
            if (cap != null) {
                cap.drawCap(canvas, paint, startX, startY, endX, endY);
            } else if (orientation != null) {
                drawHull(canvas, paint, startX, startY, endX, endY);
            }

            // Peg
            if (status != Sea.Status.NONE) {
                final Paint paint_peg = paintMap.get(status);
                final int midX = startX + (width / 2);
                final int midY = startY + (height / 2);
                final int minDimension = Math.min(width, height);
                final float radius = minDimension * PEG_RADIUS;

                canvas.drawCircle(midX, midY, radius, paint_peg);
            }
        }

        private void drawHull(Canvas canvas, Paint paint, int startX, int startY, int endX, int endY) {
            assert orientation != null;

            final int width = endX - startX;
            final int height = endY - startY;

            final int midX = startX + (width / 2);
            final int midY = startY + (height / 2);

            float minX = 0, maxX = 0, minY = 0, maxY = 0;

            switch (orientation) {
                case VERTICAL:
                    maxX = midX + (width * SHIP_RADIUS);
                    minX = midX - (width * SHIP_RADIUS);
                    maxY = endY;
                    minY = startY;
                    break;
                case HORIZONTAL:
                    maxX = endX;
                    minX = startX;
                    maxY = midY + (width * SHIP_RADIUS);
                    minY = midY - (width * SHIP_RADIUS);
                    break;
            }
            canvas.drawRect(minX, minY, maxX, maxY, paint);
        }
    }
}
