package com.gmail.eski787.fightyboat.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.gmail.eski787.fightyboat.game.Player;
import com.gmail.eski787.fightyboat.game.Sea;

/**
 * Created by Erik on 12/23/2016.
 */

public class SeaView extends View {
    private Sea mSea;
    private Paint paint = new Paint();
    private int mHeight;
    private int mWidth;

    public SeaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SeaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SeaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int numCols = 10;
        final int numRows = 10;

        final int height = mHeight / numRows;
        final int width = mWidth / numCols;

        paint.setColor(Color.GRAY);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int startX = col * width;
                int startY = row * height;
                drawTile(canvas, startX, startY, width, height, paint);
            }
        }
    }

    public void setSea(Sea sea) {
        mSea = sea;
    }

    private static void drawTile(Canvas canvas, int startX, int startY, int width, int height, Paint paint) {
        int minDimension = Math.min(width, height);

        int midX = startX + (width / 2);
        int midY = startY + (height / 2);

        canvas.drawCircle(midX, midY, minDimension / 2, paint);
    }

    private static class SeaTile {

    }
}
