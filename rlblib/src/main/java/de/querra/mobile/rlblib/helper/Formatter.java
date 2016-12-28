package de.querra.mobile.rlblib.helper;

import android.content.Context;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.querra.mobile.rlblib.R;


public class Formatter {

    public static String dateToString(Date date) {
        DateFormat formatter = DateFormat.getDateInstance();
        return formatter.format(date);
    }

    public static String asKilometers(float km) {
        return String.format(Locale.getDefault(), "%.1f km", km);
    }

    public static String getDaysLeft(Context context, LocalDate future) {
        int days = Days.daysBetween(LocalDate.now(), future).getDays();
        Locale locale = Locale.getDefault();
        boolean single = days == 1;
        String daysLiteral = single ? context.getString(R.string.day) : context.getString(R.string.days);
        return String.format(locale, "%d %s", days, daysLiteral);
    }

    public static String inMinutes(Context context, int time) {
        boolean single = time == 1;
        String timeLiteral = single ? context.getString(R.string.minute) : context.getString(R.string.minutes);
        return String.format(Locale.getDefault(), "%d %s", time, timeLiteral);
    }

    public static String inMinutesShort(Context context, int time) {
        String timeLiteral = context.getString(R.string.min);
        return String.format(Locale.getDefault(), "%d %s", time, timeLiteral);
    }

    public static String asMinutesFromAverage(Context context, int time) {
        return String.format("%s %s", inMinutesShort(context, time), context.getString(R.string.estimate_from_average));
    }

    public static String getFileName(long id) {
        return String.format("map%s.jpg", String.valueOf(id));
    }

    public static String millisToTimeString(long millis) {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    public static String minutesToTimeString(long minutes) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MINUTES.toHours(minutes),
                TimeUnit.MINUTES.toMinutes(minutes) % TimeUnit.HOURS.toMinutes(1));
    }

    public static String averageAsKmPerHour(float averageSpeed) {
        return String.format(Locale.getDefault(), "%.1f km/h", (averageSpeed * 60));
    }
}
