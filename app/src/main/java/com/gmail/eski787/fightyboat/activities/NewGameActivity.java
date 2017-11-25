package com.gmail.eski787.fightyboat.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.view.View;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlayerDetailFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerListFragment;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Sea;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.eski787.fightyboat.fragments.PlayerFragment.ARG_PLAYER;

public class NewGameActivity extends AppCompatActivity
        implements PlayerListFragment.PlayerListInteraction {
    private static final String TAG = NewGameActivity.class.getCanonicalName();
    private Fragment mFragment;
    private List<Player> players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        // Initialize players with 1 local player.
        players = new ArrayList<>();
        players.add(new Player("Player 1", new Sea(10, 10)));
        // players.add(new Player("Player 2", new Sea(10, 10)));

        // Initialize activity with player list.
        mFragment = new PlayerListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_new_game, mFragment)
                .commit();
    }

    @Override
    public List<Player> getPlayerList() {
        return players;
    }

    /**
     * Change the fragment to display a detailed view of a single player.
     * Called when the user selects a player from the {@link PlayerListFragment}.
     *
     * @param player   The Player to view.
     * @param itemView The list element View to map transitions to.
     */
    @Override
    public void onPlayerSelect(Player player, View itemView) {
        // Setup detail fragment with player.
        mFragment = new PlayerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PLAYER, player);
        mFragment.setArguments(bundle);

        // Set transition behavior.
        mFragment.setSharedElementEnterTransition(new AutoTransition());
        // mFragment.setSharedElementReturnTransition(new AutoTransition());

        // Get transition views.
        View avatar = itemView.findViewById(R.id.player_avatar);
        ViewCompat.setTransitionName(avatar, getString(R.string.transition_avatar));

        View name = itemView.findViewById(R.id.player_name);
        ViewCompat.setTransitionName(name, getString(R.string.transition_name));

        // Begin fragment transaction.
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(avatar, getString(R.string.transition_avatar))
                .addSharedElement(name, getString(R.string.transition_name))
                .addToBackStack(TAG)
                .replace(R.id.fragment_new_game, mFragment)
                .commit();
    }

//    private void onStartGame() {
//         Create game
//        GameSettings settings = new GameSettings();
//        settings.seaSize.set(10, 10);
//
//        Player[] players = new Player[2];
//        players[0] = new Player("Alice", new Sea(10, 10));
//        players[1] = new Player("Bob", new Sea(10, 10));
//
//        Game game = new Game((Player[]) players.toArray(), settings);
//
//        Intent new_game = new Intent(this, PlayGameActivity.class);
//        new_game.putExtra(IntentConstant.GAME, game);
//        startActivity(new_game);
//    }
}
