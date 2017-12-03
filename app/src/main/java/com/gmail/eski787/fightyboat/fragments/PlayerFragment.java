package com.gmail.eski787.fightyboat.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * Any Fragment which uses a Player instance should extend this.
 * Use .setArguments(Bundle) to attach a bundle with the relevant Player to ARG_PLAYER.
 */

public abstract class PlayerFragment extends Fragment {
    public static final String ARG_PLAYER = "ARG_PLAYER";
    protected Player mPlayer;

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
