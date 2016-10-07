package de.querra.mobile.runlazydroid.helper;

import android.content.Context;

import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;

public class Formatter {

    @Inject
    Context context;

    public Formatter(){
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    public String dateToString(Date date){
        DateFormat formatter = DateFormat.getDateInstance();
        return formatter.format(date);
    }

    public String asKilometers(float km){
        return String.format(Locale.getDefault(), "%.1f km", km);
    }

    public String getDaysLeft(LocalDate future){
        int days = future.getDayOfYear() - LocalDate.now().getDayOfYear();
        Locale locale = Locale.getDefault();
        boolean single = days == 1;
        String daysLiteral = single ? this.context.getString(R.string.day) : this.context.getString(R.string.days);
        return String.format(locale, "%d %s", days, daysLiteral);
    }

    public String inMinutes(int time) {
        boolean single = time == 1;
        String timeLiteral = single?this.context.getString(R.string.minute):this.context.getString(R.string.minutes);
        return String.format(Locale.getDefault(), "%d %s", time, timeLiteral);
    }

    public String inMinutesShort(int time) {
        String timeLiteral = this.context.getString(R.string.min);
        return String.format(Locale.getDefault(), "%d %s", time, timeLiteral);
    }

    public String asMinutesFromAverage(int time){
        return String.format("%s %s", inMinutesShort(time), this.context.getString(R.string.estimate_from_average));
    }

    public String getFileName(long id){
        return String.format("map%s.jpg", String.valueOf(id));
    }

    public String millisToTimeString(long millis){
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

    public String minutesToTimeString(long minutes){
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MINUTES.toHours(minutes),
                TimeUnit.MINUTES.toMinutes(minutes) % TimeUnit.HOURS.toMinutes(1));
    }

    public String averageAsKmPerHour(float averageSpeed) {
        return String.format(Locale.getDefault(), "%.1f km/h", (averageSpeed*60));
    }
}
