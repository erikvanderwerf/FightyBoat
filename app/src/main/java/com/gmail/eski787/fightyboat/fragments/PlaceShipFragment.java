package com.gmail.eski787.fightyboat.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    private static String TAG = PlaceShipFragment.class.getSimpleName();
    private PlaceShipInteraction mListener;
    private PlaceShipSeaView mSeaView;

    public PlaceShipFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_ship, container, false);
        Button button = view.findViewById(R.id.b_place_ship_continue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onShipPlaceComplete(getPlayer());
            }
        });

        // Get references to View elements.
        mSeaView = view.findViewById(R.id.place_ship_sea_view);
        final ImageView[] shelves = new ImageView[5];
        shelves[0] = view.findViewById(R.id.ship_shelf_aircraft_carrier);
        shelves[1] = view.findViewById(R.id.ship_shelf_battleship);
        shelves[2] = view.findViewById(R.id.ship_shelf_submarine);
        shelves[3] = view.findViewById(R.id.ship_shelf_destroyer);
        shelves[4] = view.findViewById(R.id.ship_shelf_cruiser);


        // Instantiate and attach models to presenters to views.
        final PlaceShipSeaPresenter seaPresenter = new PlaceShipSeaPresenter();
        seaPresenter.setSea(getPlayer().getSea());
        mSeaView.setSeaPresenter(seaPresenter);
        mSeaView.setClickListener(mSeaView.new PlaceShipClickListener());
        // TODO: Toast with summary, implement drag-and-drop from shelf to mSeaView.
        for (ImageView imageView : shelves) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Selected", Toast.LENGTH_SHORT).show();
                }
            });
        }

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
    public interface PlaceShipInteraction extends PlayerFragmentInteraction {
        void onShipPlaceComplete(Player player);
    }
}
