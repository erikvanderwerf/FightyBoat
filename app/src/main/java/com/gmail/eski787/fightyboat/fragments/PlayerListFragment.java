package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.LayoutPlayerShortBinding;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.models.PlayerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 11/24/2017.
 */

public class PlayerListFragment extends Fragment {
    private static String TAG = PlayerListFragment.class.getCanonicalName();

    private List<Player> players;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlayerListInteraction mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayerListInteraction) {
            mListener = (PlayerListInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement PlayerListInteraction");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        players = new ArrayList<>();
        players.add(new Player("Player 1", new Sea(10, 10)));

        // Inflate the layout for this fragment.
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);

        mRecyclerView = view.findViewById(R.id.player_list);
        // Changes in content do not change the layout size.
        mRecyclerView.setHasFixedSize(true);
        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Setup Adapter
        mAdapter = new PlayerListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this fragment.
     */
    public interface PlayerListInteraction {
        void onPlayerSelect(Player player);
    }

    private static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = PlayerViewHolder.class.getCanonicalName();
        LayoutPlayerShortBinding binding;

        public PlayerViewHolder(View itemView) {
            super(itemView);
        }

        public PlayerViewHolder(LayoutPlayerShortBinding playerListBinding) {
            this(playerListBinding.getRoot());
            binding = playerListBinding;
        }
    }

    private class PlayerListAdapter extends RecyclerView.Adapter<PlayerViewHolder> {
        @Override
        public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layoutId = R.layout.layout_player_short;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LayoutPlayerShortBinding playerShortBinding = DataBindingUtil
                    .inflate(inflater, layoutId, parent, false);
            return new PlayerViewHolder(playerShortBinding);
        }

        @Override
        public void onBindViewHolder(PlayerViewHolder holder, int position) {
            final Player player = players.get(position);
            final PlayerModel model = new PlayerModel(player);
            holder.binding.setUser(model);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "OnClick: " + player.getName());
                    mListener.onPlayerSelect(player);
                }
            });
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }
}
