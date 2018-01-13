package com.gmail.eski787.fightyboat.util;

import android.graphics.Point;
import android.graphics.PointF;
import android.support.annotation.NonNull;

/**
 * Created by Erik on 1/10/2018.
 */

public class Util {
    @NonNull
    public static Point intCoordinate(@NonNull PointF coordinate) {
        return new Point(((int) coordinate.x), ((int) coordinate.y));
    }
}
