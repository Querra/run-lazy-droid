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
        return String.format(Locale.GERMANY, "%.1f km", kilometers);
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

    public static String getDaysLeft(LocalDate future){
        int days = future.getDayOfYear() - LocalDate.now().getDayOfYear();
        Locale locale = Locale.getDefault();
        boolean single = days == 1;
        String daysLiteral = single?"day":"days";
        if (locale == Locale.GERMANY || locale == Locale.GERMAN){
            daysLiteral = single?"Tag":"Tage";
        }
        return String.format(locale, "%d %s", days, daysLiteral);
    }

    public static String inMinutes(int time) {
        Locale locale = Locale.getDefault();
        boolean single = time == 1;
        String timeLiteral = single?"minute":"minutes";
        if (locale == Locale.GERMANY || locale == Locale.GERMAN){
            timeLiteral = single?"Minute":"Minuten";
        }

        return String.format(Locale.getDefault(), "%d %s", time, timeLiteral);
    }
}
