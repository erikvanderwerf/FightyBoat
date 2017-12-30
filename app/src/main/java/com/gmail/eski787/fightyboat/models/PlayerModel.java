package com.gmail.eski787.fightyboat.models;

import android.databinding.BaseObservable;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Player;

/**
 * This model is used to translate attributes of a Player instance to layout fields.
 * Created by Erik on 12/21/2016.
 */

public class PlayerModel extends BaseObservable {
    private final Player mPlayer;
    public PlayerModel(final Player player) {
        this.mPlayer = player;
    }

    public final String getName() {
        return mPlayer.getName();
    }

    public final void setName(String name) {
        mPlayer.setName(name);
//        notifyPropertyChanged(BR.User);
    }

    public final int readyId() {
        return mPlayer.isReady() ? R.drawable.ic_check_black_24dp :
                R.drawable.ic_close_black_24dp;
    }
}
