package com.gmail.eski787.fightyboat.views;

import android.content.Context;
import android.util.AttributeSet;

import com.gmail.eski787.fightyboat.presenters.PlaceShipSeaPresenter;
import com.gmail.eski787.fightyboat.presenters.SeaPresenter;

// TODO: Drag-and-drop of ships from bottom bar

/**
 * Created by Erik on 12/30/2016.
 */

public class PlaceShipSeaView extends SeaView {
    private static final String TAG = PlaceShipSeaView.class.getSimpleName();
    private int cnt = 0;

    public PlaceShipSeaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlaceShipSeaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PlaceShipSeaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAttachSeaPresenter(SeaPresenter seaPresenter) {
        if (seaPresenter instanceof PlaceShipSeaPresenter) {
            super.onAttachSeaPresenter(seaPresenter);
        } else {
            throw new RuntimeException("SeaPresenter must be a PlaceShipSeaPresenter: " + seaPresenter.getClass().getName());
        }
    }
}
