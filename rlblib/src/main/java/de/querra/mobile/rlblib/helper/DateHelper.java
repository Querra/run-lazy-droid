package de.querra.mobile.rlblib.helper;

import org.joda.time.LocalDate;

public class DateHelper {
    public static LocalDate getLastSunday(){
        LocalDate today = LocalDate.now();
        LocalDate startOfToday = today.toDateTimeAtStartOfDay().toLocalDate();
        int dayOfWeek = today.getDayOfWeek();
        if(dayOfWeek == 7){
            return startOfToday;
        }
        return startOfToday.minusDays(dayOfWeek);
    }

    public static LocalDate getNextSunday(){
        return getLastSunday().plusDays(7);
    }
}
