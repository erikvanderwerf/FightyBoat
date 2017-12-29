package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListPopupWindow;
import android.transition.AutoTransition;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlaceShipFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerDetailFragment;
import com.gmail.eski787.fightyboat.fragments.PlayerListFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.GameSettings;
import com.gmail.eski787.fightyboat.game.LockSettings;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Sea;
import com.gmail.eski787.fightyboat.game.Ship;
import com.gmail.eski787.fightyboat.presenters.ShipCap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * This activity manages the state for creating a new game.
 * <p>
 * This activity supports the following fragments:
 * - {@link PlayerListFragment}
 * - {@link PlayerDetailFragment}
 * - {@link PlaceShipFragment}
 */
public class NewGameActivity extends LockableActivity
        implements PlayerListFragment.PlayerListInteraction, PlayerDetailFragment.PlayerDetailInteraction, PlaceShipFragment.PlaceShipInteraction {
    private static final String TAG = NewGameActivity.class.getSimpleName();
    private static final String ARG_PLAYER_LIST = "ARG_PLAYER_LIST";
    private static final String ARG_DETAIL_INDEX = "ARG_DETAIL_INDEX";
//    private Stack<ClickableFragment> mFragmentStack = new Stack<>();
//    private ClickableFragment mFragment;
    /**
     * ArrayList (not List) in order to serialize in Bundle
     */
    private ArrayList<Player> mPlayers;
    /**
     * Index of the player whose details are being shown, or -1 if no player is selected.
     */
    private int mPlayerDetailIndex;

    @Override
    public List<Player> getPlayerList() {
        return mPlayers;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

//    public void onButtonClick(View view) {
//        boolean handled = false;
//        if (mFragment != null) {
//            handled = mFragment.onButtonClick(view);
//        }
//        if (!handled) {
//            Log.d(TAG, String.format("Button click was not handled. View: %d\tFragment: %s",
//                    view.getId(), mFragment));
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);

        if (savedInstanceState == null) {
            onCreateNoSavedState();
            mPlayerDetailIndex = -1;
        } else {
            mPlayers = savedInstanceState.getParcelableArrayList(ARG_PLAYER_LIST);
            mPlayerDetailIndex = savedInstanceState.getInt(ARG_DETAIL_INDEX);

        }

        // Set fragment to player list or selected player detail.
        Fragment fragment;
        if (mPlayerDetailIndex == -1) {
            fragment = new PlayerListFragment();
        } else {
            Player player = getPlayerList().get(mPlayerDetailIndex);
            if (isLocked()) {
                lock(player);
                return;
            } else {
                fragment = PlayerDetailFragment.newInstance(player);
            }
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_new_game, fragment)
                .commit();
    }

    private void onCreateNoSavedState() {
        // Initialize players with 2 local players.
        Player player1 = new Player("Player 1", new Sea(10, 10, 5));
        ShipCap.CapType startCap = ShipCap.CapType.ROUND, endCap = ShipCap.CapType.SQUARE;
        Set<Ship> p1ship = player1.getSea().getShips();
        p1ship.add(new Ship(new Point(5, 5), Ship.Orientation.VERTICAL, 5, startCap, endCap));
        p1ship.add(new Ship(new Point(0, 7), Ship.Orientation.HORIZONTAL, 4, startCap, endCap));
        p1ship.add(new Ship(new Point(0, 0), Ship.Orientation.HORIZONTAL, 3, startCap, endCap));
        p1ship.add(new Ship(new Point(7, 3), Ship.Orientation.HORIZONTAL, 3, startCap, endCap));
        p1ship.add(new Ship(new Point(2, 2), Ship.Orientation.HORIZONTAL, 2, startCap, endCap));

        mPlayers = new ArrayList<>();
        mPlayers.add(player1);
        mPlayers.add(new Player("Player 2", new Sea(10, 10, 5)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_PLAYER_LIST, mPlayers);
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
        mPlayerDetailIndex = getPlayerList().indexOf(player);
        lock(player);
    }

    /**
     * Called when the user chooses to change their lock type.
     *
     * @param player The player whose lock to select.
     */
    @Override
    public void onChangeLock(final Player player) {
        final String[] types = LockSettings.LockType.getNames();
        final ListPopupWindow popupWindow = new ListPopupWindow(this);

        popupWindow.setAnchorView(this.findViewById(R.id.player_detail_change_lock));
        popupWindow.setAdapter(new ArrayAdapter<>(NewGameActivity.this,
                R.layout.layout_select_lock, types));
        popupWindow.setModal(true);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the mapping to the LockType that was selected.
                String name = types[position];
                LockSettings.LockType lockType = LockSettings.LockType.valueOfName(name);
                if (lockType == null) {
                    Log.e(TAG, String.format("Cannot find Lock Type with name: %s. Defaulting to " +
                            "Button.", name));
                    lockType = LockSettings.LockType.BUTTON_LOCK;
                }

                // Change Player Lock to selected. Get user settings if required.
                Class<? extends LockSettings> lockClass = lockType.lockSettingClass;
                Toast.makeText(NewGameActivity.this,
                        "Selected " + lockClass.toString(), Toast.LENGTH_SHORT).show();
                // TODO Display new popup/fragment for specific lock settings.
                LockSettings settings;
                try {
                    settings = lockClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e(TAG, String.format("Unable to instantiate LockSettings (%s). Defaulting" +
                            " to Button.", lockClass));
                    settings = new LockSettings.ButtonLockSettings();
                }
                player.setLockSettings(settings);
                popupWindow.dismiss();
            }
        });
        popupWindow.show();
    }

    /**
     * Called when the user selects to change their ship positions.
     *
     * @param player Current player
     */
    @Override
    public void onMoveShips(Player player) {
        // Setup PlaceShip Fragment
        PlaceShipFragment fragment = PlaceShipFragment.newInstance(player);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(TAG)
                .replace(getFragmentId(), fragment)
                .commit();
    }

    @Override
    public void onShipPlaceComplete(Player player) {
        onBackPressed();
    }

    public void onStartGame() {
        // Create game
        GameSettings settings = new GameSettings();
        settings.seaSize.set(10, 10);

        Player[] playersArray = mPlayers.toArray(new Player[mPlayers.size()]);
        Game game = new Game(playersArray, settings);

        Intent new_game = new Intent(this, PlayGameActivity.class);
        new_game.putExtra(PlayGameActivity.ARG_GAME, game);
        startActivity(new_game);
    }

    @Override
    @IdRes
    protected int getFragmentId() {
        return R.id.fragment_new_game;
    }

    @Override
    public void onSuccessfulUnlock() {
        super.onSuccessfulUnlock();
        Player player = getPlayerList().get(mPlayerDetailIndex);
        // Setup detail fragment with player.
        PlayerDetailFragment fragment = PlayerDetailFragment.newInstance(player);

        // Set transition behavior.
        fragment.setSharedElementEnterTransition(new AutoTransition());
        fragment.setSharedElementReturnTransition(new AutoTransition());

        // Get transition views.
//        View avatar = itemView.findViewById(R.id.player_detail_avatar);
//        ViewCompat.setTransitionName(avatar, getString(R.string.transition_avatar));
//        View name = itemView.findViewById(R.id.player_name);
//        ViewCompat.setTransitionName(name, getString(R.string.transition_name));

        // Begin fragment transaction.
        getSupportFragmentManager()
                .beginTransaction()
//                .addSharedElement(avatar, getString(R.string.transition_avatar))
//                .addSharedElement(name, getString(R.string.transition_name))
                .addToBackStack(TAG)
                .replace(R.id.fragment_new_game, fragment)
                .commit();
    }
}
