package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Locale;

/**
 * Special implementation of View to handle grids.
 */

public abstract class GridView extends View {
    private static final String TAG = GridView.class.getSimpleName();
    protected CustomEvent mTouchEvent = null;

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
     * are converted into grid coordinates. Depending on how the user is interacting, the onClick
     * or onLongClick methods are called.
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchEvent = new CustomEvent(event);
        return super.onTouchEvent(event);
    }

    protected final PointF getCoordinate(float x, float y) {
        final float tx = x / getTileWidth();
        final float ty = y / getTileHeight();
        return new PointF(tx, ty);
    }

    protected final int getTileWidth() {
        return getWidth() / getGridSize().x;
    }

    protected final int getTileHeight() {
        return getHeight() / getGridSize().y;
    }

    public static class CustomEvent {
        private int x, y;

        CustomEvent(MotionEvent event) {
            x = (int) event.getX();
            y = (int) event.getY();
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "%s: %d %d", CustomEvent.class.getSimpleName(), x, y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
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
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);

            Log.d(TAG, String.format("onMeasure Height Mode: %d\tSize: %d Width Mode: %d\t Size: %d",
                    heightMode, heightSize, widthMode, widthSize));

            int height = Integer.MAX_VALUE;
            int width = Integer.MAX_VALUE;

            // There's a few different cases here.
            if (heightMode == MeasureSpec.EXACTLY &&
                    widthMode == MeasureSpec.EXACTLY) {
                Log.e(TAG, "GridView is overconstrained. Only one of X or Y may be set " +
                        "exactly. Defaulting to minimum of two.");
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            }
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            }
            if (heightMode != MeasureSpec.EXACTLY &&
                    widthMode != MeasureSpec.EXACTLY) {
                throw new RuntimeException("GridView is underconstrained. X or Y must be set exactly.");
            }

            Point size = getGridSize();
            int dx = width / size.x;
            int dy = height / size.y;

            int edge_max = Math.min(dx, dy);
            setMeasuredDimension(edge_max * size.x, edge_max * size.y);
        }
    }
}
