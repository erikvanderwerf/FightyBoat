package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import static com.gmail.eski787.fightyboat.game.Sea.SeaStatus;

/**
 * A Radar view into another players Sea. Radar tiles are either hidden or
 * revealed, allowing a view into the other player's Sea.
 */
@Deprecated
public class Radar implements Parcelable {
    public static final Creator<Radar> CREATOR = new Creator<Radar>() {
        @Override
        public Radar createFromParcel(Parcel in) {
            return new Radar(in);
        }

        @Override
        public Radar[] newArray(int size) {
            return new Radar[size];
        }
    };
    private final Sea mSea;
    private RadarStatus[][] mGrid;

    public Radar(Player player) {
        mSea = player.getSea();

        final int rows = mSea.getNumberOfRows();
        final int cols = mSea.getNumberOfColumns();
        mGrid = new RadarStatus[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                mGrid[y][x] = RadarStatus.HIDDEN;
            }
        }
    }

    private Radar(Parcel in) {
        mSea = in.readParcelable(Sea.class.getClassLoader());
    }

    /**
     * Returns the status if revealed, or null if the radar has not revealed
     * that tile.
     *
     * @param tile Coordinate of tile to get
     * @return SeaStatus or null
     */
    @Nullable
    public SeaStatus getSeaStatus(Point tile) {
        if (mGrid[tile.y][tile.x] == RadarStatus.REVEALED) {
            return mSea.getStatus(tile);
        } else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mSea, flags);
    }

    private enum RadarStatus {
        HIDDEN, REVEALED
    }
}
