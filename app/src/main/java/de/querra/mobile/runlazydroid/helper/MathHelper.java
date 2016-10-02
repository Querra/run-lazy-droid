package de.querra.mobile.runlazydroid.helper;

import java.math.BigDecimal;

public class MathHelper {
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static int getDifferenceInMinutes(long millisStart, long millisEnd){
        return getMinutesFromMillis(millisEnd - millisStart);
    }

    public static int getMinutesFromMillis(long millis){
        return (int) ((float)millis/60000f);
    }
}
