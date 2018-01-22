package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.presenters.GamePresenter;
import com.gmail.eski787.fightyboat.presenters.PlayGameRadarPresenter;
import com.gmail.eski787.fightyboat.presenters.SeaPresenter;
import com.gmail.eski787.fightyboat.views.PlayGameRadarView;
import com.gmail.eski787.fightyboat.views.SeaView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayGameInteraction} interface
 * to handle interaction events.
 */
public class PlayGameFragment extends Fragment {
    public static final String TAG = PlayGameFragment.class.getSimpleName();
    private PlayGameInteraction mListener;
    private SeaView<SeaPresenter> mSeaView;
    private PlayGameRadarView mRadarView;
    private Button mPlayButton;

    public PlayGameFragment() { /* Required empty public constructor */ }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlayGameInteraction) {
            mListener = (PlayGameInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PlayGameInteraction");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_game, container, false);
        mRadarView = view.findViewById(R.id.play_radar_view);
        mSeaView = view.findViewById(R.id.play_sea_view);
        mPlayButton = view.findViewById(R.id.play_button);

        GamePresenter presenter = mListener.getGame();

        mSeaView.setPresenter(presenter.getPlayerSeaPresenter());

        List<PlayGameRadarPresenter> radarPresenters = presenter.getOpponentRadarPresenters();
        mRadarView.setPresenter(radarPresenters.get(0));
        mRadarView.setDefaultListeners();
        mRadarView.setCoordinateClickListener(new SeaView.CoordinateClickListener() {
            @Override
            public boolean onCoordinateClick(PointF coordinate) {
                Log.d(TAG, String.format("User Click: %s", coordinate));
                presenter.onUserClick(coordinate);
                return true;
            }

            @Override
            public boolean onCoordinateLongClick(PointF coordinate) {
                return false;
            }
        });

        mPlayButton.setOnClickListener(v -> presenter.onPlayButtonClick());
        presenter.registerPlayButtonListener((text, enabled) -> {
            mPlayButton.setText(text);
            mPlayButton.setEnabled(enabled);
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface PlayGameInteraction {
        GamePresenter getGame();
    }
}
