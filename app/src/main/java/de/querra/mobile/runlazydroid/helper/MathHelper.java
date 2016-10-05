package de.querra.mobile.runlazydroid.helper;

import java.math.BigDecimal;

import de.querra.mobile.runlazydroid.RunLazyDroidApplication;

public class MathHelper {

    public MathHelper(){
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public int getDifferenceInMinutes(long millisStart, long millisEnd){
        return getMinutesFromMillis(millisEnd - millisStart);
    }

    private int getMinutesFromMillis(long millis){
        return (int) ((float)millis/60000f);
    }
}
