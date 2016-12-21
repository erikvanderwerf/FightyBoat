package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.FragmentPlayerLockBinding;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.models.PlayerModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerLockInteraction} interface
 * to handle interaction events.
 * Use the {@link PlayerLockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerLockFragment extends Fragment {
    public static final String TAG = PlayerLockFragment.class.getName();
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PLAYER = "arg_player";

    private Player mPlayer;

    private PlayerLockInteraction mListener;

    public PlayerLockFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param player Player.
     * @return A new instance of fragment PlayerLockFragment.
     */
    public static PlayerLockFragment newInstance(Player player) {
        PlayerLockFragment fragment = new PlayerLockFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayer = getArguments().getParcelable(ARG_PLAYER);
        }

        Fragment parent = getParentFragment();
        Log.d(TAG, parent.toString());
        if (parent instanceof PlayerLockInteraction)
            mListener = (PlayerLockInteraction) getParentFragment();
        else {
            throw new RuntimeException("Parent fragment must implement PlayerLockInteraction");
        }

        FragmentPlayerLockBinding binding = DataBindingUtil.getBinding(getView());
        binding.setUser(new PlayerModel(mPlayer));
        binding.buttonUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onUnlockAttempt(true);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_lock, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
    public interface PlayerLockInteraction {
        void onUnlockAttempt(boolean success);
    }
}
