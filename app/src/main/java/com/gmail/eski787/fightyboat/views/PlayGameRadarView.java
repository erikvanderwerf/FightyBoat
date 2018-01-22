package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.presenters.PlayGameRadarPresenter;

/**
 * Created by Erik on 1/1/2018.
 */

public class PlayGameRadarView extends RadarView<PlayGameRadarPresenter> {
    private static final String TAG = PlayGameRadarView.class.getSimpleName();

    public PlayGameRadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayGameRadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayGameRadarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPresenter == null) {
            Log.d(TAG, "mPresenter is null.");
            return;
        }

        // Draw selected coordinate under everything else
        final Point selected = mPresenter.getSelected();
        Log.d(TAG, String.format("Selected at: %s", selected));
        if (selected != null) {
            final int SELECTED_TILE = ContextCompat.getColor(getContext(), R.color.selected_tile);
            mPaint.setColor(SELECTED_TILE);
            final int height = getTileHeight();
            final int width = getTileWidth();
            canvas.drawRect(selected.x * width, selected.y * height,
                    (selected.x + 1) * width, (selected.y + 1) * height, mPaint);
        }

        super.onDraw(canvas);
    }
}
