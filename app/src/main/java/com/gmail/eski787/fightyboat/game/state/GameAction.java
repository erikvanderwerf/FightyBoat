package com.gmail.eski787.fightyboat.game.state;

import com.gmail.eski787.fightyboat.game.Game;

/**
 * Created by Erik on 1/9/2018.
 */

public abstract class GameAction {
    public abstract @Game.TurnState
    int[] validStates();

    public abstract @Game.TurnState
    int transition(Game game);

    /**
     * Created by Erik on 1/9/2018.
     */
    public static class UnlockAction extends GameAction {
        @Override
        public int[] validStates() {
            return new int[]{Game.AWAITING_UNLOCK};
        }

        @Override
        public int transition(Game game) {
            return Game.AWAITING_SELECTION;
        }
    }

    public static class SelectAction extends GameAction {

        @Override
        public int[] validStates() {
            return new int[]{Game.AWAITING_SELECTION};
        }

        @Override
        public int transition(Game game) {
            return Game.AWAITING_FIRE;
        }
    }
}
