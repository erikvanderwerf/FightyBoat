package com.gmail.eski787.fightyboat.presenters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.gmail.eski787.fightyboat.views.SeaView.SHIP_RADIUS;

/**
 * Concrete implementations of {@link ShipCap} will implement draw calls for the end of a
 * {@link com.gmail.eski787.fightyboat.game.Ship} in the style specified.
 * Created by Erik on 12/29/2016.
 */

public abstract class ShipCap {
    private static final String TAG = ShipCap.class.getSimpleName();

    CapDirection mDirection;

    ShipCap(CapDirection direction) {
        super();
        mDirection = direction;
    }

    /**
     * @param type      The cap type to return.
     * @param direction The direction for the cap to open.
     * @return An instance of a {@link ShipCap} with specified parameters.
     */
    @NonNull
    public static ShipCap getCap(CapType type, CapDirection direction) {
        ShipCap cap = null;

        switch (type) {
            case POINT:
                return new PointShipCap(direction);
            case SQUARE:
                return new SquareShipCap(direction);
            case ROUND:
                return new RoundShipCap(direction);
        }
        throw new RuntimeException("Invalid CapType: " + type.toString());
    }

    private void setDirection(CapDirection direction) {
        mDirection = direction;
    }

    /**
     * Draw the ship cap on the canvas with this given paint.
     *
     * @param canvas Canvas to draw on.
     * @param paint  Paint to use.
     * @param startX Left edge.
     * @param startY Top edge.
     * @param endX   Right edge.
     * @param endY   Bottom edge.
     */
    public abstract void drawCap(Canvas canvas, Paint paint, int startX, int startY, int endX, int endY);

    /**
     * Determines which tile edge the {@link com.gmail.eski787.fightyboat.game.Ship} continues on to.
     */
    public enum CapDirection {
        LEFT, RIGHT, UP, DOWN
    }

    /**
     * Available styles for {@link com.gmail.eski787.fightyboat.game.Ship} end caps.
     */
    public enum CapType {
        POINT, SQUARE, ROUND
    }

    private static class RoundShipCap extends ShipCap {
        RoundShipCap(CapDirection direction) {
            super(direction);
        }

        @Override
        public void drawCap(Canvas canvas, Paint paint, int startX, int startY, int endX, int endY) {
            final int width = endX - startX;
            final int height = endY - startY;

            final int midX = startX + (width / 2);
            final int midY = startY + (height / 2);
            final int minDimension = Math.min(width, height);
            final float radius = minDimension * SHIP_RADIUS;

            float minX, maxX, minY, maxY;
            switch (mDirection) {
                case UP:
                    maxX = midX + radius;
                    minX = midX - radius;
                    maxY = midY;
                    minY = startY;
                    break;
                case DOWN:
                    maxX = midX + radius;
                    minX = midX - radius;
                    maxY = endY;
                    minY = midY;
                    break;
                case LEFT:
                    maxX = midX;
                    minX = startX;
                    maxY = midY + radius;
                    minY = midY - radius;
                    break;
                case RIGHT:
                    maxX = endX;
                    minX = midX;
                    maxY = midY + radius;
                    minY = midY - radius;
                    break;
                default:
                    throw new RuntimeException(String.format("Direction is an invalid state. %s", mDirection));
            }

            canvas.drawCircle(midX, midY, radius, paint);
            canvas.drawRect(minX, minY, maxX, maxY, paint);
        }
    }

    private static class PointShipCap extends ShipCap {
        PointShipCap(CapDirection direction) {
            super(direction);
        }

        @Override
        public void drawCap(Canvas canvas, Paint paint, int startX, int startY, int endX, int endY) {
            Log.d(TAG, "PointShipCap not implemented.");

            final int width = endX - startX;
            final int height = endY - startY;
            final float xOffset = width * (1 - 2 * SHIP_RADIUS) / 2;
            final float yOffset = height * (1 - 2 * SHIP_RADIUS) / 2;
            final float avgX = (startX + endX) / 2;
            final float avgY = (startY + endY) / 2;

            float firstX, firstY, midX, midY, lastX, lastY, ctrlX1, ctrlY1, ctrlX2, ctrlY2;
            switch (mDirection) {
                case DOWN:
                    firstX = ctrlX1 = startX + xOffset;
                    lastX = ctrlX2 = endX - xOffset;
                    firstY = lastY = endY;
                    midY = startY + yOffset;
                    midX = avgX;
                    ctrlY1 = ctrlY2 = avgY;
                    break;
                case LEFT:
                    firstY = ctrlY1 = startY + yOffset;
                    lastY = ctrlY2 = endY - yOffset;
                    firstX = lastX = startX;
                    midX = endX - xOffset;
                    midY = avgY;
                    ctrlX1 = ctrlX2 = avgX;
                    break;
                case RIGHT:
                    firstY = ctrlY1 = startY + yOffset;
                    lastY = ctrlY2 = endY - yOffset;
                    firstX = lastX = endX;
                    midX = startX + xOffset;
                    midY = avgY;
                    ctrlX1 = ctrlX2 = avgX;
                    break;
                case UP:
                    firstX = ctrlX1 = startX + xOffset;
                    lastX = ctrlX2 = endX - xOffset;
                    firstY = lastY = startY;
                    midY = endY - yOffset;
                    midX = avgX;
                    ctrlY1 = ctrlY2 = avgY;
                    break;
                default:
                    throw new RuntimeException(String.format("Direction is an invalid state. %s", mDirection));
            }

            // So much preprocessing to get here.
            final Path arc = new Path();
            arc.moveTo(firstX, firstY);
            arc.quadTo(ctrlX1, ctrlY1, midX, midY);
            arc.quadTo(ctrlX2, ctrlY2, lastX, lastY);

            canvas.drawPath(arc, paint);
        }
    }

    private static class SquareShipCap extends ShipCap {
        SquareShipCap(CapDirection direction) {
            super(direction);
        }

        @Override
        public void drawCap(Canvas canvas, Paint paint, int startX, int startY, int endX, int endY) {
            final int width = endX - startX;
            final int height = endY - startY;

            final int midX = startX + (width / 2);
            final int midY = startY + (height / 2);
            final int minDimension = Math.min(width, height);
            final int radius = (int) (minDimension * SHIP_RADIUS);

            int minX, maxX, minY, maxY;

            minX = midX - radius;
            maxX = midX + radius;
            minY = midY - radius;
            maxY = midY + radius;

            switch (mDirection) {
                case UP:
                    minY = startY;
                    break;
                case DOWN:
                    maxY = endY;
                    break;
                case LEFT:
                    minX = startX;
                    break;
                case RIGHT:
                    maxX = endX;
                    break;
            }

            canvas.drawRect(minX, minY, maxX, maxY, paint);
        }
    }
}
