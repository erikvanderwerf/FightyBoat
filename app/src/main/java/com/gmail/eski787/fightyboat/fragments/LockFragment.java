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
    public static final String ARG_LOCK_SETTINGS = "ARG_LOCK_SETTINGS";
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
     * Created by Erik on 12/25/2017.
     */

    public enum LockType {
        BUTTON_LOCK(ButtonLockFragment.class, "Button Lock");

        public final Class<? extends LockFragment> classType;
        public final String name;

        LockType(Class<? extends LockFragment> lockFragmentClass, String name) {
            classType = lockFragmentClass;
            this.name = name;
        }

        /**
         * Get an array of every lock's name.
         *
         * @return Array of every lock's name.
         */
        public static String[] getNames() {
            LockType[] values = LockType.values();
            String[] names = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                names[i] = values[i].name;
            }
            return names;
        }

        public static LockType valueOfName(String name) {
            for (LockType type : LockType.values()) {
                if (type.name.equals(name)) {
                    return type;
                }
            }
            return null;
        }
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
         * This method is called to indicate to the parent that a user has successfully unlocked
         * the {@link LockFragment}.
         */
        void onSuccessfulUnlock();
    }
}
