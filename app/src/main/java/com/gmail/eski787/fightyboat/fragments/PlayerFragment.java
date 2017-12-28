package com.gmail.eski787.fightyboat.fragments;

import android.os.Bundle;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * Any Fragment which uses a Player instance should extend this.
 * Use .setArguments(Bundle) to attach a bundle with the relevant Player to ARG_PLAYER.
 */

public abstract class PlayerFragment extends ClickableFragment {
    public static final String ARG_PLAYER = "ARG_PLAYER";
    protected Player mPlayer;

    public static <T extends PlayerFragment> T newInstance(Player player, Class<T> retClass)
            throws IllegalAccessException, java.lang.InstantiationException {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PLAYER, player);
        // I feel dirty.
        T instance = retClass.newInstance();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        Player player = arguments.getParcelable(ARG_PLAYER);
        if (player == null) {
            throw new RuntimeException("Cannot create a PlayerFragment with no Player.");
        }
        mPlayer = player;

    }
}
