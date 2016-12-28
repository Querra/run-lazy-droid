package de.querra.mobile.rlblib.entities;

import static de.querra.mobile.rlblib.entities.AchievementCategoryNames.ACTIVITY_NAME;
import static de.querra.mobile.rlblib.entities.AchievementCategoryNames.DISTANCE_NAME;
import static de.querra.mobile.rlblib.entities.AchievementCategoryNames.SPECIAL_NAME;
import static de.querra.mobile.rlblib.entities.AchievementCategoryNames.SPEED_NAME;
import static de.querra.mobile.rlblib.entities.AchievementCategoryNames.TIME_NAME;

public enum AchievementCategory {
    DISTANCE(DISTANCE_NAME),
    TIME(TIME_NAME),
    SPEED(SPEED_NAME),
    ACTIVITY(ACTIVITY_NAME),
    SPECIAL(SPECIAL_NAME);

    private final String name;

    AchievementCategory(String name){
        this.name = name;
    }

    public static AchievementCategory fromString(String achievementCategory){
        switch (achievementCategory){
            case DISTANCE_NAME:
                return DISTANCE;
            case TIME_NAME:
                return TIME;
            case SPEED_NAME:
                return SPEED;
            case ACTIVITY_NAME:
                return ACTIVITY;
            case SPECIAL_NAME:
                return SPECIAL;
        }
        return null;
    }

    public String getName(){
        return this.name;
    }
}
