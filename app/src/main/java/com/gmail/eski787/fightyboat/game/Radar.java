package com.gmail.eski787.fightyboat.game;

import android.graphics.Point;
import android.support.annotation.Nullable;

import static com.gmail.eski787.fightyboat.game.Sea.SeaStatus;

/**
 * A Radar view into another players Sea. Radar tiles are either hidden or
 * revealed, allowing a view into the other player's Sea.
 */
public class Radar {
    private final Sea mSea;
    private final RadarStatus[][] mGrid;

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

    private enum RadarStatus {
        HIDDEN, REVEALED
    }
}
