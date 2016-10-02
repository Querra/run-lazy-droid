package de.querra.mobile.runlazydroid.helper;

import android.content.Context;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.querra.mobile.runlazydroid.R;

public class Formatter {
    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date);
    }

    public static String asKilometers(float km){
        return String.format(Locale.getDefault(), "%.1f km", km);
    }

    public static String getDaysLeft(LocalDate future, Context context){
        int days = future.getDayOfYear() - LocalDate.now().getDayOfYear();
        Locale locale = Locale.getDefault();
        boolean single = days == 1;
        String daysLiteral = single ? context.getString(R.string.day) : context.getString(R.string.days);
        return String.format(locale, "%d %s", days, daysLiteral);
    }

    public static String inMinutes(int time, Context context) {
        boolean single = time == 1;
        String timeLiteral = single?context.getString(R.string.minute):context.getString(R.string.minutes);
        return String.format(Locale.getDefault(), "%d %s", time, timeLiteral);
    }

    public static String getFileName(long id){
        return String.format("map%s.jpg", String.valueOf(id));
    }

    public static String millisToTimeString(long millis){
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}
