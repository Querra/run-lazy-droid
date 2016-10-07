package de.querra.mobile.runlazydroid.services.internal;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import javax.inject.Inject;

import de.querra.mobile.runlazydroid.R;
import de.querra.mobile.runlazydroid.RunLazyDroidApplication;

public class PreferencesServiceImplementation implements PreferencesService {

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Context context;

    public PreferencesServiceImplementation() {
        RunLazyDroidApplication.getAppComponent().inject(this);
    }

    @Override
    public Date getStartDate() {
        long millis = sharedPreferences.getLong(this.context.getString(R.string.preference__start_date), 0);
        return new Date(millis);
    }

    @Override
    public void setStartDate(Date date) {
        this.sharedPreferences.edit().putLong(this.context.getString(R.string.preference__start_date), date.getTime()).apply();
    }

    @Override
    public float getWeekTarget() {
        return (float) this.sharedPreferences.getInt(this.context.getString(R.string.preference__week_target), 10);
    }

    @Override
    public void setWeekTarget(float weekGoal) {
        this.sharedPreferences.edit().putInt(this.context.getString(R.string.preference__week_target), Math.round(weekGoal)).apply();
    }

    @Override
    public float getPenaltyDistance() {
        return (float) this.sharedPreferences.getInt(this.context.getString(R.string.preference__penalty_distance), 5);
    }

    @Override
    public void setPenaltyDistance(float penaltyDistance) {
        this.sharedPreferences.edit().putInt(this.context.getString(R.string.preference__penalty_distance), Math.round(penaltyDistance)).apply();
    }

    @Override
    public boolean autoIncrementDistance() {
        return this.sharedPreferences.getBoolean(this.context.getString(R.string.preference__auto_increment_distance), true);
    }

    @Override
    public void setAutoIncrementDistance(boolean autoIncrement) {
        this.sharedPreferences.edit().putBoolean(this.context.getString(R.string.preference__auto_increment_distance), autoIncrement).apply();
    }

    @Override
    public float getIncrementDistance() {
        return (float) this.sharedPreferences.getInt(this.context.getString(R.string.preference__increment_distance), 1);
    }

    @Override
    public void setIncrementDistance(float incrementDistance) {
        this.sharedPreferences.edit().putInt(this.context.getString(R.string.preference__increment_distance), Math.round(incrementDistance)).apply();
    }

    @Override
    public boolean areDefaultsSet() {
        return this.sharedPreferences.getBoolean(this.context.getString(R.string.preference__defaults_set), false);
    }

    @Override
    public void setDefaultValues(boolean override) {
        if (!areDefaultsSet() || override) {
            setStartDate(new Date());
            setWeekTarget(10);
            setPenaltyDistance(5);
            setIncrementDistance(1);
            setAutoIncrementDistance(true);
        }
        this.sharedPreferences.edit().putBoolean(this.context.getString(R.string.preference__defaults_set), true).apply();
    }
}
