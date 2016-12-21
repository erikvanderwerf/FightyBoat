package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Player;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerAdvancementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerAdvancementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerAdvancementFragment extends Fragment implements PlayerLockFragment.PlayerLockInteraction {
    public static String TAG = PlayerAdvancementFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    public PlayerAdvancementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayerAdvancementFragment.
     */
    public static PlayerAdvancementFragment newInstance() {
        PlayerAdvancementFragment fragment = new PlayerAdvancementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void advanceTo(Player player, Fragment fragment) {
        Log.d(TAG, player.toString());
        //Log.d(TAG, fragment.toString());

        PlayerLockFragment lockFragment = PlayerLockFragment.newInstance(player);
        Log.d(TAG, lockFragment.toString());

        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_player_advancement, lockFragment)
                .commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_advancement, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onUnlockAttempt(boolean success) {

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
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
