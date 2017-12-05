package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.FragmentPlayerDetailBinding;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.models.PlayerModel;

/**
 * This fragment will provide detailed information about a player, and allow them to change
 * their name, avatar image, and ship placement.
 * Created by Erik on 11/24/2017.
 */
public class PlayerDetailFragment extends PlayerFragment {
    private PlayerDetailInteraction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param player Player to use for this Fragment.
     * @return A new instance of fragment PlaceShipFragment.
     */
    public static PlayerDetailFragment newInstance(Player player) {
        PlayerDetailFragment fragment = new PlayerDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayerDetailInteraction) {
            mListener = (PlayerDetailInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PlayerDetailInteraction.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_player_detail, container, false);
        final int layoutId = R.layout.fragment_player_detail;
        FragmentPlayerDetailBinding binding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        binding.setUser(new PlayerModel(mPlayer));

        // Add onClick callbacks
        binding.changeLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onChangeLock(mPlayer);
            }
        });

        binding.moveShips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMoveShips(mPlayer);
            }
        });

        return binding.getRoot();
    }

    public interface PlayerDetailInteraction {
        void onChangeLock(Player mPlayer);
        void onMoveShips(Player mPlayer);
    }
}
