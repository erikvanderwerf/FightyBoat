package com.gmail.eski787.fightyboat.fragments;

import android.content.ClipData;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gmail.eski787.fightyboat.R;
import com.gmail.eski787.fightyboat.databinding.LayoutShelfShipTypeBinding;
import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Ship;
import com.gmail.eski787.fightyboat.presenters.PlaceShipSeaPresenter;
import com.gmail.eski787.fightyboat.presenters.SeaPresenter;
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
    private static Ship.ShipType[] shipTypes = Ship.ShipType.values();
    private PlaceShipInteraction mListener;
    private PlaceShipSeaView mSeaView;
    private RecyclerView mShipShelf;
    private PlaceShipSeaPresenter mSeaPresenter;

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
        mShipShelf = view.findViewById(R.id.place_ship_shelf);

        // List of available ahips
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mShipShelf.setHasFixedSize(true);
        mShipShelf.setLayoutManager(manager);
        mShipShelf.setAdapter(new ShipTypeAdapter());

        // Instantiate and attach models to presenters to views.
        mSeaPresenter = new PlaceShipSeaPresenter(getPlayer().getSea());
        mSeaView.setPresenter(mSeaPresenter);
        mSeaView.setClickListener(mSeaView.new PlaceShipClickListener());

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

    private class ShipTypeViewHolder extends RecyclerView.ViewHolder {
        private LayoutShelfShipTypeBinding binding;

        ShipTypeViewHolder(View itemView) {
            super(itemView);
        }

        ShipTypeViewHolder(final LayoutShelfShipTypeBinding binding) {
            this(binding.getRoot());
            this.binding = binding;

            binding.shipTypeIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getActionMasked();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            Log.d(TAG, "Touch down");
                            String type = binding.getType().name();
                            Ship dragShip = new Ship(new Point(),
                                    Ship.Orientation.HORIZONTAL, binding.getType());
                            ClipData clipData = ClipData.newPlainText("ship type", type);
                            PlaceShipSeaView.ShipDragShadow dragShadow =
                                    mSeaView.new ShipDragShadow(new SeaPresenter.ShipPresenter(dragShip));

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                view.startDragAndDrop(clipData, dragShadow, null, 0);
                            } else {
                                view.startDrag(clipData, dragShadow, null, 0);
                            }
                            return true;
                        case MotionEvent.ACTION_UP:
                            Log.d(TAG, "Touch up");
                            view.performClick();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            Log.d(TAG, "Touch move");
                            return true;
                    }
                    return false;
                }
            });
        }
    }

    private class ShipTypeAdapter extends RecyclerView.Adapter<ShipTypeViewHolder> {

        @Override
        public ShipTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final int layoutId = R.layout.layout_shelf_ship_type;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            LayoutShelfShipTypeBinding shelfShipTypeBinding = DataBindingUtil
                    .inflate(inflater, layoutId, parent, false);
            return new ShipTypeViewHolder(shelfShipTypeBinding);
        }

        @Override
        public void onBindViewHolder(ShipTypeViewHolder holder, int position) {
            final Ship.ShipType shipType = shipTypes[position];
            holder.binding.setType(shipType);

//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), String.format("Selected %s", shipType.name()),
//                            Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return shipTypes.length;
        }
    }
}
