package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LockInteraction} interface
 * to handle interaction events.
 */
public abstract class LockFragment extends PlayerFragment {
    private static final String TAG = LockFragment.class.getSimpleName();
    protected LockInteraction mListener;

    public LockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LockInteraction)
            mListener = (LockInteraction) context;
        else {
            throw new RuntimeException(context.toString()
                    + " must implement LockInteraction");
        }
        Log.d(TAG, "onAttach: " + mListener.toString());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface LockInteraction {
        /**
         * Called by the {@link LockFragment} when the user has unlocked the displayed lock.
         */
        void onSuccessfulUnlock();
    }
}
