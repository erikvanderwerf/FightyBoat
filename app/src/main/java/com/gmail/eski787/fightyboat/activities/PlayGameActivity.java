package com.gmail.eski787.fightyboat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.fragments.PlayGameFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.presenters.GamePresenter;

public class PlayGameActivity extends LockableActivity implements PlayGameFragment.PlayGameInteraction, GamePresenter.StateTransitionListener {
    public static final String ARG_GAME = "ARG_GAME";
    private static final String TAG = PlayGameActivity.class.getSimpleName();
    private GamePresenter mGamePresenter;
    private Fragment mFragment;

    @Override
    protected int getFragmentId() {
        return R.id.fragment_play_game;
    }

    @Override
    public GamePresenter getGame() {
        return mGamePresenter;
    }

    @Override
    public Player getPlayer() {
        return mGamePresenter.getCurrentPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        Game game;
        if (savedInstanceState == null) {
            // Get extras
            Intent intent = getIntent();
            game = intent.getParcelableExtra(ARG_GAME);
        } else {
            game = savedInstanceState.getParcelable(ARG_GAME);
        }
        mGamePresenter = new GamePresenter(game);
        mGamePresenter.registerStateTransitionListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ARG_GAME, mGamePresenter.getGame());
    }

    @Override
    public void onSuccessfulUnlock() {
        super.onSuccessfulUnlock();
        mGamePresenter.onUnlock();
    }

    @Override
    public void transitionToFragment(@NonNull Fragment fragment) {
        Log.d(TAG, String.format("Transitioning Fragment from: %s to: %s",
                mFragment != null ? mFragment : "null", fragment));
        mFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentId(), fragment)
                .commit();
    }

    public void invalidateFragment() {
        View view = mFragment.getView();
        if (view != null) {
            view.invalidate();
        }
    }
}
