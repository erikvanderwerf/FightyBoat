package com.gmail.eski787.fightyboat.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Erik on 12/25/2016.
 */

public class ArrayUtil {
    public static <T extends Parcelable> void flattenArray(T[] array, Parcel dest, int flags) {
        int length = array.length;
        //dest.writeParcelableArray();
    }
}
