package com.gmail.eski787.fightyboat.activities;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlaceShipFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerDetailFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerListFragment;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;

import java.util.ArrayList;
import java.util.List;


/**
 * This activity manages the state for creating a new game.
 * <p>
 * This activity supports the following fragments:
 * - {@link PlayerListFragment}
 * - {@link PlayerDetailFragment}
 * - {@link PlaceShipFragment}
 */
public class NewGameActivity extends AppCompatActivity
        implements PlayerListFragment.PlayerListInteraction, PlayerDetailFragment.PlayerDetailInteraction, PlaceShipFragment.PlaceShipInteraction {
    private static final String TAG = NewGameActivity.class.getCanonicalName();
    private static final String ARG_PLAYER_LIST = "ARG_PLAYER_LIST";
    private Fragment mFragment;
    /* ArrayList (not List) in order to serialize in Bundle */
    private ArrayList<Player> players;


    @Override
    public List<Player> getPlayerList() {
        return players;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        if (savedInstanceState == null) {
            // Initialize players with 2 local players.
            Player player1 = new Player("Player 1", new Sea(10, 10));
            List<Ship> p1ship = player1.getSea().getShips();
            p1ship.add(new Ship(new Point(5, 5), Ship.Orientation.VERTICAL, 5));
            p1ship.add(new Ship(new Point(0, 7), Ship.Orientation.HORIZONTAL, 4));
            p1ship.add(new Ship(new Point(0, 0), Ship.Orientation.HORIZONTAL, 3));
            p1ship.add(new Ship(new Point(7, 3), Ship.Orientation.HORIZONTAL, 3));
            p1ship.add(new Ship(new Point(2, 2), Ship.Orientation.HORIZONTAL, 2));

            players = new ArrayList<>();
            players.add(player1);
            players.add(new Player("Player 2", new Sea(10, 10)));
        } else {
            players = savedInstanceState.getParcelableArrayList(ARG_PLAYER_LIST);
        }

        // Initialize activity with player list.
        mFragment = new PlayerListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_new_game, mFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_settings:
                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_PLAYER_LIST, players);
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
        mFragment = PlayerDetailFragment.newInstance(player);

        // Set transition behavior.
        mFragment.setSharedElementEnterTransition(new AutoTransition());
        mFragment.setSharedElementReturnTransition(new AutoTransition());

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

    @Override
    public void onChangeLock(Player mPlayer) {
        // TODO Change Lock display
    }

    @Override
    public void onMoveShips(Player player) {
        // Setup PlaceShip Fragment
        mFragment = PlaceShipFragment.newInstance(player);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TAG)
                .replace(R.id.fragment_new_game, mFragment)
                .commit();
    }

    @Override
    public void onShipPlaceComplete(Player player) {
        getSupportFragmentManager().popBackStack();
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
