package com.gmail.eski787.fightyboat.models;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * This model is used to translate attributes of a Player instance to layout fields.
 * Created by Erik on 12/21/2016.
 */

public class PlayerModel {
    private final Player player;
    public PlayerModel(final Player player) {
        this.player = player;
    }

    public String getName() {
        return player.getName();
    }
}
