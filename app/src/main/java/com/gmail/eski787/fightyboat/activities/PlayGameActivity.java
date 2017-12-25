package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlayGameFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;
import com.google.common.collect.Iterables;

import java.util.Iterator;

public class PlayGameActivity extends LockableActivity implements PlayGameFragment.PlayGameInteraction {
    private Game mGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        // Get extras
        Intent intent = getIntent();
        mGame = intent.getParcelableExtra(IntentConstant.GAME);

        Iterator<Player> players = Iterables.cycle(mGame.getPlayers()).iterator();
        setPlayers(players);

        advanceAndLock();
    }

    @Override
    protected int getFragmentId() {
        return R.id.fragment_play_game;
    }

    @Override
    protected void onIteratorComplete() {
        throw new RuntimeException("Cycle should never complete. Was it set?");
    }

    @Override
    protected void onSuccessfulUnlock(Player player) {
        Toast.makeText(getApplicationContext(), "Unlocked " + player.getName(), Toast.LENGTH_SHORT).show();

        PlayGameFragment gameFragment = PlayGameFragment.newInstance(player);

        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentId(), gameFragment)
                .commit();
    }

    @Override
    public Player[] getPlayers() {
        return mGame.getPlayers();
    }
}
