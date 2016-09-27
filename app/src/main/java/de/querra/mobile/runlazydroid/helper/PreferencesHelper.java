package de.querra.mobile.runlazydroid.helper;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import de.querra.mobile.runlazydroid.RunLazyDroidApplication;

public class PreferencesHelper {

    public static final String DISTANCE_RUN = "distance_run";
    public static final String PENALTIES = "penalties";
    public static final String START_DATE = "start_date";
    public static final String WEEK_GOAL = "week_goal";
    public static final String PENALTY_DISTANCE = "penalty_distance";

    private static final String SAVED_PREFERENCES = "run_lazy_droid_saved_preferences";
    private static final String DEFAULTS_SET = "defaults_set";

    public static float getDistanceRun(){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return 0f;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(DISTANCE_RUN, 0f);
    }

    public static void setDistanceRun(float distanceRun){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(DISTANCE_RUN, distanceRun).apply();
    }

    public static int getPenalties(){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return 0;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PENALTIES, 0);
    }

    public static void setPenalties(int penalties){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(PENALTIES, penalties).apply();
    }

    public static Date getStartDate(){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        long millis = sharedPreferences.getLong(START_DATE, 0);
        return new Date(millis);
    }

    public static void setStartDate(Date date){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(START_DATE, date.getTime()).apply();
    }

    public static float getWeekGoal(){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return 0f;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(WEEK_GOAL, 0f);
    }

    public static void setWeekGoal(float weekGoal){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(WEEK_GOAL, weekGoal).apply();
    }

    public static float getPenaltyDistance() {
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return 0f;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(PENALTY_DISTANCE, 0f);
    }

    public static void setPenaltyDistance(float penaltyDistance){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(PENALTY_DISTANCE, penaltyDistance).apply();
    }

    private static boolean areDefaultsSet(){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(DEFAULTS_SET, false);
    }

    public static void setDefaultValues(boolean override){
        Context context = RunLazyDroidApplication.getContext();
        if (context == null){
            return;
        }
        if (!areDefaultsSet() || override) {
            setDistanceRun(0f);
            setPenalties(0);
            setStartDate(new Date());
            setWeekGoal(10f);
            setPenaltyDistance(5f);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(DEFAULTS_SET, true).apply();
    }
}
