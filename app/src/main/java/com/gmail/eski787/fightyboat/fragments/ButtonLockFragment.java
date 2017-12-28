package com.gmail.eski787.fightyboat.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.FragmentLockButtonBinding;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.models.PlayerModel;

/**
 * A simple button lock screen. Press to unlock.
 * @see com.gmail.eski787.fightyboat.game.LockSettings.ButtonLockSettings
 */

public class ButtonLockFragment extends LockFragment {

    /**
     * This method is provided as a convinience in the case that a certain lock class is unable to
     * be instantiated using {@link PlayerFragment#newInstance(Player, Class)}. In the event of
     * a class instantiation failure this method should be used directly as this cannot fail.
     *
     * @param player The player to associate the lock with.
     * @return An instance of a {@link ButtonLockFragment} associated with the player.
     * @see PlayerFragment#newInstance(Player, Class)
     */
    public static ButtonLockFragment newInstance(Player player) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PLAYER, player);

        ButtonLockFragment buttonLockFragment = new ButtonLockFragment();
        buttonLockFragment.setArguments(bundle);
        return buttonLockFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLockButtonBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_lock_button, container, false);

        binding.setUser(new PlayerModel(mPlayer));
        binding.unlockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSuccessfulUnlock();
            }
        });
        return binding.getRoot();
    }

    @Override
    public boolean onButtonClick(View view) {
        return false;
    }
}
