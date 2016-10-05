package de.querra.mobile.runlazydroid.services;

import java.util.Date;

public interface PreferencesService {
    Date getStartDate();

    void setStartDate(Date date);

    float getWeekTarget();

    void setWeekTarget(float weekGoal);

    float getPenaltyDistance();

    void setPenaltyDistance(float penaltyDistance);

    boolean areDefaultsSet();

    void setDefaultValues(boolean override);
}
