package de.querra.mobile.runlazydroid.helper;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Date;

public class PreferencesHelper {

    public static final String DISTANCE_RUN = "distance_run";
    public static final String PENALTIES = "penalties";
    public static final String START_DATE = "start_date";
    public static final String WEEK_GOAL = "week_goal";
    public static final String PENALTY_DISTANCE = "penalty_distance";

    private static final String SAVED_PREFERENCES = "run_lazy_droid_saved_preferences";
    private static final String DEFAULTS_SET = "defaults_set";

    public static float getDistanceRun(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(DISTANCE_RUN, 0f);
    }

    public static void setDistanceRun(@NonNull Context context, float distanceRun){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(DISTANCE_RUN, distanceRun).apply();
    }

    public static int getPenalties(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(PENALTIES, 0);
    }

    public static void setPenalties(@NonNull Context context, int penalties){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(PENALTIES, penalties).apply();
    }

    public static Date getStartDate(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        long millis = sharedPreferences.getLong(START_DATE, 0);
        return new Date(millis);
    }

    public static void setStartDate(@NonNull Context context, Date date){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(START_DATE, date.getTime()).apply();
    }

    public static float getWeekGoal(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(WEEK_GOAL, 0f);
    }

    public static void setWeekGoal(@NonNull Context context, float weekGoal){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(WEEK_GOAL, weekGoal).apply();
    }

    public static float getPenaltyDistance(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(PENALTY_DISTANCE, 0f);
    }

    public static void setPenaltyDistance(@NonNull Context context, float penaltyDistance){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(PENALTY_DISTANCE, penaltyDistance).apply();
    }

    private static boolean areDefaultsSet(@NonNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(DEFAULTS_SET, false);
    }

    public static void setDefaultValues(@NonNull Context context, boolean override){
        if (!areDefaultsSet(context) || override) {
            setDistanceRun(context, 0f);
            setPenalties(context, 0);
            setStartDate(context, new Date());
            setWeekGoal(context, 10f);
            setPenaltyDistance(context, 5f);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SAVED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(DEFAULTS_SET, true).apply();
    }
}
