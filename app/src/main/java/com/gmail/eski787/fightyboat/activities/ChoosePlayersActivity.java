package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.LayoutPlayerListBinding;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.GameSettings;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.models.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class ChoosePlayersActivity extends AppCompatActivity {
    private List<Player> players;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_players);

        // Initialize players with 1 local player.
        players = new ArrayList<>();
        players.add(new Player("Player 1", new Sea(10, 10)));

        mRecyclerView = findViewById(R.id.player_list);
        // Changes in content do not change the layout size.
        mRecyclerView.setHasFixedSize(true);
        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Setup Adapter
        mAdapter = new PlayerListAdapter(players);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onStartGame() {
        // Create game
        GameSettings settings = new GameSettings();
        settings.seaSize.set(10, 10);

//        Player[] players = new Player[2];
//        players[0] = new Player("Alice", new Sea(10, 10));
//        players[1] = new Player("Bob", new Sea(10, 10));

        Game game = new Game((Player[]) players.toArray(), settings);

        Intent new_game = new Intent(this, PlayGameActivity.class);
        new_game.putExtra(IntentConstant.GAME, game);
        startActivity(new_game);
    }

    private static class PlayerListAdapter extends RecyclerView.Adapter<PlayerViewHolder> {
        private List<Player> players;

        public PlayerListAdapter(List<Player> players) {
            this.players = players;
        }

        @Override
        public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layoutId = R.layout.layout_player_list;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LayoutPlayerListBinding playerListBinding = DataBindingUtil
                    .inflate(inflater, layoutId, parent, false);
            return new PlayerViewHolder(playerListBinding);
        }

        @Override
        public void onBindViewHolder(PlayerViewHolder holder, int position) {
            Player player = players.get(position);
            holder.binding.setUser(new PlayerModel(player));
        }

        @Override
        public int getItemCount() {
            return players.size();
        }
    }

    private static class PlayerViewHolder extends RecyclerView.ViewHolder {
        LayoutPlayerListBinding binding;

        public PlayerViewHolder(View itemView) {
            super(itemView);
        }

        public PlayerViewHolder(LayoutPlayerListBinding playerListBinding) {
            this(playerListBinding.getRoot());
            binding = playerListBinding;
        }
    }
}
