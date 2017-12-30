package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * Any Fragment which uses a Player instance should extend this.
 */

public abstract class PlayerFragment extends ClickableFragment {
    protected Player mPlayer;
    private PlayerFragmentInteraction mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayerFragmentInteraction) {
            mListener = (PlayerFragmentInteraction) context;
            mPlayer = mListener.getPlayer();
        } else {
            throw new RuntimeException(context.toString() + " must implement PlayerFragmentInteraction.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mPlayer = null;
    }

    public interface PlayerFragmentInteraction {
        Player getPlayer();
    }
}
