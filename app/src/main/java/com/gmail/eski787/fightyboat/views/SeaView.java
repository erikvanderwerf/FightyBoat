package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;
import com.gmail.eski787.fightyboat.presenters.ShipCap;

import java.util.EnumMap;
import java.util.Map;

/**
 * Base class for displaying a {@link Sea}.
 * Created by Erik on 12/23/2016.
 */

public class SeaView extends GridView {
    public static final float SHIP_RADIUS = 0.47f;
    static final float PEG_RADIUS = 0.3f;
    private static final String TAG = SeaView.class.getSimpleName();
    private final EnumMap<Sea.SeaStatus, Paint> mPaintMap = new EnumMap<>(Sea.SeaStatus.class);
    @Nullable
    protected Sea mSea;
    protected SeaTile[][] mTiles;

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
        return new Point(10, 10);
    }

    @Override
    protected boolean isSquare() {
        return true;
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

    public void setSea(Sea sea) {
        mSea = sea;
        regenerateSeaTiles();
        invalidate();
    }

    protected void regenerateSeaTiles() {
        assert mSea != null;

        int rows = mSea.getNumberOfRows();
        int cols = mSea.getNumberOfColumns();

        mTiles = new SeaTile[rows][cols];
        // Assignment and SeaStatus
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                SeaTile tile = new SeaTile();
                tile.seaStatus = mSea.getStatus(x, y);
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

                tile.seaStatus = mSea.getStatus(x, y);
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
        Sea.SeaStatus seaStatus;
        @Nullable
        ShipCap cap;
        @Nullable
        Ship.Orientation orientation;

        void draw(Canvas canvas, Map<Sea.SeaStatus, Paint> paintMap,
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
            if (seaStatus != Sea.SeaStatus.NONE) {
                final Paint paint_peg = paintMap.get(seaStatus);
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
