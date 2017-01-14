package com.gmail.eski787.fightyboat.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.FragmentPlayerUnlockButtonBinding;
import com.gmail.eski787.fightyboat.models.PlayerModel;

/**
 * Created by Erik on 1/14/2017.
 */

public class ButtonLockFragment extends LockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPlayerUnlockButtonBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_unlock_button, container, false);
        View view = binding.getRoot();

        binding.setUser(new PlayerModel(mPlayer));
        binding.buttonUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onUnlockAttempt(true);
            }
        });
        return view;
    }
}
