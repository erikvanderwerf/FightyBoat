package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * Any Fragment which uses a Player instance should extend this.
 */

public abstract class PlayerFragment extends Fragment {
    private static final String TAG = PlayerFragment.class.getSimpleName();
    private PlayerFragmentInteraction mListener;

    protected final Player getPlayer() {
        return mListener.getPlayer();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
        if (context instanceof PlayerFragmentInteraction) {
            mListener = (PlayerFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PlayerFragmentInteraction.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PlayerFragmentInteraction {
        Player getPlayer();
    }
}
