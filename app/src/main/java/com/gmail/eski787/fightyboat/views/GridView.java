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
 * Created by Erik on 1/15/2017.
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

    protected abstract Point getGridSize();

    protected abstract boolean isSquare();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isSquare()) {
            int height = getMeasuredHeight();
            int width = getMeasuredWidth();
            int min = Math.min(height, width);
            setMeasuredDimension(min, min);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        final int x = ((int) event.getX());
        final int y = ((int) event.getY());

        final int tx = x / getTileWidth();
        final int ty = y / getTileHeight();
        final Point coordinate = new Point(x, y);

        if (mPresenter != null) {
            mPresenter.onTouchEvent(coordinate, event);
        } else {
            Log.e(TAG, "No presenter attached.");
        }

        return true;
    }

    protected int getTileWidth() {
        return getWidth() / getGridSize().x;
    }

    protected int getTileHeight() {
        return getHeight() / getGridSize().y;
    }
}
