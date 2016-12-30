package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.views.SeaView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlaceShipInteraction} interface
 * to handle interaction events.
 * Use the {@link PlaceShipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceShipFragment extends Fragment {
    private static final String ARG_PLAYER = "arg_player";

    private PlaceShipInteraction mListener;
    private Player mPlayer;
    private SeaView mSeaView;

    public PlaceShipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlaceShipFragment.
     * @param player Player to place ships for.
     */
    public static PlaceShipFragment newInstance(Player player) {
        PlaceShipFragment fragment = new PlaceShipFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPlayer = getArguments().getParcelable(ARG_PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_ship, container, false);
        Button button = (Button) view.findViewById(R.id.b_place_ship_continue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onComplete(mPlayer);
            }
        });

        mSeaView = (SeaView) view.findViewById(R.id.placeShipSeaView);
        mSeaView.setSea(mPlayer.getSea());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PlaceShipInteraction) {
            mListener = (PlaceShipInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PlaceShipInteraction");
        }
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
    public interface PlaceShipInteraction {
        void onComplete(Player player);
    }
}
