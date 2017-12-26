package com.gmail.eski787.fightyboat.game;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;

/**
 * Created by Erik on 12/25/2017.
 */

abstract class LockSettings implements Parcelable {
    LockSettings(Parcel in) {

    }

    public abstract LockFragment getLockFragment();

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static class ButtonLockSettings extends LockSettings {
        public static final Creator<ButtonLockSettings> CREATOR = new Creator<ButtonLockSettings>() {
            @Override
            public ButtonLockSettings createFromParcel(Parcel in) {
                return new ButtonLockSettings(in);
            }

            @Override
            public ButtonLockSettings[] newArray(int size) {
                return new ButtonLockSettings[size];
            }
        };

        ButtonLockSettings(Parcel in) {
            super(in);
        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public LockFragment getLockFragment() {
            ButtonLockFragment fragment = new ButtonLockFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(LockFragment.ARG_LOCK_SETTINGS, this);
            return fragment;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }
    }
}
