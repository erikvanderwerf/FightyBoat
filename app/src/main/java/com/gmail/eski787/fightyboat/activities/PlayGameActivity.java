package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlayGameFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;

public class PlayGameActivity extends LockableActivity implements PlayGameFragment.PlayGameInteraction {
    public static final String ARG_GAME = "ARG_GAME";
    private static final String ARG_PLAYER_INDEX = "ARG_PLAYER_INDEX";
    private Game mGame;
    private int mPlayerIndex;


    @Override
    protected int getFragmentId() {
        return R.id.fragment_play_game;
    }

    @Override
    public Player[] getPlayers() {
        return mGame.getPlayers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        if (savedInstanceState == null) {
            // Get extras
            Intent intent = getIntent();
            mGame = intent.getParcelableExtra(ARG_GAME);
            mPlayerIndex = 0;
        } else {
            mGame = savedInstanceState.getParcelable(ARG_GAME);
            mPlayerIndex = savedInstanceState.getInt(ARG_PLAYER_INDEX);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_GAME, mGame);
        outState.putInt(ARG_PLAYER_INDEX, mPlayerIndex);
    }

    @Override
    public void onSuccessfulUnlock() {
        super.onSuccessfulUnlock();
        Player player = mGame.getPlayers()[mPlayerIndex];
        Toast.makeText(getApplicationContext(), "Unlocked " + player.getName(), Toast.LENGTH_SHORT).show();

        PlayGameFragment gameFragment = PlayGameFragment.newInstance(player);

        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentId(), gameFragment)
                .commit();
    }
}
