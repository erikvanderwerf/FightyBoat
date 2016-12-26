package com.gmail.eski787.fightyboat.view;

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

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;

/**
 * Created by Erik on 12/23/2016.
 */

public class SeaView extends View {
    private static final String TAG = SeaView.class.getSimpleName();
    @Nullable private Sea mSea;
    private int mScreenHeight;
    private int mScreenWidth;
    private final EnumMap<Sea.Status, Paint> mPaintMap = new EnumMap<Sea.Status, Paint>(Sea.Status.class);
    //private int mTileHeight;
    //private int mTileWidth;

    private void initializePaintMap() {
        Paint none = new Paint(), hit = new Paint(), miss = new Paint();

        none.setColor(ResourcesCompat.getColor(getResources(), R.color.tileNone, null));
        hit.setColor(ResourcesCompat.getColor(getResources(), R.color.tileHit, null));
        miss.setColor(ResourcesCompat.getColor(getResources(), R.color.tileMiss, null));

        mPaintMap.put(Sea.Status.NONE, none);
        mPaintMap.put(Sea.Status.HIT, hit);
        mPaintMap.put(Sea.Status.MISS, miss);
    }

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int height = getTileHeight();
        final int width = getTileWidth();

        if (mSea == null) {
            Log.d(TAG, "mSea is null.");
            return;
        }

        for (int row = 0; row < mSea.getNumberOfRows(); row++) {
            for (int col = 0; col < mSea.getNumberOfColumns(); col++) {
                int startX = col * width;
                int startY = row * height;

                Paint paint = mPaintMap.get(mSea.getStatus(col, row));

                drawTile(canvas, startX, startY, width, height, paint);
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
                case NONE: advance = Sea.Status.HIT; break;
                case HIT: advance = Sea.Status.MISS; break;
                case MISS: advance = Sea.Status.NONE; break;
            }

            mSea.set(tx, ty, advance);
        } else {
            Log.d(TAG, "Sea is null");
        }
        invalidate();
        return b;
    }

    public void setSea(Sea sea) {
        mSea = sea;
        invalidate();
    }

    private static void drawTile(Canvas canvas, int startX, int startY, int width, int height, Paint paint) {
        int minDimension = Math.min(width, height);

        int midX = startX + (width / 2);
        int midY = startY + (height / 2);

        canvas.drawCircle(midX, midY, minDimension / 3, paint);
    }
}
