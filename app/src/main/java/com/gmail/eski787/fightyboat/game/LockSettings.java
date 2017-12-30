package com.gmail.eski787.fightyboat.game;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gmail.eski787.fightyboat.fragments.ButtonLockFragment;
import com.gmail.eski787.fightyboat.fragments.LockFragment;

import javax.annotation.Nullable;

/**
 * Contains the information related to the lock setting a player has chosen.
 */

public abstract class LockSettings implements Parcelable {
    private static String TAG = LockSettings.class.getSimpleName();

    LockSettings() {
        // Default empty constructor.
    }

    LockSettings(Parcel in) {
        // Default action do nothing.
    }

    /**
     * @return The class of the {@link LockFragment} that this {@link LockSettings} populates.
     */
    protected abstract Class<? extends LockFragment> getLockFragmentClass();

    public LockFragment getLockFragment() {
        try {
            return getLockFragmentClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, String.format("Unable to instantiate player lock (%s). Defaulting to " +
                    "Button.", getLockFragmentClass()));
            return new ButtonLockFragment();
        }
    }

    @Override
    public String toString() {
        return String.format("%s()", getClass().getSimpleName());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Default action do nothing.
    }

    /**
     * An enumeration of the different {@link LockSettings} used in the application.
     */
    public enum LockType {
        BUTTON_LOCK(ButtonLockSettings.class, "Button Lock");

        public final Class<? extends LockSettings> lockSettingClass;
        public final String displayName;

        LockType(Class<? extends LockSettings> lockSettingClass, String displayName) {
            this.lockSettingClass = lockSettingClass;
            this.displayName = displayName;
        }

        /**
         * @return Array of every lock's name.
         */
        @NonNull
        public static String[] getNames() {
            LockType[] values = LockType.values();
            String[] names = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                names[i] = values[i].displayName;
            }
            return names;
        }

        @Nullable
        public static LockType valueOfName(String name) {
            for (LockType type : LockType.values()) {
                if (type.displayName.equals(name)) {
                    return type;
                }
            }
            return null;
        }
    }

    /**
     * The settings for a simple button lock. There is no additional state to remember.
     */
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

        public ButtonLockSettings() {
            super();
        }

        ButtonLockSettings(Parcel in) {
            super(in);
        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public Class<? extends LockFragment> getLockFragmentClass() {
            return ButtonLockFragment.class;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
        }
    }
}
