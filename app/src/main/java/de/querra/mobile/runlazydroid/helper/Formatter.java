package de.querra.mobile.runlazydroid.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import org.joda.time.LocalDate;

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

    public static String penaltyToKilometers(@NonNull Context context, int penalty){
        return asKilometers(getPenaltyDistance(context, penalty));
    }

    public static float getPenaltyDistance(@NonNull Context context, int penalty){
        return penalty*PreferencesHelper.getPenaltyDistance(context);
    }

    public static float getProgress(@NonNull Context context){
        return PreferencesHelper.getDistanceRun(context)/(PreferencesHelper.getWeekGoal(context)+getPenaltyDistance(context, PreferencesHelper.getPenalties(context)));
    }

    public static String getDaysLeft(Locale locale, LocalDate future){
        int days = future.getDayOfYear() - LocalDate.now().getDayOfYear();
        String daysLiteral = "days";
        if (locale == Locale.GERMANY || locale == Locale.GERMAN){
            daysLiteral = "Tage";
        }
        return String.format(locale, "%d %s", days, daysLiteral);
    }

    public static String inMinutes(int time) {
        return String.format(Locale.getDefault(), "%d Minuten", time);
    }
}
