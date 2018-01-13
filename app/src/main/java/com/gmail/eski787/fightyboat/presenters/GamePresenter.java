package com.gmail.eski787.fightyboat.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gmail.eski787.fightyboat.fragments.PlayGameFragment;
import com.gmail.eski787.fightyboat.game.Game;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.state.GameAction;
import com.gmail.eski787.fightyboat.game.state.TurnState;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Erik on 1/12/2018.
 */

public class GamePresenter implements Presenter, Game.GameChangeListener {
    @NonNull
    private final Game mGame;
    @Nullable
    private GamePresenterInteraction mListener;

    public GamePresenter(@NonNull Game game) {
        mGame = game;
        mGame.addGameChangeListener(this);
        onGameInvalidate();
    }

    /**
     * To be used only for serialization purposes. Promise.
     *
     * @return Game instance for this presenter.
     */
    @NonNull
    public Game getGame() {
        return mGame;
    }

    public List<PlayGameRadarPresenter> getOpponentRadars() {
        List<PlayGameRadarPresenter> radarPresenters = new LinkedList<>();
        for (Player player : mGame.getOpponents()) {
            radarPresenters.add(new PlayGameRadarPresenter(player.getSea()));
        }
        return radarPresenters;
    }

    public void onAttach(GamePresenterInteraction fragment) {
        mListener = fragment;
    }

    public void onDetach() {
        mListener = null;
    }

    public void onUnlock() {
        mGame.sendAction(new GameAction.UnlockAction());
    }

    @Override
    public void onGameInvalidate() {
        TurnState state = mGame.getTurnState();

        Fragment fragment = null;

        switch (state.getState()) {
            case TurnState.STATE_LOCKED:
                fragment = getCurrentPlayer().getLockSettings().getLockFragment();
                break;
            case TurnState.STATE_CONTINUE:
                fragment = new PlayGameFragment();
                break;
            case TurnState.STATE_FIRE:
            case TurnState.STATE_SELECTION:
            default:
                break;
        }

        if (mListener != null) {
            if (fragment != null) {
                mListener.transitionToFragment(fragment);
            }
        }
    }

    public void onPlayButtonClick() {

    }

    public Player getCurrentPlayer() {
        return mGame.getCurrentPlayer();
    }

    public interface GamePresenterInteraction {
        void transitionToFragment(@NonNull Fragment fragment);
    }
}
