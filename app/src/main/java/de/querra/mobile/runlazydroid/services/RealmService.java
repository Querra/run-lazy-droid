package de.querra.mobile.runlazydroid.services;

import de.querra.mobile.runlazydroid.data.entities.Target;

public interface RealmService {

    float getWeekTargetWithPenalties();

    float getDistanceRun();

    float getTotalPenaltyDistance();

    int getProgress();

    float getDistanceLeft();

    Target getLastTarget();

    float getAllTimeDistance();

    int getAllTimePenalties();

    float getAllTimePenaltyDistance();

    int getAllTimeRunTime();

    boolean targetNeedsUpdate();

    boolean newTargetNeedsCopy();

    int getAllTimeTargetsAchieved();
}
