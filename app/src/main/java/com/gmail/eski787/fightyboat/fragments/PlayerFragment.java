package com.gmail.eski787.fightyboat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * Created by Erik on 1/14/2017.
 */

public abstract class PlayerFragment extends Fragment {
    protected static final String ARG_PLAYER = "arg_player";

    @Nullable
    protected Player mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayer = getArguments().getParcelable(ARG_PLAYER);
        }
    }

    public void onAttachPlayer(Player player) {
        mPlayer = player;
    }
}
