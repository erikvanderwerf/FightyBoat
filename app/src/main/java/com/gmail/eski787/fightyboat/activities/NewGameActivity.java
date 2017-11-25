package com.gmail.eski787.fightyboat.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlayerDetailFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerListFragment;
import com.gmail.eski787.fightyboat.game.Player;

public class NewGameActivity extends AppCompatActivity
        implements PlayerListFragment.PlayerListInteraction {
    private static final String TAG = NewGameActivity.class.getCanonicalName();
//    private List<Player> players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        // Initialize players with 1 local player.
//        players = new ArrayList<>();
//        players.add(new Player("Player 1", new Sea(10, 10)));

        // Initialize activity with player list.
        PlayerListFragment playerListFragment = new PlayerListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_new_game, playerListFragment)
                .commit();
    }

    @Override
    public void onPlayerSelect(Player player) {
        PlayerDetailFragment detailFragment = new PlayerDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("player", player);
        detailFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_new_game, detailFragment)
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
