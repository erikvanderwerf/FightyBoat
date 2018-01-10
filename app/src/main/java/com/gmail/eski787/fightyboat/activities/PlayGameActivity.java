package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlayGameFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.state.GameAction;

public class PlayGameActivity extends LockableActivity implements PlayGameFragment.PlayGameInteraction, Game.GameChangeListener {
    public static final String ARG_GAME = "ARG_GAME";
    private static final String TAG = PlayGameActivity.class.getSimpleName();
    private Game mGame;
    private Fragment mFragment;

    @Override
    protected int getFragmentId() {
        return R.id.fragment_play_game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        if (savedInstanceState == null) {
            // Get extras
            Intent intent = getIntent();
            mGame = intent.getParcelableExtra(ARG_GAME);
        } else {
            mGame = savedInstanceState.getParcelable(ARG_GAME);
        }
        assert mGame != null;
        mGame.addGameChangeListener(this);
        onGameChange();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_GAME, mGame);
    }

    @Override
    public void onSuccessfulUnlock() {
        super.onSuccessfulUnlock();
        mGame.sendAction(new GameAction.UnlockAction());
    }

    @Override
    public Player getPlayer() {
        return mGame.getCurrentPlayer()
    }

    @Override
    public void onGameChange() {
        @Game.TurnState int state = mGame.getTurnState();

        Fragment fragment = null;

        switch (state) {
            case Game.AWAITING_UNLOCK:
                fragment = getPlayer().getLockSettings().getLockFragment();
                break;
            case Game.AWAITING_CONTINUE:
                fragment = new PlayGameFragment();
                break;
            case Game.AWAITING_FIRE:
            case Game.AWAITING_SELECTION:
            default:
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentId(), fragment)
                    .commit();
            mFragment = fragment;
        } else {
//            mFragment.getView().invalidate();
        }
    }
}
