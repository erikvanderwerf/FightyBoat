package com.gmail.eski787.fightyboat.presenters;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

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
    public static final String TAG = GamePresenter.class.getSimpleName();
    @NonNull
    private final Game mGame;
    @Nullable
    private StateTransitionListener mStateTransitionListener;
    private PlayButtonListener mPlayButtonListener;

    public GamePresenter(@NonNull Game game) {
        mGame = game;
        mGame.addGameChangeListener(this);
    }

    public Player getCurrentPlayer() {
        return mGame.getCurrentPlayer();
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

    public List<PlayGameRadarPresenter> getOpponentRadarPresenters() {
        List<PlayGameRadarPresenter> radarPresenters = new LinkedList<>();
        for (Player player : mGame.getOpponents()) {
            radarPresenters.add(new PlayGameRadarPresenter(player.getSea()));
        }
        return radarPresenters;
    }

    public SeaPresenter getPlayerSeaPresenter() {
        return new SeaPresenter(mGame.getCurrentPlayer().getSea());
    }

    public void onUnlock() {
        mGame.postAction(new GameAction.UnlockAction(mGame.getOpponentIndex()));
    }

    @Override
    public void onStateTransition(TurnState from, TurnState to) {
        Fragment fragment = null;

        switch (to.getState()) {
            case TurnState.STATE_LOCKED:
                fragment = getCurrentPlayer().getLockSettings().getLockFragment();
                break;
            case TurnState.STATE_NO_SELECTION:
                fragment = new PlayGameFragment();
                break;
            case TurnState.STATE_CONTINUE:
            case TurnState.STATE_SELECTION:
                break;
            default:
                Log.d(TAG, "No state to switch.");
                break;
        }

        Log.d(TAG, String.format("onStateTransition: %s %s %s", mPlayButtonListener, mStateTransitionListener, fragment));
        if (mPlayButtonListener != null) {
            mPlayButtonListener.setPlayButton(to.getPlayButtonText(), to.getPlayButtonState());
        }
        if (mStateTransitionListener != null) {
            if (fragment != null) {
                mStateTransitionListener.transitionToFragment(fragment);
            } else {
                mStateTransitionListener.invalidateFragment();
            }
        }
    }

    public void onPlayButtonClick() {
        mGame.postAction(new GameAction.PlayButtonAction(mGame.getOpponentIndex()));
    }

    public void onUserClick(PointF coordinate) {
        mGame.postAction(new GameAction.SelectAction(mGame.getOpponentIndex(), coordinate));
    }

    public void registerStateTransitionListener(StateTransitionListener listener) {
        mStateTransitionListener = listener;
        onStateTransition(null, mGame.getTurnState());
    }

    public void registerPlayButtonListener(PlayButtonListener listener) {
        mPlayButtonListener = listener;
        TurnState state = mGame.getTurnState();
        mPlayButtonListener.setPlayButton(state.getPlayButtonText(), state.getPlayButtonState());
    }

    public interface StateTransitionListener {
        void transitionToFragment(@NonNull Fragment fragment);

        void invalidateFragment();
    }

    public interface PlayButtonListener {
        void setPlayButton(String text, boolean enabled);
    }
}
