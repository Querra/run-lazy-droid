package de.querra.mobile.runlazydroid.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

    public static String asKilometers(float kilometers){
        return String.format(Locale.GERMANY, "%.2f km", kilometers);
    }

    public static String penaltyToKilometers(int penaltiy){
        return asKilometers(penaltiy*PreferencesHelper.getPenaltyDistance());
    }
}
