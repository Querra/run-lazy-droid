package de.querra.mobile.runlazydroid.services.internal;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import de.querra.mobile.runlazydroid.R;

public class PreferencesService {

    public static final String SAVED_PREFERENCES = "run_lazy_droid_saved_preferences";
    private static PreferencesService INSTANCE;

    private PreferencesService() {
        // no external instantiation of singleton
    }

    public Date getStartDate(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        long millis = sharedPreferences.getLong(context.getString(R.string.preference__start_date), 0);
        return new Date(millis);
    }

    public void setStartDate(Context context, Date date) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putLong(context.getString(R.string.preference__start_date), date.getTime()).apply();
    }

    public float getWeekTarget(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return (float) sharedPreferences.getInt(context.getString(R.string.preference__week_target), 10);
    }
    public void setWeekTarget(Context context, float weekGoal) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putInt(context.getString(R.string.preference__week_target), Math.round(weekGoal)).apply();
    }

    public float getPenaltyDistance(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return (float) sharedPreferences.getInt(context.getString(R.string.preference__penalty_distance), 5);
    }

    public void setPenaltyDistance(Context context, float penaltyDistance) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putInt(context.getString(R.string.preference__penalty_distance), Math.round(penaltyDistance)).apply();
    }

    public boolean autoIncrementDistance(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.preference__auto_increment_distance), true);
    }

    public void setAutoIncrementDistance(Context context, boolean autoIncrement) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putBoolean(context.getString(R.string.preference__auto_increment_distance), autoIncrement).apply();
    }

    public float getIncrementDistance(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return (float) sharedPreferences.getInt(context.getString(R.string.preference__increment_distance), 1);
    }

    public void setIncrementDistance(Context context, float incrementDistance) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putInt(context.getString(R.string.preference__increment_distance), Math.round(incrementDistance)).apply();
    }

    public boolean areDefaultsSet(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(context.getString(R.string.preference__defaults_set), false);
    }

    public void setDefaultValues(Context context, boolean override) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if (!areDefaultsSet(context) || override) {
            setStartDate(context, new Date());
            setWeekTarget(context, 10);
            setPenaltyDistance(context, 5);
            setIncrementDistance(context, 1);
            setAutoIncrementDistance(context, true);
        }
        sharedPreferences.edit().putBoolean(context.getString(R.string.preference__defaults_set), true).apply();
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static PreferencesService getInstance(){
        if (INSTANCE == null){
            synchronized (PreferencesService.class){
                if (INSTANCE == null){
                    INSTANCE = new PreferencesService();
                }
            }
        }
        return INSTANCE;
    }
}