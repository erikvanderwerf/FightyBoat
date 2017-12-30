package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.presenters.PlayGameSeaPresenter;
import com.gmail.eski787.fightyboat.views.RadarView;
import com.gmail.eski787.fightyboat.views.SeaView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayGameInteraction} interface
 * to handle interaction events.
 * Use the {@link PlayGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayGameFragment extends PlayerFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private PlayGameInteraction mListener;
    private SeaView<PlayGameSeaPresenter> mSeaView;
    private RadarView mRadarView;

    public PlayGameFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_game, container, false);
        mRadarView = view.findViewById(R.id.play_radar_view);
        mSeaView = view.findViewById(R.id.play_sea_view);


        PlayGameSeaPresenter presenter = new PlayGameSeaPresenter();
        presenter.setSea(mPlayer.getSea());
        mSeaView.setSeaPresenter(presenter);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onButtonClick(View view) {
        return false;
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
    public interface PlayGameInteraction extends PlayerFragmentInteraction {
        // TODO: Update argument type and name
        Player[] getPlayers();
    }
}
