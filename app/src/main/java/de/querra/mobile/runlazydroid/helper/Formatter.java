package de.querra.mobile.runlazydroid.helper;

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

    public static String penaltyToKilometers(int penalty){
        return asKilometers(getPenaltyDistance(penalty));
    }

    public static float getPenaltyDistance(int penalty){
        return penalty*PreferencesHelper.getPenaltyDistance();
    }

    public static float getProgress(){
        return PreferencesHelper.getDistanceRun()/(PreferencesHelper.getWeekGoal()+getPenaltyDistance(PreferencesHelper.getPenalties()));
    }

    public static String getDaysLeft(Locale locale, LocalDate future){
        int days = future.getDayOfYear() - LocalDate.now().getDayOfYear();
        String daysLiteral = "days";
        if (locale == Locale.GERMANY || locale == Locale.GERMAN){
            daysLiteral = "Tage";
        }
        return String.format(locale, "%d %s", days, daysLiteral);
    }
}
