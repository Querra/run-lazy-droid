package de.querra.mobile.runlazydroid.services.internal;

import java.util.Date;

public interface PreferencesService {
    Date getStartDate();

    void setStartDate(Date date);

    float getWeekTarget();

    void setWeekTarget(float weekGoal);

    float getPenaltyDistance();

    void setPenaltyDistance(float penaltyDistance);

    float getIncrementDistance();

    void setIncrementDistance(float incrementDistance);

    boolean areDefaultsSet();

    void setDefaultValues(boolean override);
}
