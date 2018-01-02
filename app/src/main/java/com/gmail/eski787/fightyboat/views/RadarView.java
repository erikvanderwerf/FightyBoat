package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.util.AttributeSet;

import com.gmail.eski787.fightyboat.presenters.RadarPresenter;

/**
 * Created by Erik on 1/15/2017.
 */

public abstract class RadarView<T extends RadarPresenter> extends SeaView<T> {
    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
