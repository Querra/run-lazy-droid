package de.querra.mobile.runlazydroid.services;


import android.content.SharedPreferences;

import java.util.Date;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.RunLazyDroidApplication;

public class PreferencesServiceImplementation implements PreferencesService{

    @Inject
    SharedPreferences sharedPreferences;

    public static final String START_DATE = "preference__start_date";
    public static final String WEEK_GOAL = "preference__week_goal";
    public static final String PENALTY_DISTANCE = "preference__penalty_distance";
    private static final String DEFAULTS_SET = "defaults_set";

    public PreferencesServiceImplementation(){
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    @Override
    public Date getStartDate() {
        long millis = sharedPreferences.getLong(START_DATE, 0);
        return new Date(millis);
    }

    @Override
    public void setStartDate(Date date) {
        this.sharedPreferences.edit().putLong(START_DATE, date.getTime()).apply();
    }

    @Override
    public float getWeekTarget() {
        return this.sharedPreferences.getFloat(WEEK_GOAL, 0f);
    }

    @Override
    public void setWeekTarget(float weekGoal) {
        this.sharedPreferences.edit().putFloat(WEEK_GOAL, weekGoal).apply();
    }

    @Override
    public float getPenaltyDistance() {
        return this.sharedPreferences.getFloat(PENALTY_DISTANCE, 0f);
    }

    @Override
    public void setPenaltyDistance(float penaltyDistance) {
        this.sharedPreferences.edit().putFloat(PENALTY_DISTANCE, penaltyDistance).apply();
    }

    @Override
    public boolean areDefaultsSet() {
        return this.sharedPreferences.getBoolean(DEFAULTS_SET, false);
    }

    @Override
    public void setDefaultValues(boolean override) {
        if (!areDefaultsSet() || override) {
            setStartDate(new Date());
            setWeekTarget(10f);
            setPenaltyDistance(5f);
        }
        this.sharedPreferences.edit().putBoolean(DEFAULTS_SET, true).apply();
    }
}
