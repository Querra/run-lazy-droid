package de.querra.mobile.runlazydroid.helper;

import org.joda.time.LocalDate;

public class DateHelper {
    public static LocalDate getLastSunday(){
        LocalDate today = LocalDate.now();
        LocalDate startOfToday = today.toDateTimeAtStartOfDay().toLocalDate();
        return startOfToday.minusDays(today.getDayOfWeek());
    }

    public static LocalDate getNextSunday(){
        return getLastSunday().plusDays(7);
    }
}
