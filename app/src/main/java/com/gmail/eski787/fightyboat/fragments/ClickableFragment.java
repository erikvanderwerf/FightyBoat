package com.gmail.eski787.fightyboat.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * This Fragment defines a single abstract method for receiving click events if the user
 * presses a button on the fragment.
 */

public abstract class ClickableFragment extends Fragment {
    /**
     * Called by the containing {@link android.support.v7.app.AppCompatActivity} whenever the user
     * generates a click event.
     *
     * @param view The view that was clicked.
     * @return true if the click was handled, false otherwise.
     */
    public abstract boolean onButtonClick(View view);
}
