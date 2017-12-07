package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Special implementation of View to handle grids.
 */

public abstract class GridView extends View {
    private static final String TAG = GridView.class.getCanonicalName();

    /**
     * View constructor.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public GridView(Context context) {
        super(context);
    }

    /**
     * View constructor.
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * View constructor.
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * View constructor.
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public GridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Gets the number of rows (y) and number of columns (x) in the grid display
     * @return Rows -> y, Columns -> x
     */
    protected abstract Point getGridSize();

    /**
     * This callback is run when the user generates a touch event. The coordinates of the touch event
     * are converted into grid coordinates and the onGridTouchEvent method is called.
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        final int x = ((int) event.getX());
        final int y = ((int) event.getY());

        final int tx = x / getTileWidth();
        final int ty = y / getTileHeight();
        final Point coordinate = new Point(tx, ty);

        boolean handled = onGridTouchEvent(coordinate, event);
        if (handled) {
            invalidate();
        }
        return handled;
    }

    /**
     * Called when the user generates a touch event.
     *
     * @param coordinate The grid coordinate of the touch event.
     * @param event      The original touch event.
     * @return True if the event was handled, false otherwise.
     */
    protected abstract boolean onGridTouchEvent(Point coordinate, MotionEvent event);

    protected final int getTileWidth() {
        return getWidth() / getGridSize().x;
    }

    protected final int getTileHeight() {
        return getHeight() / getGridSize().y;
    }

    /**
     * A special {@link GridView} that automatically sizes itself to create a grid of squares.
     */
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

        /**
         * Sets the measured dimensions of the View to create square grid tiles out of the available
         * space.
         * @param widthMeasureSpec horizontal space requirements as imposed by the parent.
         *                         The requirements are encoded with
         *                         {@link android.view.View.MeasureSpec}.
         * @param heightMeasureSpec vertical space requirements as imposed by the parent.
         *                         The requirements are encoded with
         *                         {@link android.view.View.MeasureSpec}.
         */
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            Point size = getGridSize();

            int dx = height / size.x;
            int dy = width / size.y;

            int min = Math.min(dx, dy);
            setMeasuredDimension(min * size.x, min * size.y);
        }
    }
}
