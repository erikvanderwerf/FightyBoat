package com.gmail.eski787.fightyboat.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.FragmentLockButtonBinding;
import com.gmail.eski787.fightyboat.models.PlayerModel;

/**
 * A simple button lock screen. Press to unlock.
 * @see com.gmail.eski787.fightyboat.game.LockSettings.ButtonLockSettings
 */

public class ButtonLockFragment extends LockFragment {
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
