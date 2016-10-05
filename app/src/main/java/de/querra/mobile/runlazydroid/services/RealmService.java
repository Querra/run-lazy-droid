package de.querra.mobile.runlazydroid.services;

public interface RealmService {

    float getWeekTargetWithPenalties();

    float getDistanceRun();

    float getTotalPenaltyDistance();

    int getProgress();

    float getDistanceLeft();

    float getAllTimeDistance();

    int getAllTimePenalties();

    float getAllTimePenaltyDistance();

    int getAllTimeRunTime();
}
