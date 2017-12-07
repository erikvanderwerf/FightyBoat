package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gmail.eski787.fightyboat.presenters.RadarPresenter;

/**
 * Created by Erik on 1/15/2017.
 */

public class RadarView extends GridView.SquareView {
    @Nullable
    private RadarPresenter mPresenter;

    public RadarView(Context context) {
        super(context);
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected Point getGridSize() {
        return new Point(10, 10);
    }

    @Override
    protected boolean onClick(MotionEvent event) {
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPresenter == null) {
            return;
        }

    }
}
