package com.gmail.eski787.fightyboat.models;

import com.gmail.eski787.fightyboat.game.Player;

/**
 * Created by Erik on 12/21/2016.
 */

public class PlayerModel {
    final Player player;
    public PlayerModel(final Player player) {
        this.player = player;
    }

    public String getName() {
        return player.name;
    }
}
