package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.presenters.GamePresenter;
import com.gmail.eski787.fightyboat.presenters.PlayGameRadarPresenter;
import com.gmail.eski787.fightyboat.presenters.PlayGameSeaPresenter;
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
    private PlayGameInteraction mListener;
    private SeaView<PlayGameSeaPresenter> mSeaView;
    private PlayGameRadarView mRadarView;
    private Button play_button;

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
        play_button = view.findViewById(R.id.play_button);


        GamePresenter presenter = mListener.getGame();
        PlayGameSeaPresenter seaPresenter = new PlayGameSeaPresenter(presenter.getCurrentPlayer().getSea());
        mSeaView.setPresenter(seaPresenter);

        List<PlayGameRadarPresenter> radarPresenters = presenter.getOpponentRadars();

        mRadarView.setPresenter(radarPresenters.get(0));
        mRadarView.setClickListener(mRadarView.new ClickListener());

        play_button.setOnClickListener(v -> mListener.onPlayButtonClick());

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
