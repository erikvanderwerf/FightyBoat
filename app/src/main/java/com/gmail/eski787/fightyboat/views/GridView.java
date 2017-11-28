package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gmail.eski787.fightyboat.presenters.GridPresenter;

/**
 * Special implementation of View to handle grids.
 */

public abstract class GridView extends View {
    private static final String TAG = GridView.class.getCanonicalName();
    @Nullable
    protected GridPresenter mPresenter;


    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Gets the number of rows (y) and number of columns (x) in the grid display
     * @return Rows -> y, Columns -> x
     */
    protected abstract Point getGridSize();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        final int x = ((int) event.getX());
        final int y = ((int) event.getY());

        final int tx = x / getTileWidth();
        final int ty = y / getTileHeight();
        final Point coordinate = new Point(tx, ty);

        if (mPresenter != null) {
            onGridTouchEvent(coordinate, event);
        } else {
            Log.e(TAG, "No presenter attached.");
        }

        return true;
    }

    protected abstract void onGridTouchEvent(Point coordinate, MotionEvent event);

    protected final int getTileWidth() {
        return getWidth() / getGridSize().x;
    }

    protected final int getTileHeight() {
        return getHeight() / getGridSize().y;
    }

    public abstract static class SquareView extends GridView {

        public SquareView(Context context) {
            super(context);
        }

        public SquareView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SquareView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public SquareView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            int min = Math.min(height, width);
            setMeasuredDimension(min, min);
        }
    }
}
