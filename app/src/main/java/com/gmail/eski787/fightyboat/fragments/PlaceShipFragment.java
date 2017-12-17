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
import com.gmail.eski787.fightyboat.presenters.PlaceShipSeaPresenter;
import com.gmail.eski787.fightyboat.views.PlaceShipSeaView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlaceShipInteraction} interface to handle interaction events.
 * Use the newInstance factory method to
 * create an instance of this fragment.
 */
public class PlaceShipFragment extends PlayerFragment {
    private PlaceShipInteraction mListener;
    private PlaceShipSeaView mSeaView;

    public PlaceShipFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param player Player to use for this Fragment.
     * @return A new instance of fragment PlaceShipFragment.
     */
    public static PlaceShipFragment newInstance(Player player) {
        PlaceShipFragment fragment = new PlaceShipFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_ship, container, false);
        Button button = view.findViewById(R.id.b_place_ship_continue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onShipPlaceComplete(mPlayer);
            }
        });

        final PlaceShipSeaPresenter seaPresenter = new PlaceShipSeaPresenter();
        seaPresenter.setSea(mPlayer.getSea());

        mSeaView = view.findViewById(R.id.place_ship_sea_view);
        mSeaView.setSeaPresenter(seaPresenter);

        // Set click listener.
        mSeaView.setClickListener(mSeaView.new PlaceShipClickListener());

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
        void onShipPlaceComplete(Player player);
    }
}
